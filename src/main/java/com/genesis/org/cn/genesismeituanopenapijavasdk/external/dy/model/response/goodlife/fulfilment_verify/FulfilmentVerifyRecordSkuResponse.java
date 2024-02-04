package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.goodlife.fulfilment_verify;

import lombok.Data;

/**
 * 抖音验券记录团购信息响应模型
 */
@Data
public class FulfilmentVerifyRecordSkuResponse {

    /**
     * 团购SKU ID
     */
    private String sku_id;

    /**
     * 团购名称
     */
    private String title;

    /**
     * 团购类型（type=1团餐券; type=2代金券;type=3次卡）
     */
    private Integer groupon_type;

    /**
     * 团购市场价
     */
    private Integer market_price;

    /**
     * 团购售卖开始时间， 单位秒，时间戳
     */
    private Long sold_start_time;

    /**
     * 商家系统（第三方）团购id
     */
    private String third_sku_id;

    /**
     * 企业号商家总店id（验券准备接口中返回）
     */
    private String account_id;
}
