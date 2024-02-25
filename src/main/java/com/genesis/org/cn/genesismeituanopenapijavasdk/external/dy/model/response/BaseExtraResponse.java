package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response;

import lombok.Data;

/**
 * 抖音请求基础状态响应模型
 */
@Data
public class BaseExtraResponse {


    /**
     * 错误码，0为成功
     */
    protected Integer error_code;

    /**
     * 错误码描述
     */
    protected String description;

    /**
     * （弃用）子错误码
     */
    protected Integer sub_error_code;

    /**
     * （弃用）子错误码描述
     */
    protected String sub_description;

    /**
     * 请求id
     */
    protected String logid;

    /**
     * （弃用）接口响应时间，时间戳，单位秒
     */
    protected Long now;
}
