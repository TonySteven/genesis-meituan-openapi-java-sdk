package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bty.scm.boot.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;


/**
 * MtShopScore实体类
 *
 * @author 人工智能
 * &#064;date  2023-10-29 15:12:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "mt_shop_score")
public class MtShopScoreEntity extends BaseEntity {

    /**
     * 品牌
     */
    @TableField(value = "brand")
    private String brand;

    /**
     * 美团门店id
     */
    @TableField(value = "shop_id")
    private String shopId;

    /**
     * 美团门店编码
     */
    @TableField(value = "appPoiCode")
    private Integer appPoiCode;

    /**
     * 美团门店评分
     */
    @TableField(value = "avgPoiScore")
    private BigDecimal avgPoiScore;

    /**
     * 美团门店口味评分
     */
    @TableField(value = "avgTasteScore")
    private BigDecimal avgTasteScore;

    /**
     * 美团门店包装评分
     */
    @TableField(value = "avgPackingScore")
    private BigDecimal avgPackingScore;

    /**
     * 美团门店配送评分
     */
    @TableField(value = "avgDeliveryScore")
    private BigDecimal avgDeliveryScore;

    /**
     * 美团门店配送满意度
     */
    @TableField(value = "deliverySatisfaction")
    private Integer deliverySatisfaction;

    /**
     * 是否删除
     */
    @TableField(value = "is_deleted")
    private Boolean isDeleted;

    /**
     * 备注
     */
    @TableField(value = "remarks")
    private String remarks;
}
