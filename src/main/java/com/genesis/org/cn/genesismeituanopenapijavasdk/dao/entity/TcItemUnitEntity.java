package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcItemUnitResponse;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 品项单位
 */
@Data
@TableName("tc_item_unit")
public class TcItemUnitEntity implements Serializable {

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
     * 单位编码
     */
    @TableField("code")
    private String code;

    /**
     * 单位名称
     */
    @TableField("name")
    private String name;

    /**
     * 单位拼音
     */
    @TableField("pinyin")
    private String pinyin;

    /**
     * 删除标识 0表示正常（默认），1表示在回收站，2表示从回收站删除（不可恢复）
     */
    @TableField("delflg")
    private Integer delflg;



    public static List<TcItemUnitEntity> toEntityListByResponse(String centerId, List<TcItemUnitResponse> responseList){
        List<TcItemUnitEntity> entityList = new ArrayList<>();
        for(TcItemUnitResponse response : responseList){
            entityList.add(toEntityByResponse(centerId,response));
        }
        return entityList;
    }

    public static TcItemUnitEntity toEntityByResponse(String centerId,TcItemUnitResponse response){
        TcItemUnitEntity entity = new TcItemUnitEntity();
        entity.setCenterId(centerId);
        entity.setId(response.getId());
        entity.setCode(response.getCode());
        entity.setName(response.getName());
        entity.setPinyin(response.getPinyin());
        entity.setDelflg(response.getDelflg());
        return entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TcItemUnitEntity that = (TcItemUnitEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(centerId, that.centerId) && Objects.equals(code, that.code) && Objects.equals(name, that.name) && Objects.equals(pinyin, that.pinyin) && Objects.equals(delflg, that.delflg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, centerId, code, name, pinyin, delflg);
    }
}
