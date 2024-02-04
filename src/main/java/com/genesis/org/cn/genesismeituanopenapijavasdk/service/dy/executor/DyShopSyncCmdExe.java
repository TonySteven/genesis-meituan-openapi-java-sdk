package com.genesis.org.cn.genesismeituanopenapijavasdk.service.dy.executor;

import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.dy.IDyShopService;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.dy.DyShopEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.enums.DyAccountEnums;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.request.goodlife.shop.ShopQueryRequest;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.goodlife.shop.ShopQueryResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy.goodlife.shop.ShopSyncCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.result.ApiResult;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.dy.context.DyConfigContextService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 抖音SaaS-定时调用抖音门店信息并落库api具体逻辑.
 */
@Slf4j
@Service
public class DyShopSyncCmdExe {

    @Resource
    private DyConfigContextService dyConfigContextService;

    @Resource
    private IDyShopService dyShopService;

    public ApiResult<String> execute(ShopSyncCmd cmd) {
        log.info("开始执行抖音门店查询并落库api");

        String logPrefix = String.format("[dy]同步门店信息 ,accountId:%s",cmd.getAccountId());
        try {

            log.info("{}, {}", logPrefix, "start:开始同步");

            // 获取本地生活商家账户信息
            DyAccountEnums account = DyAccountEnums.getByAccountId(cmd.getAccountId());
            if(ObjectUtils.isEmpty(account)){
                return ApiResult.error("未找生活商家账户信息");
            }

            List<DyShopEntity> shopEntityList = new ArrayList<>();

            // 创建抖音门店查询请求对象
            ShopQueryRequest request = cmd.toRequest();
            request.setPage(1);
            request.setSize(100);

            do {
                // 查询抖音验券历史记录
                ShopQueryResponse response = dyConfigContextService
                    .getDyService(cmd.getAppId()).queryShopList(request);

                // 如果没有数据直接break
                if(ObjectUtils.isEmpty(response) || ObjectUtils.isEmpty(response.getPois())){
                    break;
                }

                shopEntityList.addAll(DyShopEntity.toEntityByResponse(response));

                // 查询数据小于每页条，说明没有数据了，直接终止循环
                if(response.getPois().size() < request.getSize()){
                    break;
                }

                request.setPage(request.getPage() + 1);
            }while (true);

            if(ObjectUtils.isEmpty(shopEntityList)){
                return new ApiResult<>(null);
            }

            // 获取门店ID列表
            List<String> poiIds = shopEntityList.stream().map(DyShopEntity::getPoiId).toList();

            // 查询门店列表
            Map<String, DyShopEntity> existShopMap = dyShopService.getMapByAccountIdAndPoiIds(cmd.getAccountId(), poiIds);

            // 根据入库list列表查询数据库是否存在该数据，如果存在直接更新，不存在就新增
            List<DyShopEntity> saveList = new ArrayList<>();

            List<DyShopEntity> updateList = new ArrayList<>();

            shopEntityList.forEach(entity -> {
                if(existShopMap.containsKey(entity.getPoiId())){
                    // 存在直接更新
                    updateList.add(entity);
                }else{
                    // 不存在直接新增
                    saveList.add(entity);
                }
            });

            // 批量保存门店信息当前门店券数据
            dyShopService.operateBatch(saveList,updateList);

            log.info("{}, {}", logPrefix, "end:完成同步");
            return new ApiResult<>(logPrefix + ", 同步成功");
        }catch (Exception e){
            String errorLog = logPrefix + ", 同步门店信息异常, e:" + e.getMessage();
            log.error("{}", errorLog,e);
            return new ApiResult<>(errorLog);
        }
    }
}
