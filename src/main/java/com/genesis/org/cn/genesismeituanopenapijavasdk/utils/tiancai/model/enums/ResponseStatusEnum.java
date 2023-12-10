package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * oa返回状态枚举
 *
 * @author steven
 * &#064;date  2023/10/20
 */
@Getter
@AllArgsConstructor
public enum ResponseStatusEnum {
    /**
     * 成功
     */
    SUCCESS("success", "成功"),
    /**
     * 失败
     */
    FAILED("failed", "失败");

    private final String value;
    private final String info;
}
