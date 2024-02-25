package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.dy;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.goodlife.shop.ShopQueryResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.goodlife.shop.ShopResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

/**
 * <p>
 * 抖音门店信息
 * </p>
 *
 * @author LiPei
 * @since 2024-02-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("dy_shop")
public class DyShopEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 生活服务商家账户 ID
     */
    @TableField("root_account_id")
    private String rootAccountId;

    /**
     * 抖音门店账户 ID
     */
    @TableField("poi_account_id")
    private String poiAccountId;

    /**
     * 上级账户 ID
     */
    @TableField("parent_account_id")
    private String parentAccountId;

    /**
     * 抖音门店 ID
     */
    @TableField("poi_id")
    private String poiId;

    /**
     * 抖音门店名称
     */
    @TableField("poi_name")
    private String poiName;

    /**
     * 门店地址
     */
    @TableField("address")
    private String address;

    /**
     * 门店坐标 - 纬度
     */
    @TableField("latitude")
    private String latitude;

    /**
     * 门店坐标 - 经度
     */
    @TableField("longitude")
    private String longitude;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 是否删除 0-否 1-是
     */
    @TableField("is_deleted")
    private Integer isDeleted;

    public static List<DyShopEntity> toEntityByResponse(ShopQueryResponse response){
        List<DyShopEntity> entityList = new ArrayList<>();
        for(ShopResponse shopResponse : response.getPois()){
            entityList.add(DyShopEntity.toEntityByResponse(shopResponse));
        }
        return entityList;
    }

    public static DyShopEntity toEntityByResponse(ShopResponse response){
        DyShopEntity entity = new DyShopEntity();
        if(ObjectUtils.isNotEmpty(response.getRoot_account())){
            entity.setRootAccountId(response.getRoot_account().getAccount_id());
        }
        if(ObjectUtils.isNotEmpty(response.getAccount())){
            if(ObjectUtils.isNotEmpty(response.getAccount().getParent_account())){
                entity.setParentAccountId(response.getAccount().getParent_account().getAccount_id());
            }
            if(ObjectUtils.isNotEmpty(response.getAccount().getPoi_account())){
                entity.setPoiAccountId(response.getAccount().getPoi_account().getAccount_id());
            }
        }
        if(ObjectUtils.isNotEmpty(response.getPoi())){
            entity.setPoiId(response.getPoi().getPoi_id());
            entity.setPoiName(response.getPoi().getPoi_name());
            entity.setAddress(response.getPoi().getAddress());
            entity.setLatitude(response.getPoi().getLatitude());
            entity.setLongitude(response.getPoi().getLongitude());
        }
        return entity;
    }
}
