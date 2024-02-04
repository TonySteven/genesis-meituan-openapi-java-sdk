package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.service;

import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.config.DyConfig;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.request.OauthClientTokenRequest;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.request.goodlife.fulfilment_verify.FulfilmentVerifyRecordQueryRequest;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.request.goodlife.shop.ShopQueryRequest;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.OauthClientTokenResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.goodlife.fulfilment_verify.FulfilmentVerifyRecordQueryResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.goodlife.shop.ShopQueryResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.service.impl.DyBaseServiceImpl;


/**
 * 抖音相关接口
 * @author LP
 */
public interface DyService {

    static DyBaseServiceImpl dyBaseService(){
        return new DyBaseServiceImpl();
    }

    static DyBaseServiceImpl dyBaseService(DyConfig dyConfig){
        return new DyBaseServiceImpl(dyConfig);
    }

    void init();

    void init(DyConfig dyConfig);

    /**
     * 获取配置信息
     * @return 结果
     */
    DyConfig getConfig();

    /**
     * 设置token（可用来做自定义缓存时使用）
     */
    void setAccessToken(String accessToken);

    /**
     * 生成 client_token
     * @param request 入参
     * @return 结果
     */
    OauthClientTokenResponse oauthClientToken(OauthClientTokenRequest request);

    /**
     * 查询抖音门店信息
     *
     * @param request 入参
     * @return 结果
     */
    ShopQueryResponse queryShopList(ShopQueryRequest request);

    /**
     * 获取抖音验券历史记录
     *
     * @param request 入参
     * @return 结果
     */
    FulfilmentVerifyRecordQueryResponse queryFulfilmentVerifyRecords(FulfilmentVerifyRecordQueryRequest request);

}
