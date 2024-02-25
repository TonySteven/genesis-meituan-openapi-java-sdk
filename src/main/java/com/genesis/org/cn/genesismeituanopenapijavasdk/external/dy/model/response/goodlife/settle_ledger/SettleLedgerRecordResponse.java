package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.goodlife.settle_ledger;

import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.goodlife.sku.RecordSkuResponse;
import lombok.Data;

/**
 * 分账明细查询响应模型
 */
@Data
public class SettleLedgerRecordResponse {

    /**
     * 游标，用于分页，无业务意义
     */
    private String cursor;

    /**
     * 分账记录id
     */
    private String ledger_id;

    /**
     * 核销记录id
     */
    private String verify_id;

    /**
     * 券id
     */
    private String certificate_id;

    /**
     * 订单id
     */
    private String order_id;

    /**
     * 核销时间， 单位秒，时间戳
     */
    private Long verify_time;

    /**
     * 分账单状态，0表示初始化、1表示分账中、2表示分账成功、3表示分账失败、4表示分账单已废弃
     */
    private Integer status;

    /**
     * 券码
     */
    private String code;

    /**
     * 分账单金额
     */
    private SettleLedgerRecordAmountResponse amount;

    /**
     * 分账款项金额
     */
    private SettleLedgerRecordFundAmountResponse fund_amount;

    /**
     * 团购信息
     */
    private RecordSkuResponse sku;
}
