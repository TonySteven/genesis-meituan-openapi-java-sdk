package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.request;

import lombok.Data;

/**
 * 分页查询基础请求入参
 */
@Data
public class BasePageQueryRequest {

    /**
     * 页码 ，（从1开始）
     */
    private Integer page = 1;

    /**
     * 页大小
     * 数值范围：[1, 100]
     */
    private Integer size = 10;
}
