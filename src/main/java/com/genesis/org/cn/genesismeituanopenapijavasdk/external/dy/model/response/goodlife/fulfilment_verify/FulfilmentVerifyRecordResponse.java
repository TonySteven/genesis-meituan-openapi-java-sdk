package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.goodlife.fulfilment_verify;

import lombok.Data;

/**
 * 抖音验券历史查询响应模型
 */
@Data
public class FulfilmentVerifyRecordResponse {

    /**
     * 游标，用于分页，无业务意义
     */
    private String cursor;

    /**
     * 核销记录id
     */
    private String verify_id;

    /**
     * 券id
     */
    private String certificate_id;

    /**
     * 核销时间， 单位秒，时间戳
     */
    private Long verify_time;

    /**
     * 是否可撤销
     */
    private Boolean can_cancel;

    /**
     * 核销类型，0默认值，1用户自验，2商家扫二维码，3商家手动输入，4开放平台API
     */
    private Integer verify_type;

    /**
     * 核销记录的状态，1表示已核销，2表示已撤销
     */
    private Integer status;

    /**
     * 券码
     */
    private String code;

    /**
     * 撤销时间，单位秒，时间戳（已撤销时返回）
     */
    private Long cancel_time;

    /**
     * 金额信息
     */
    private FulfilmentVerifyRecordAmountResponse amount;

    /**
     * 团购信息
     */
    private FulfilmentVerifyRecordSkuResponse sku;

    /**
     * 核销金额信息
     */
    private FulfilmentVerifyRecordVerifyAmountInfoResponse verify_amount_info;
}
