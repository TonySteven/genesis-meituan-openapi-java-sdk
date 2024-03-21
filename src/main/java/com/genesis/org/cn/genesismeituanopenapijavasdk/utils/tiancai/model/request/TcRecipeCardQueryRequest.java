package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.request;

import lombok.Data;

/**
 * 获取菜品成本卡详细信息入参
 */
@Data
public class TcRecipeCardQueryRequest {

    /**
     * 菜品id（多个需用英文逗号隔开）
     */
    private String dishJkid;

    /**
     * 是否包含已删除的菜品单位:0(不包含),1(包含)
     */
    private String containsDel;

    /**
     * 是否包含成本卡和做法都为空的菜品单位:0(不包含),1(包含)
     */
    private String containsEmptyDt;

    /**
     * 是否包含菜品涉及的所有成本卡和做法:0(不包含),1(包含)
     */
    private String containsAllDt;
}
