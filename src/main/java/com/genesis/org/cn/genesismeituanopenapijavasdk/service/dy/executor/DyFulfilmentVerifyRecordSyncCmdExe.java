package com.genesis.org.cn.genesismeituanopenapijavasdk.service.dy.executor;

import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.dy.IDyFulfilmentVerifyRecordService;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.dy.IDyShopService;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.dy.DyFulfilmentVerifyRecordEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.dy.DyShopEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.enums.DyAccountEnums;
import com.genesis.org.cn.genesismeituanopenapijavasdk.enums.StatusCodeEnums;
import com.genesis.org.cn.genesismeituanopenapijavasdk.exception.BusinessException;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.request.goodlife.fulfilment_verify.FulfilmentVerifyRecordQueryRequest;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.goodlife.fulfilment_verify.FulfilmentVerifyRecordQueryResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy.goodlife.fulfilment_verify.FulfilmentVerifyRecordSyncCmd;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * 抖音SaaS-定时调用抖音验券历史并落库api具体逻辑.
 */
@Slf4j
@Service
public class DyFulfilmentVerifyRecordSyncCmdExe {

    @Resource
    private DyConfigContextService dyConfigContextService;

    @Resource
    private IDyFulfilmentVerifyRecordService dyFulfilmentVerifyRecordService;

    @Resource
    private IDyShopService dyShopService;

    public ApiResult<List<String>> execute(FulfilmentVerifyRecordSyncCmd cmd) {
        log.info("开始执行抖音验券历史记录查询并落库api");

        // 获取本地生活商家账户信息
        DyAccountEnums account = DyAccountEnums.getByAccountId(cmd.getAccountId());
        if(ObjectUtils.isEmpty(account)){
            return ApiResult.error("未找生活商家账户信息");
        }

        DyFulfilmentVerifyRecordSyncCmdExe cmdExe = (DyFulfilmentVerifyRecordSyncCmdExe) AopContext.currentProxy();

        // 先根据生活服务商家账户 ID查询门店信息
        List<DyShopEntity> shopEntityList = dyShopService.getListByAccountIdAndPoiIds(cmd.getAccountId(),cmd.getPoiIds());
        if(ObjectUtils.isEmpty(shopEntityList)){
            throw new BusinessException(StatusCodeEnums.NO_CONTENT,"未找到门店信息");
        }

        List<Future<String>> futureList = new ArrayList<>();

        // 循环门店信息 异步获取门店验券数据（因为抖音券记录查询没有门店关联id只能一个门店一个门店的查询）
        for(DyShopEntity shopEntity : shopEntityList){
            futureList.add(cmdExe.handlePoiRecords(cmd, shopEntity));

        }

        // 等待所有线程结果
        List<String> successList = ThreadUtils.threadWaiting(futureList);

        return ApiResult.success(successList);
    }


    @Async("dy-sync-data")
    @Transactional(rollbackFor = Exception.class)
    public Future<String> handlePoiRecords(FulfilmentVerifyRecordSyncCmd cmd, DyShopEntity shopEntity){
        String logPrefix = String.format("[dy]同步验券记录, 门店ID:%s, 门店名称:%s",shopEntity.getPoiId(),shopEntity.getPoiName());

        log.info("{}, {}", logPrefix, "start:开始同步");
        try {
            List<DyFulfilmentVerifyRecordEntity> recordEntityList = new ArrayList<>();

            FulfilmentVerifyRecordQueryRequest request = cmd.toRequest();
            // 企业号商家总店id
            request.setAccount_id(shopEntity.getRootAccountId());
            // 门店ID
            request.setPoi_ids(Collections.singletonList(shopEntity.getPoiId()));
            // 游标
            request.setCursor("0");
            // 每页大小
            request.setSize(20);

            do {
                // 查询抖音验券历史记录
                FulfilmentVerifyRecordQueryResponse response = dyConfigContextService
                    .getDyService(cmd.getAppId()).queryFulfilmentVerifyRecords(request);

                // 如果没有数据直接break
                if(ObjectUtils.isEmpty(response) || ObjectUtils.isEmpty(response.getRecords())){
                    break;
                }

                // 将查询结果转为实体类，添加到要入库的list中
                recordEntityList.addAll(DyFulfilmentVerifyRecordEntity
                    .toEntityListByResponse(shopEntity.getRootAccountId(), shopEntity.getPoiId(),response));

                // 查询数据小于每页条，说明没有数据了，直接终止循环
                if(response.getRecords().size() < request.getSize()){
                    break;
                }

                // 获取最后一条数据的游标
                request.setCursor(response.getRecords().get(response.getRecords().size() - 1).getCursor());

            }while (true);

            if(ObjectUtils.isEmpty(recordEntityList)){
                log.info("{}, {}", logPrefix, "end:门店没有核销记录");
                return new AsyncResult<>(logPrefix + ", 门店没有核销记录");
            }

            // 获取核销记录ID列表
            List<String> verifyIds = recordEntityList.stream().map(DyFulfilmentVerifyRecordEntity::getVerifyId).toList();

            // 根据核销记录ID获取数据库中存在的核销记录
            Map<String,DyFulfilmentVerifyRecordEntity> existRecordEntityMap = dyFulfilmentVerifyRecordService
                .getMapByVerifyIds(shopEntity.getRootAccountId(), shopEntity.getPoiId(),verifyIds);

            // 根据入库list列表查询数据库是否存在该数据，如果存在直接更新，不存在就新增
            List<DyFulfilmentVerifyRecordEntity> saveList = new ArrayList<>();

            List<DyFulfilmentVerifyRecordEntity> updateList = new ArrayList<>();

            recordEntityList.forEach(entity -> {
                if(existRecordEntityMap.containsKey(entity.getVerifyId())){
                    // 存在直接更新
                    updateList.add(entity);
                }else{
                    // 不存在直接新增
                    saveList.add(entity);
                }
            });

            // 批量保存当前门店券数据
            dyFulfilmentVerifyRecordService.operateBatch(saveList,updateList);
        }catch (Exception e){
            String errorLog = logPrefix + ", 同步验券记录异常, e:" + e.getMessage();
            log.error("{}", errorLog,e);
            return new AsyncResult<>(errorLog);
        }
        log.info("{}, {}", logPrefix, "end:完成同步");
        return new AsyncResult<>(logPrefix + ", 同步成功");
    }
}
