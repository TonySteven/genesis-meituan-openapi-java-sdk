package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.service.impl;

import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.config.DyConfig;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.request.OauthClientTokenRequest;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.request.goodlife.fulfilment_verify.FulfilmentVerifyRecordQueryRequest;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.request.goodlife.shop.ShopQueryRequest;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.OauthClientTokenResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.goodlife.fulfilment_verify.FulfilmentVerifyRecordQueryResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.goodlife.shop.ShopQueryResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.service.DyService;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.utils.DyHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;


/**
 * 抖音接口请求抽象实现类
 */
@Slf4j
public class DyBaseServiceImpl implements DyService {

    private DyHttpUtils dyHttpUtils;

    public DyBaseServiceImpl(){
        this.init();
    }

    public DyBaseServiceImpl(DyConfig dyConfig){
        this.init(dyConfig);
    }

    @Override
    public void init() {
        this.init(null);
    }

    @Override
    public void init(DyConfig dyConfig) {
        if(ObjectUtils.isEmpty(dyConfig)){
            dyConfig = new DyConfig();
        }
        this.dyHttpUtils = new DyHttpUtils(dyConfig);
    }

    /**
     * 获取配置信息
     * @return 结果
     */
    @Override
    public DyConfig getConfig(){
        return this.dyHttpUtils.getDyConfig();
    }

    /**
     * 设置token（可用来做自定义缓存时使用）
     * @param accessToken 结果
     */
    public void setAccessToken(String accessToken){
        this.dyHttpUtils.getDyConfig().setAccessToken(accessToken);
    }

    /**
     * 生成 client_token
     *
     * @param request 入参
     * @return 结果
     */
    @Override
    public OauthClientTokenResponse oauthClientToken(OauthClientTokenRequest request) {
        return dyHttpUtils.post(request,OauthClientTokenResponse.class);
    }

    /**
     * 查询抖音门店信息
     *
     * @param request 入参
     * @return 结果
     */
    @Override
    public ShopQueryResponse queryShopList(ShopQueryRequest request) {
        return dyHttpUtils.get(request,ShopQueryResponse.class);
    }


    /**
     * 获取抖音验券历史记录
     *
     * @param request 入参
     * @return 结果
     */
    public FulfilmentVerifyRecordQueryResponse queryFulfilmentVerifyRecords(FulfilmentVerifyRecordQueryRequest request){
        return dyHttpUtils.get(request, FulfilmentVerifyRecordQueryResponse.class);
    }
}
