package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcItemSizeResponse;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 品项规格表
 * </p>
 *
 * @author LiPei
 * @since 2024-03-16
 */
@Data
@TableName("tc_item_size")
public class TcItemSizeEntity implements Serializable {

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
     * 所属品项ID
     */
    @TableField("item_id")
    private String itemId;

    /**
     * 规格id
     */
    @TableField("size_id")
    private String sizeId;

    /**
     * 规格编码
     */
    @TableField("code")
    private String code;

    /**
     * 规格名称
     */
    @TableField("name")
    private String name;

    /**
     * 是否为默认规格
     */
    @TableField("is_default")
    private Integer isDefault;

    /**
     * 标准价格
     */
    @TableField("std_price")
    private BigDecimal stdPrice;

    /**
     * 成本价格
     */
    @TableField("cost_price")
    private BigDecimal costPrice;

    /**
     * 删除标识 0表示正常（默认），1表示在回收站，2表示从回收站删除（不可恢复）
     */
    @TableField("delflg")
    private Integer delflg;

    public static List<TcItemSizeEntity> toEntityListByResponse(TcItemEntity item, List<TcItemSizeResponse> responses){
        if(ObjectUtils.isEmpty(responses)){
            return null;
        }
        List<TcItemSizeEntity> list = new ArrayList<>();
        for (TcItemSizeResponse response : responses) {
            list.add(toEntityByResponse(item,response));
        }
        return list;
    }

    public static TcItemSizeEntity toEntityByResponse(TcItemEntity item, TcItemSizeResponse response){
        TcItemSizeEntity entity = new TcItemSizeEntity();
        entity.setCenterId(item.getCenterId());
        entity.setItemId(item.getItemId());
        entity.setSizeId(response.getSize_id());
        entity.setCode(response.getCode());
        entity.setName(response.getName());
        entity.setIsDefault(Boolean.TRUE.equals(response.getIs_default()) ? 1 : 0);
        entity.setStdPrice(response.getStd_price());
        entity.setCostPrice(response.getCost_price());
        entity.setDelflg(response.getDelflg());
        return entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TcItemSizeEntity that = (TcItemSizeEntity) o;
        return Objects.equals(centerId, that.centerId) && Objects.equals(itemId, that.itemId) && Objects.equals(sizeId, that.sizeId) && Objects.equals(code, that.code) && Objects.equals(name, that.name) && Objects.equals(isDefault, that.isDefault) && Objects.equals(stdPrice, that.stdPrice) && Objects.equals(costPrice, that.costPrice) && Objects.equals(delflg, that.delflg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(centerId, itemId, sizeId, code, name, isDefault, stdPrice, costPrice, delflg);
    }
}
