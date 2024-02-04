package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.goodlife.shop;

import lombok.Data;

/**
 * 抖音门店信息响应模型
 */
@Data
public class ShopAccountsResponse {

    private ShopAccountResponse parent_account;

    private ShopAccountResponse poi_account;
}
