package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.goodlife.fulfilment_verify;

import lombok.Data;

/**
 * 验券历史查询次卡单笔核销金额响应模型
 * 【deprecated】
 */
@Data
@Deprecated
public class FulfilmentVerifyRecordTimesCardAmountResponse {

    /**
     * 【deprecated】单次核销金额（当前次卡未接入营销活动，因此全原价 = 用户实付）
     */
    @Deprecated
    private Integer amount;
}
