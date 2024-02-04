package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.goodlife.fulfilment_verify;

import lombok.Data;

/**
 * 验券历史查询对应核销序号的金额明细响应模型
 */
@Data
public class FulfilmentVerifyRecordTimesCardSerialAmountResponse {

    /**
     * 次序号
     */
    private Integer serial_numb;

    /**
     * 对应次序号的金额信息
     */
    private Amount amount;

    /**
     * 对应次序号的金额信息
     */
    @Data
    public static class Amount {

        /**
         * 原始金额，单位分
         */
        private Integer original_amount;

        /**
         * 用户实付金额，单位分
         */
        private Integer pay_amount;

        /**
         * 商家营销金额，单位分
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
}
