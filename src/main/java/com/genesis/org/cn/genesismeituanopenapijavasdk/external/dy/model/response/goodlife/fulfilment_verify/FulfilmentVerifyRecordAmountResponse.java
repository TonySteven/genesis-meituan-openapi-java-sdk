package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.goodlife.fulfilment_verify;

import lombok.Data;

/**
 * 抖音验券历史查询金额信息响应模型
 */
@Data
public class FulfilmentVerifyRecordAmountResponse {

    /**
     * 次卡单次券原始金额, 单位分
     */
    private Integer original_amount;

    /**
     * 次卡单次用户实付金额, 单位分
     */
    private Integer pay_amount;

    /**
     * 次卡单次商家营销金额, 单位分
     */
    private Integer merchant_ticket_amount;

    /**
     * 划线价，单位分
     */
    private Integer list_market_amount;

    /**
     * 平台优惠金额，单位分
     */
    private Integer platform_discount_amount;

    /**
     * 支付优惠金额，单位分
     */
    private Integer payment_discount_amount;

    /**
     * 券实付金额（=用户实付金额+支付优惠金额），单位分
     */
    private Integer coupon_pay_amount;
}
