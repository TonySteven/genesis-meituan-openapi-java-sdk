package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.request;

import lombok.Data;

/**
 *
 */
@Data
public class TcScmDjmxRequest {

    /**
     * 开始时间
     */
    private String beginDate;

    /**
     * 结束时间
     */
    private String endDate;

    /**
     * 上次修改时间
     */
    private String lastModifyTime;

    /**
     * 订单状态类型 0-正常订单 1-反审订单
     */
    private Integer orderStatusType;
}
