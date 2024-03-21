package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import lombok.Data;

import java.util.List;

@Data
public class TcRecipeCardResponse {

    /**
     * ID
     */
    private String id;

    /**
     * 集团ID
     */
    private String centerId;

    /**
     * 菜品jk_id
     */
    private String dishJkId;

    /**
     * 菜品单位jk_id
     */
    private String dishUnitJkId;

    /**
     * 菜品单位code
     */
    private String dishUnitCode;

    /**
     * 菜品单位删除标识:0(未删除),1(已删除)
     */
    private Integer delFlag;

    /**
     * 菜品成本卡
     */
    private List<TcRecipeCardDetailsResponse> recipecard_dts;


    /**
     * 做法信息
     */
    private List<Object> makelist;
}
