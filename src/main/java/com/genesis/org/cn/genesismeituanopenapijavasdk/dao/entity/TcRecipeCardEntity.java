package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcRecipeCardResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 菜品成本卡信息
 * </p>
 *
 * @author LiPei
 * @since 2024-03-20
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("tc_recipe_card")
public class TcRecipeCardEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("id")
    private String id;

    /**
     * 集团ID
     */
    @TableField("center_id")
    private String centerId;

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
     * 菜品单位code
     */
    @TableField("dish_unit_code")
    private String dishUnitCode;

    /**
     * 菜品单位删除标识:0(未删除),1(已删除)
     */
    @TableField("del_flag")
    private Integer delFlag;

    /**
     * 成本卡明细品项
     */
    @TableField(exist = false)
    private List<TcRecipeCardDetailsEntity> recipecardDts;

    public static List<TcRecipeCardEntity> toEntityListByResponse(String centerId,List<TcRecipeCardResponse> responseList){
        List<TcRecipeCardEntity> entityList = new ArrayList<>();
        for (TcRecipeCardResponse response : responseList){
            entityList.add(TcRecipeCardEntity.toEntityByResponse(centerId,response));
        }
        return entityList;
    }

    public static TcRecipeCardEntity toEntityByResponse(String centerId, TcRecipeCardResponse response){
        TcRecipeCardEntity entity = new TcRecipeCardEntity();
        entity.setCenterId(centerId);
        entity.setDishJkId(response.getDishJkId());
        entity.setDishUnitJkId(response.getDishUnitJkId());
        entity.setDishUnitCode(response.getDishUnitCode());
        entity.setDelFlag(response.getDelFlag());
        entity.setRecipecardDts(TcRecipeCardDetailsEntity.toEntityListByResponse(entity,response.getRecipecard_dts()));
        return entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TcRecipeCardEntity that = (TcRecipeCardEntity) o;
        return Objects.equals(centerId, that.centerId) && Objects.equals(dishJkId, that.dishJkId) && Objects.equals(dishUnitJkId, that.dishUnitJkId) && Objects.equals(delFlag, that.delFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(centerId, dishJkId, dishUnitJkId, delFlag);
    }
}
