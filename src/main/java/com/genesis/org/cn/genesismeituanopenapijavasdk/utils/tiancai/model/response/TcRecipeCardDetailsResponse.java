package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TcRecipeCardDetailsResponse {

    /**
     * id
     */
    private String id;

    /**
     * 集团ID
     */
    private String center_id;

    /**
     * 品项id
     */
    private String item_id;

    /**
     * 品项单位id
     */
    private String item_unit_id;

    /**
     * 菜品jk_id
     */
    private String dish_jk_id;

    /**
     * 菜品单位jk_id
     */
    private String dish_unit_jk_id;

    /**
     * 堂食
     */
    private Boolean ts;

    /**
     * 外带
     */
    private Boolean wd;

    /**
     * 外卖
     */
    private Boolean wm;

    /**
     * 自提
     */
    private Boolean zt;

    /**
     * 试吃
     */
    private Boolean sc;

    /**
     * 员工餐
     */
    private Boolean ygc;

    /**
     * 废弃
     */
    private Boolean fq;

    /**
     * 毛料用量
     */
    private BigDecimal gross_use;

    /**
     * 生效日期 格式:年月日
     */
    private LocalDateTime begin_date;

    /**
     * 失效日期 格式:年月日
     */
    private LocalDateTime end_date;

    /**
     * 修改时间 格式:年月日时分秒
     */
    private LocalDateTime modify_date;

    /**
     * 适用全部门店标志
     */
    private Boolean all_shop_flag;

    /**
     * 适用门店列表
     */
    private List<String> shop_ids;
}
