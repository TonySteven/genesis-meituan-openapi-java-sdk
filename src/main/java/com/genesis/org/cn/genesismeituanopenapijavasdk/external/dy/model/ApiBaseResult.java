package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model;

import lombok.Data;

/**
 * 基础对象接收器
 * @param <T>
 */
@Data
public class ApiBaseResult<T> {

    /**
     * 状态码
     */
    protected String code;

    /**
     * 内容
     */
    protected T data;

    /**
     * message
     */
    protected String message;
}
