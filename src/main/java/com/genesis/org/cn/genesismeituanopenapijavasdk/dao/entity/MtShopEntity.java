package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bty.scm.boot.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * MtShop实体类
 *
 * @author 人工智能
 * &#064;date  2023-10-29 15:14:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "mt_shop")
public class MtShopEntity extends BaseEntity {

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
