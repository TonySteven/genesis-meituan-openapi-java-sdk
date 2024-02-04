package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response;

import lombok.Data;

import java.util.List;

/**
 * 抖音记录列表统一上级内容响应模型
 */
@Data
public class BaseRecordResponse<T> {

    /**
     * 错误码，0为成功
     */
    protected Integer error_code;

    /**
     * 错误码描述
     */
    protected String description;

    /**
     * 总条数
     */
    protected Integer total;

    /**
     * 内容列表
     */
    protected List<T> records;

    /**
     * 内容列表
     */
    protected List<T> pois;
}
