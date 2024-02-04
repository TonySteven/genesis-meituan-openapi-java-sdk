package com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页查询基础请求入参
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BasePageQueryCmd extends BaseCmd {

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
