package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.goodlife.settle_ledger;

import lombok.Data;

/**
 * 分账明细查询响应模型
 */
@Data
public class SettleLedgerRecordFundAmountResponse {

    /**
     * 商家货款金额，单位分 （商家实际收到金额，已经扣除支付手续费和佣金费用）
     */
    private Integer goods;

    /**
     * 支付手续费，单位分 （平台收取的千分之六支付手续费）
     */
    private Integer pay_handling;

    /**
     * 达人佣金，单位分 （团购上设置或者服务商引入的才会有佣金）
     */
    private Integer talent_commission;

    /**
     * 服务商分佣总金额，单位分
     */
    private Integer total_agent_merchant;

    /**
     * 软件服务费。已包含支付手续费，单位：分
     */
    private Integer total_merchant_platform_service;
}
