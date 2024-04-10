package com.genesis.org.cn.genesismeituanopenapijavasdk.enums;

import lombok.Getter;

/**
 * 天才同步同步类型枚举
 */
@Getter
public enum TcSyncTypeEnums {
    /**
     * 品项
     */
    ITEM("ITEM", "品项"),
    /**
     * 品项类别
     */
    ITEM_CLASS("ITEM_CLASS", "品项类别"),
    /**
     * 做法档案
     */
    METHODS("METHODS", "做法档案"),
    /**
     * 做法档案类别
     */
    METHODS_CLASS("METHODS_CLASS", "做法档案类别"),
    /**
     * 结算方式
     */
    PAYWAY("PAYWAY", "结算方式"),
    /**
     * 结算方式类别
     */
    PAYWAY_CLASS("PAYWAY_CLASS", "结算方式类别"),
    /**
     * 成本卡
     */
    RECIPE_CARD("RECIPE_CARD", "成本卡"),
    /**
     * 供应链品项
     */
    SCM_PX("SCM_PX", "供应链品项"),
    /**
     * 供应链供应商
     */
    SCM_GYS("SCM_GYS", "供应链供应商"),
    ;

    private final String code;
    private final String desc;
    TcSyncTypeEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
