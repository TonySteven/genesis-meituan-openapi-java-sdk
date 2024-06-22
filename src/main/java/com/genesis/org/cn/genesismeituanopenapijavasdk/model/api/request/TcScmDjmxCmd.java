package com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class TcScmDjmxCmd {

    /**
     * 开始时间
     */
    private LocalDateTime beginDate;

    /**
     * 结束时间
     */
    private LocalDateTime endDate;

    /**
     * 单号
     */
    private String billNo;
}
