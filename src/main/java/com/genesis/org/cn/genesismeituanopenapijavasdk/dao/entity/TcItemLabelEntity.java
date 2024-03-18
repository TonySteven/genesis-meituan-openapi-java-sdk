package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcItemLabelResponse;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 品项标签表
 * </p>
 *
 * @author LiPei
 * @since 2024-03-16
 */
@Data
@TableName("tc_item_label")
public class TcItemLabelEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
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
     * 标签ID
     */
    @TableField("label_id")
    private String labelId;

    /**
     * 标签名称
     */
    @TableField("label_name")
    private String labelName;

    public static List<TcItemLabelEntity> toEntityListByResponse(TcItemEntity item,List<TcItemLabelResponse> response){
        if(ObjectUtils.isEmpty(response)){
            return null;
        }
        List<TcItemLabelEntity> list = new ArrayList<>();
        for (TcItemLabelResponse tcItemLabelResponse : response) {
            list.add(toEntityByResponse(item,tcItemLabelResponse));
        }
        return list;
    }

    public static TcItemLabelEntity toEntityByResponse(TcItemEntity item,TcItemLabelResponse response) {
        TcItemLabelEntity entity = new TcItemLabelEntity();
        entity.setCenterId(item.getCenterId());
        entity.setItemId(item.getItemId());
        entity.setLabelId(response.getLabel_id());
        entity.setLabelName(response.getLabel_name());
        return entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TcItemLabelEntity that = (TcItemLabelEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(centerId, that.centerId) && Objects.equals(itemId, that.itemId) && Objects.equals(labelId, that.labelId) && Objects.equals(labelName, that.labelName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, centerId, itemId, labelId, labelName);
    }
}
