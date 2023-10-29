package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bty.scm.boot.mybatis.base.BaseEntity;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * MtShopScore实体类
 *
 * @author 人工智能
 * &#064;date  2023-10-29 15:12:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "mt_shop_score")
public class MtShopScoreEntity extends BaseEntity {

    /**
     * id
     */
    @TableField(value = "id")
    private String id;

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
     * 创建人
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 最后修改人
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 最后修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @TableField(value = "is_deleted")
    @TableLogic(delval = "null", value = "0")
    private Integer isDeleted;

    /**
     * 备注
     */
    @TableField(value = "remarks")
    private String remarks;
}
