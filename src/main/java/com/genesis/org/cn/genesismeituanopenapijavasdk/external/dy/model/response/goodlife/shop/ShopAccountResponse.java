package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.goodlife.shop;

import lombok.Data;

/**
 * 抖音门店信息响应模型
 */
@Data
public class ShopAccountResponse {

    /**
     * 账户 ID
     */
    private String account_id;

    /**
     * 账户名称
     */
    private String account_name;

    /**
     * 账户类型
     */
    private String account_type;
}
