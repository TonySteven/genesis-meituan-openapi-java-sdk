package com.genesis.org.cn.genesismeituanopenapijavasdk.service.dy.executor;

import cn.hutool.core.collection.ListUtil;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.dy.DySettleLedgerRecordDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.dy.IDyAccountDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.dy.IDyFulfilmentVerifyRecordDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.dy.IDyShopDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.dy.DySettleLedgerRecordEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.dy.DyShopEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.enums.DyAccountEnums;
import com.genesis.org.cn.genesismeituanopenapijavasdk.enums.StatusCodeEnums;
import com.genesis.org.cn.genesismeituanopenapijavasdk.exception.BusinessException;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.request.goodlife.settle_ledger.SettleLedgerRecordQueryRequest;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.goodlife.settle_ledger.SettleLedgerRecordQueryResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy.goodlife.settle_ledger.DySettleLedgerRecordAllSyncCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy.goodlife.settle_ledger.DySettleLedgerRecordSyncCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.result.ApiResult;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.dy.context.DyConfigContextService;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.ThreadUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * 抖音SaaS-定时调用抖音分账明细记录历史并落库api具体逻辑.
 */
@Slf4j
@Service
public class DySettleLedgerRecordSyncCmdExe {

    @Resource
    private DyConfigContextService dyConfigContextService;

    @Resource
    private DySettleLedgerRecordDao dySettleLedgerRecordDao;

    @Resource
    private IDyShopDao dyShopService;

    @Resource
    private IDyFulfilmentVerifyRecordDao dyFulfilmentVerifyRecordService;

    @Resource
    private IDyAccountDao dyAccountDao;

    public ApiResult<Object> executeAll(DySettleLedgerRecordAllSyncCmd cmd) {
        log.info("[dy]开始执行全量抖音分账历史记录查询并落库api");

        List<DyAccountEnums> accountList = dyAccountDao.getAccountList(cmd.getAccountIds());

        List<Object> result = new ArrayList<>();

        // 循环同步账户id分账历史
        for (DyAccountEnums account : accountList){
            try {
                DySettleLedgerRecordSyncCmd syncCmd = new DySettleLedgerRecordSyncCmd();
                syncCmd.setAccountId(account.getAccountId());
                syncCmd.setAppId(account.getAppId());
                syncCmd.setPoiIds(cmd.getPoiIds());
                syncCmd.setCertificateIds(cmd.getCertificateIds());
                syncCmd.setStartTime(cmd.getStartTime());
                syncCmd.setEndTime(cmd.getEndTime());
                syncCmd.setRequestId(cmd.getRequestId());

                result.add(execute(syncCmd));
            }catch (Exception e){
                String error = String.format("[dy]账户下门店分账记录同步失败,accountId:%s,e:%s",account.getAccountId(),e.getMessage());
                log.error("{}",error,e);
                result.add(ApiResult.error(error));
            }
        }
        return ApiResult.success(result);
    }

    public ApiResult<List<String>> execute(DySettleLedgerRecordSyncCmd cmd) {
        log.info("开始执行抖音分账明细历史记录查询并落库api");

        // 获取本地生活商家账户信息
        DyAccountEnums account = DyAccountEnums.getByAccountId(cmd.getAccountId());
        if(ObjectUtils.isEmpty(account)){
            return ApiResult.error("未找生活商家账户信息");
        }

        DySettleLedgerRecordSyncCmdExe cmdExe = (DySettleLedgerRecordSyncCmdExe) AopContext.currentProxy();

        // 先根据生活服务商家账户 ID查询门店信息
        List<DyShopEntity> shopEntityList = dyShopService.getListByAccountIdAndPoiIds(cmd.getAccountId(),cmd.getPoiIds());
        if(ObjectUtils.isEmpty(shopEntityList)){
            throw new BusinessException(StatusCodeEnums.NO_CONTENT,"未找到门店信息");
        }

        List<Future<String>> futureList = new ArrayList<>();

        // 循环门店信息 异步获取门店分账明细数据（因为抖音券记录查询没有门店关联id只能一个门店一个门店的查询）
        for(DyShopEntity shopEntity : shopEntityList){
            futureList.add(cmdExe.handlePoiRecords(cmd, shopEntity));

        }

        // 等待所有线程结果
        List<String> successList = ThreadUtils.threadWaiting(futureList);

        return ApiResult.success(successList);
    }


    @Async("dy-sync-data")
    @Transactional(rollbackFor = Exception.class)
    public Future<String> handlePoiRecords(DySettleLedgerRecordSyncCmd cmd, DyShopEntity shopEntity){
        String logPrefix = String.format("[dy]同步分账明细记录, 门店ID:%s, 门店名称:%s",shopEntity.getPoiId(),shopEntity.getPoiName());

        log.info("{}, {}", logPrefix, "start:开始同步");
        try {
            List<DySettleLedgerRecordEntity> recordEntityList = new ArrayList<>();

            // 先查询验券明细记录
            List<String> certificateIds = dyFulfilmentVerifyRecordService
                .getCertificateIds(shopEntity.getRootAccountId(),shopEntity.getPoiId(),cmd);



            // 根据核销记录ID分组 / 50为一组进行查询
            List<List<String>> certificateIdsList = ListUtil.partition(certificateIds,50);

            // 循环券记录ID分组 查询券分账记录
            for(List<String> itemIds : certificateIdsList){

                SettleLedgerRecordQueryRequest request = new SettleLedgerRecordQueryRequest(itemIds);
                // 查询抖音分账明细记录
                SettleLedgerRecordQueryResponse response = dyConfigContextService
                    .getDyService(cmd.getAppId()).querySettleLedgerRecords(request);

                // 如果没有数据直接break
                if(ObjectUtils.isEmpty(response) || ObjectUtils.isEmpty(response.getRecords())){
                    break;
                }

                // 将查询结果转为实体类，添加到要入库的list中
                recordEntityList.addAll(DySettleLedgerRecordEntity
                    .toEntityListByResponse(shopEntity.getRootAccountId(), shopEntity.getPoiId(),response));
            }

            if(ObjectUtils.isEmpty(recordEntityList)){
                log.info("{}, {}", logPrefix, "end:门店没有分账记录");
                return new AsyncResult<>(logPrefix + ", 门店没有分账记录");
            }

            // 获取分账记录ID列表
            List<String> ledgerIds = recordEntityList.stream().map(DySettleLedgerRecordEntity::getLedgerId).toList();

            // 根据分账记录ID获取数据库中存在的分账记录
            Map<String,DySettleLedgerRecordEntity> existRecordEntityMap = dySettleLedgerRecordDao
                .getMapByLedgerIds(shopEntity.getRootAccountId(), shopEntity.getPoiId(),ledgerIds);

            // 根据入库list列表查询数据库是否存在该数据，如果存在直接更新，不存在就新增
            List<DySettleLedgerRecordEntity> saveList = new ArrayList<>();

            List<DySettleLedgerRecordEntity> updateList = new ArrayList<>();

            recordEntityList.forEach(entity -> {
                if(existRecordEntityMap.containsKey(entity.getLedgerId())){
                    // 存在直接更新
                    updateList.add(entity);
                }else{
                    // 不存在直接新增
                    saveList.add(entity);
                }
            });

            // 批量保存当前门店券数据
            dySettleLedgerRecordDao.operateBatch(saveList,updateList);
        }catch (Exception e){
            String errorLog = logPrefix + ", 同步分账明细记录记录异常, e:" + e.getMessage();
            log.error("{}", errorLog,e);
            return new AsyncResult<>(errorLog);
        }
        log.info("{}, {}", logPrefix, "end:完成同步");
        return new AsyncResult<>(logPrefix + ", 同步成功");
    }
}
