package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * <p>
 * 成本卡适用门店
 * </p>
 *
 * @author LiPei
 * @since 2024-03-21
 */
@Data
@TableName("tc_recipe_card_details_shop")
public class TcRecipeCardDetailsShopEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    /**
     * 集团ID
     */
    @TableField("center_id")
    private String centerId;

    /**
     * 成本卡明细ID
     */
    @TableField("card_dt_id")
    private String cardDtId;

    /**
     * 菜品jk_id
     */
    @TableField("dish_jk_id")
    private String dishJkId;

    /**
     * 菜品单位jk_id
     */
    @TableField("dish_unit_jk_id")
    private String dishUnitJkId;

    /**
     * 品项id
     */
    @TableField("item_id")
    private String itemId;

    /**
     * 品项单位id
     */
    @TableField("item_unit_id")
    private String itemUnitId;

    /**
     * 适用门店
     */
    @TableField("shop_id")
    private String shopId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TcRecipeCardDetailsShopEntity that = (TcRecipeCardDetailsShopEntity) o;
        return Objects.equals(centerId, that.centerId) && Objects.equals(dishJkId, that.dishJkId) && Objects.equals(dishUnitJkId, that.dishUnitJkId) && Objects.equals(itemId, that.itemId) && Objects.equals(itemUnitId, that.itemUnitId) && Objects.equals(shopId, that.shopId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(centerId, dishJkId, dishUnitJkId, itemId, itemUnitId, shopId);
    }
}
