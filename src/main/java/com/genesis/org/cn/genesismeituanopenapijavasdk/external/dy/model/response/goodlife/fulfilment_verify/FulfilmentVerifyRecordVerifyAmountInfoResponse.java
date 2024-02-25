package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.goodlife.fulfilment_verify;

import lombok.Data;

/**
 * 验券历史核销金额信息响应模型
 */
@Data
public class FulfilmentVerifyRecordVerifyAmountInfoResponse {

    /**
     * 【deprecated】次卡单笔核销金额
     */
    @Deprecated
    private FulfilmentVerifyRecordTimesCardAmountResponse times_card_amount;

    /**
     * 对应核销序号的金额明细
     */
    private FulfilmentVerifyRecordTimesCardSerialAmountResponse times_card_serial_amount;
}
