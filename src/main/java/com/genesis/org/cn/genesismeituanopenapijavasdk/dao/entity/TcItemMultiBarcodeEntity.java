package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcItemMultiBarcodeResponse;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 品项多条码表
 * </p>
 *
 * @author LiPei
 * @since 2024-03-16
 */
@Data
@TableName("tc_item_multi_barcode")
public class TcItemMultiBarcodeEntity implements Serializable {

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
     * 条码
     */
    @TableField("barcode")
    private String barcode;

    /**
     * 删除标识
     */
    @TableField("delflg")
    private Integer delflg;

    public static List<TcItemMultiBarcodeEntity> toEntityListByResponse(TcItemEntity item, List<TcItemMultiBarcodeResponse> response){
        if(ObjectUtils.isEmpty(response)){
            return null;
        }
        List<TcItemMultiBarcodeEntity> list = new ArrayList<>();
        for (TcItemMultiBarcodeResponse tcItemMultiBarcodeResponse : response) {
            list.add(toEntityByResponse(item,tcItemMultiBarcodeResponse));
        }
        return list;
    }

    public static TcItemMultiBarcodeEntity toEntityByResponse(TcItemEntity item, TcItemMultiBarcodeResponse response) {
        TcItemMultiBarcodeEntity entity = new TcItemMultiBarcodeEntity();
        entity.setCenterId(item.getCenterId());
        entity.setItemId(item.getItemId());
        entity.setSizeId(response.getSize_id());
        entity.setSizeName(response.getSize_name());
        entity.setBarcode(response.getBarcode());
        entity.setDelflg(response.getDelflg());
        return entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TcItemMultiBarcodeEntity that = (TcItemMultiBarcodeEntity) o;
        return Objects.equals(centerId, that.centerId) && Objects.equals(itemId, that.itemId) && Objects.equals(sizeId, that.sizeId) && Objects.equals(sizeName, that.sizeName) && Objects.equals(barcode, that.barcode) && Objects.equals(delflg, that.delflg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(centerId, itemId, sizeId, sizeName, barcode, delflg);
    }
}
