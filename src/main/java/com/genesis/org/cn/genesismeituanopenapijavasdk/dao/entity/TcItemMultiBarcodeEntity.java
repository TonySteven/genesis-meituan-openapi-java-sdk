package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcItemMultiBarcodeResponse;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    @TableId("center_id")
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
}
