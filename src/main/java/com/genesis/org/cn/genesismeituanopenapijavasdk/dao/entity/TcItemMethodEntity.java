package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcItemMethodResponse;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 品项通用做法表
 * </p>
 *
 * @author LiPei
 * @since 2024-03-16
 */
@Data
@TableName("tc_item_method")
public class TcItemMethodEntity implements Serializable {

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
    @TableId("center_id")
    private String centerId;

    /**
     * 品项ID
     */
    @TableField("item_id")
    private String itemId;

    /**
     * 做法ID
     */
    @TableField("method_id")
    private String methodId;

    /**
     * 做法类型 0:通用做法 1:专用做法
     */
    @TableField("method_type")
    private Integer methodType;

    /**
     * 做法编号
     */
    @TableField("code")
    private String code;

    /**
     * 做法名称
     */
    @TableField("name")
    private String name;

    public static List<TcItemMethodEntity> toEntityListByResponse(TcItemEntity item, List<TcItemMethodResponse> response,Integer methodType){
        List<TcItemMethodEntity> list = new ArrayList<>();
        for (TcItemMethodResponse tcItemMethodResponse : response) {
            list.add(toEntityByResponse(item,tcItemMethodResponse,methodType));
        }
        return list;
    }

    public static TcItemMethodEntity toEntityByResponse(TcItemEntity item, TcItemMethodResponse response,Integer methodType) {
        TcItemMethodEntity entity = new TcItemMethodEntity();
        entity.setCenterId(item.getCenterId());
        entity.setItemId(item.getItemId());
        entity.setMethodId(response.getId());
        entity.setMethodType(methodType);
        entity.setCode(response.getCode());
        entity.setName(response.getName());
        return entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TcItemMethodEntity that = (TcItemMethodEntity) o;
        return Objects.equals(centerId, that.centerId) && Objects.equals(itemId, that.itemId) && Objects.equals(methodId, that.methodId) && Objects.equals(methodType, that.methodType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(centerId, itemId, methodId, methodType);
    }
}
