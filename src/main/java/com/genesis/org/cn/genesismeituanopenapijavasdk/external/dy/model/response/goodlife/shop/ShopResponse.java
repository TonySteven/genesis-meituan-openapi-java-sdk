package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.goodlife.shop;

import lombok.Data;

/**
 * 抖音门店信息响应模型
 */
@Data
public class ShopResponse {

    /**
     * 门店信息
     */
    private ShopPoiResponse poi;

    /**
     * 生活服务商家账户信息
     */
    private ShopAccountResponse root_account;

    private ShopAccountsResponse account;
}
