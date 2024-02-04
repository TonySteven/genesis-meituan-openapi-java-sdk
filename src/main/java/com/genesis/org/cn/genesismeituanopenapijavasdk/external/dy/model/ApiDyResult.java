package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model;

import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.ApiBaseResult;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.BaseExtraResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 抖音对象接收器
 * @param <T>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ApiDyResult<T> extends ApiBaseResult<T> {

    /**
     * 访问状态内容
     */
    protected BaseExtraResponse extra;
}
