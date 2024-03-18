package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcItemPgkResponse;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 品项套餐表
 * </p>
 *
 * @author LiPei
 * @since 2024-03-16
 */
@Data
@TableName("tc_item_pgk")
public class TcItemPgkEntity implements Serializable {

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
     * 品项ID
     */
    @TableField("item_id")
    private String itemId;

    /**
     * 品项编码
     */
    @TableField("item_code")
    private String itemCode;

    /**
     * 品项名称
     */
    @TableField("item_name")
    private String itemName;

    /**
     * 规格ID
     */
    @TableField("size_id")
    private String sizeId;

    /**
     * 规格名称
     */
    @TableField("size_name")
    private String sizeName;

    /**
     * 套餐ID
     */
    @TableField("pkg_id")
    private String pkgId;

    /**
     * 套餐类型 0：常规套餐;1：宴会套餐
     */
    @TableField("pkg_type")
    private Integer pkgType;

    /**
     * 默认数量
     */
    @TableField("default_qty")
    private Integer defaultQty;

    /**
     * 标准价格
     */
    @TableField("std_price")
    private BigDecimal stdPrice;

    /**
     * 是否按数量加价 true:是，false:否
     */
    @TableField("is_raise_by_qty")
    private Integer isRaiseByQty;

    /**
     * 加价金额
     */
    @TableField("raise_price")
    private BigDecimal raisePrice;

    /**
     * 限定数量
     */
    @TableField("limited_qty")
    private Integer limitedQty;

    /**
     * 套餐分组ID
     */
    @TableField("pkg_class_id")
    private String pkgClassId;

    /**
     * 套餐分组名称
     */
    @TableField("pkg_class_name")
    private String pkgClassName;

    public static List<TcItemPgkEntity> toEntityListByResponse(TcItemEntity item, List<TcItemPgkResponse> response,Integer pkgType){
        List<TcItemPgkEntity> list = new ArrayList<>();
        for (TcItemPgkResponse tcItemPgkResponse : response) {
            list.add(toEntityByResponse(item,tcItemPgkResponse,pkgType));
        }
        return list;
    }

    public static TcItemPgkEntity toEntityByResponse(TcItemEntity item, TcItemPgkResponse response,Integer pkgType) {
        TcItemPgkEntity entity = new TcItemPgkEntity();
        entity.setCenterId(item.getCenterId());
        entity.setItemId(item.getItemId());
        entity.setItemCode(item.getItemCode());
        entity.setItemName(item.getItemName());
        entity.setSizeId(response.getSize_id());
        entity.setSizeName(response.getSize_name());
        entity.setPkgId(response.getPkg_id());
        entity.setPkgType(pkgType);
        entity.setDefaultQty(response.getDefault_qty());
        entity.setStdPrice(response.getStd_price());
        entity.setIsRaiseByQty(Boolean.TRUE.equals(response.getIs_raise_by_qty()) ? 1 : 0);
        entity.setRaisePrice(response.getRaise_price());
        entity.setLimitedQty(response.getLimited_qty());
        entity.setPkgClassId(response.getPkg_class_id());
        entity.setPkgClassName(response.getPkg_class_name());
        return entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TcItemPgkEntity that = (TcItemPgkEntity) o;
        return Objects.equals(centerId, that.centerId) && Objects.equals(itemId, that.itemId) && Objects.equals(itemCode, that.itemCode) && Objects.equals(itemName, that.itemName) && Objects.equals(sizeId, that.sizeId) && Objects.equals(sizeName, that.sizeName) && Objects.equals(pkgId, that.pkgId) && Objects.equals(pkgType, that.pkgType) && Objects.equals(defaultQty, that.defaultQty) && Objects.equals(stdPrice, that.stdPrice) && Objects.equals(isRaiseByQty, that.isRaiseByQty) && Objects.equals(raisePrice, that.raisePrice) && Objects.equals(limitedQty, that.limitedQty) && Objects.equals(pkgClassId, that.pkgClassId) && Objects.equals(pkgClassName, that.pkgClassName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(centerId, itemId, itemCode, itemName, sizeId, sizeName, pkgId, pkgType, defaultQty, stdPrice, isRaiseByQty, raisePrice, limitedQty, pkgClassId, pkgClassName);
    }
}
