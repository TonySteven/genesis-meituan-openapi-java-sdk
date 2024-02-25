package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.goodlife.settle_ledger;

import lombok.Data;

/**
 * 分账明细查询响应模型
 */
@Data
public class SettleLedgerRecordAmountResponse {

    /**
     * 券原始金额，单位分
     */
    private Integer original;

    /**
     * 用户实付金额，单位分
     */
    private Integer pay;

    /**
     * 商家营销金额，单位分
     */
    private Integer merchant_ticket;

    /**
     * 实付保险费用。由保司收取，单位分
     */
    private Integer actual_insured;
}
