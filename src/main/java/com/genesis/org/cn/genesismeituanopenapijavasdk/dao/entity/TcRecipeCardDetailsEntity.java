package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcRecipeCardDetailsResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 成本卡菜品明细
 * </p>
 *
 * @author LiPei
 * @since 2024-03-20
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("tc_recipe_card_details")
public class TcRecipeCardDetailsEntity implements Serializable {

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
     * 堂食
     */
    @TableField("ts")
    private Integer ts;

    /**
     * 外带
     */
    @TableField("wd")
    private Integer wd;

    /**
     * 外卖
     */
    @TableField("wm")
    private Integer wm;

    /**
     * 自提
     */
    @TableField("zt")
    private Integer zt;

    /**
     * 试吃
     */
    @TableField("sc")
    private Integer sc;

    /**
     * 员工餐
     */
    @TableField("ygc")
    private Integer ygc;

    /**
     * 废弃
     */
    @TableField("fq")
    private Integer fq;

    /**
     * 毛料用量
     */
    @TableField("gross_use")
    private BigDecimal grossUse;

    /**
     * 生效日期 格式:年月日
     */
    @TableField("begin_date")
    private LocalDateTime beginDate;

    /**
     * 失效日期 格式:年月日
     */
    @TableField("end_date")
    private LocalDateTime endDate;

    /**
     * 修改时间 格式:年月日时分秒
     */
    @TableField("modify_date")
    private LocalDateTime modifyDate;

    /**
     * 适用全部门店标志
     */
    @TableField("all_shop_flag")
    private Integer allShopFlag;

    /**
     * 适用门店列表
     */
    @TableField(exist = false)
    private List<TcRecipeCardDetailsShopEntity> shopList;

    public static List<TcRecipeCardDetailsEntity> toEntityListByResponse(TcRecipeCardEntity cartEntity, List<TcRecipeCardDetailsResponse> responseList)
    {
        List<TcRecipeCardDetailsEntity> entityList = new ArrayList<>();
        for (TcRecipeCardDetailsResponse response : responseList){
            entityList.add(toEntityByResponse(cartEntity, response));
        }
        return entityList;
    }

    public static TcRecipeCardDetailsEntity toEntityByResponse(TcRecipeCardEntity cartEntity, TcRecipeCardDetailsResponse response)
    {
        TcRecipeCardDetailsEntity entity = new TcRecipeCardDetailsEntity();
        entity.setId(response.getId());
        entity.setCenterId(cartEntity.getCenterId());
        entity.setItemId(response.getItem_id());
        entity.setItemUnitId(response.getItem_unit_id());
        entity.setDishJkId(response.getDish_jk_id());
        entity.setDishUnitJkId(response.getDish_unit_jk_id());
        entity.setTs(Boolean.TRUE.equals(response.getTs()) ? 1 : 0);
        entity.setWm(Boolean.TRUE.equals(response.getWm()) ? 1 : 0);
        entity.setWd(Boolean.TRUE.equals(response.getWd()) ? 1 : 0);
        entity.setZt(Boolean.TRUE.equals(response.getZt()) ? 1 : 0);
        entity.setSc(Boolean.TRUE.equals(response.getSc()) ? 1 : 0);
        entity.setYgc(Boolean.TRUE.equals(response.getYgc()) ? 1 : 0);
        entity.setFq(Boolean.TRUE.equals(response.getFq()) ? 1 : 0);
        entity.setGrossUse(response.getGross_use());
        entity.setBeginDate(response.getBegin_date());
        entity.setEndDate(response.getEnd_date());
        entity.setModifyDate(response.getModify_date());
        entity.setAllShopFlag(Boolean.TRUE.equals(response.getAll_shop_flag()) ? 1 : 0);

        if(ObjectUtils.isNotEmpty(response.getShop_ids())){
            List<TcRecipeCardDetailsShopEntity> shopList = getTcRecipeCardDetailsShopEntities(cartEntity, response);
            entity.setShopList(shopList);
        }
        return entity;
    }

    private static List<TcRecipeCardDetailsShopEntity> getTcRecipeCardDetailsShopEntities(TcRecipeCardEntity cartEntity, TcRecipeCardDetailsResponse response) {
        List<TcRecipeCardDetailsShopEntity> shopList = new ArrayList<>();
        for(String shopId : response.getShop_ids()){
            TcRecipeCardDetailsShopEntity shopEntity = new TcRecipeCardDetailsShopEntity();
            shopEntity.setCenterId(cartEntity.getCenterId());
            shopEntity.setCardDtId(response.getId());
            shopEntity.setShopId(shopId);
            shopEntity.setItemId(response.getItem_id());
            shopEntity.setItemUnitId(response.getItem_unit_id());
            shopEntity.setDishJkId(response.getDish_jk_id());
            shopEntity.setDishUnitJkId(response.getDish_unit_jk_id());
            shopList.add(shopEntity);
        }
        return shopList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TcRecipeCardDetailsEntity that = (TcRecipeCardDetailsEntity) o;
        return Objects.equals(centerId, that.centerId)
            && Objects.equals(itemId, that.itemId)
            && Objects.equals(itemUnitId, that.itemUnitId)
            && Objects.equals(dishJkId, that.dishJkId)
            && Objects.equals(dishUnitJkId, that.dishUnitJkId)
            && Objects.equals(ts, that.ts)
            && Objects.equals(wd, that.wd)
            && Objects.equals(wm, that.wm)
            && Objects.equals(zt, that.zt)
            && Objects.equals(sc, that.sc)
            && Objects.equals(ygc, that.ygc)
            && Objects.equals(fq, that.fq)
            && Objects.equals(grossUse, that.grossUse)
            && Objects.equals(beginDate, that.beginDate)
            && Objects.equals(endDate, that.endDate)
            && Objects.equals(allShopFlag, that.allShopFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(centerId, itemId, itemUnitId, dishJkId, dishUnitJkId, ts, wd, wm, zt, sc, ygc, fq, grossUse, beginDate, endDate, allShopFlag);
    }
}
