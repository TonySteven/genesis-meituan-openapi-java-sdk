package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcItemMethodClassesResponse;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 品项做法类别信息
 * @date : 2024-03-12 15:22:13
 */
@Data
@TableName("tc_item_method_classes")
public class TcItemMethodClassesEntity implements Serializable{

     @Serial
     private static final long serialVersionUID = 1L;

    /** 类别id */
    @TableId("id")
    private String id;

    /**
     * 集团ID
     */
    @TableField("center_id")
    private String centerId;

    /** 类别编号 */
    @TableField("code")
    private String code;

    /** 类别名称 */
    @TableField("name")
    private String name;

    /** 类别名称拼音 */
    @TableField("pinyin")
    private String pinyin;

    /** 上级类别id */
    @TableField("pid")
    private String pid;

    /** 删除标识  0表示正常（默认），1表示在回收站，2表示从回收站删除（不可恢复） */
    @TableField("delflg")
    private Integer delflg;

    /** 上级类别id  顶级默认为0 */
    @TableField("parent_id")
    private String parentId;

    /** 创建店编号 */
    @TableField("shop_code")
    private String shopCode;

    /** 创建店名称 */
    @TableField("shop_name")
    private String shopName;

    /** 集团编号 */
    @TableField("center_code")
    private String centerCode;

    /** 集团名称 */
    @TableField("center_name")
    private String centerName;

    /** 品牌编号 */
    @TableField("brand_code")
    private String brandCode;

    /** 品牌名称 */
    @TableField("brand_name")
    private String brandName;

    /**  */
    @TableField("is_system")
    private Integer isSystem;

    /** 创建时间 */
    @TableField("create_time")
    private LocalDateTime createTime;

    /** 最后修改时间 */
    @TableField("modify_time")
    private LocalDateTime modifyTime;

    public static List<TcItemMethodClassesEntity> toEntityListByResponse(String centerId,List<TcItemMethodClassesResponse> responseList) {
        List<TcItemMethodClassesEntity> list = new ArrayList<>();
        for(TcItemMethodClassesResponse response : responseList){
            list.add(toEntityByResponse(centerId,response));
        }
        return list;
    }

    public static TcItemMethodClassesEntity toEntityByResponse(String centerId,TcItemMethodClassesResponse response)
    {
        TcItemMethodClassesEntity entity = new TcItemMethodClassesEntity();
        entity.setCenterId(centerId);
        entity.setId(response.getId());
        entity.setCode(response.getCode());
        entity.setName(response.getName());
        entity.setPinyin(response.getPinyin());
        entity.setPid(response.getPid());
        entity.setDelflg(response.getDelflg());
        entity.setParentId(response.getParent_id());
        entity.setShopCode(response.getShop_code());
        entity.setShopName(response.getShop_name());
        entity.setCenterCode(response.getCenter_code());
        entity.setCenterName(response.getCenter_name());
        entity.setBrandCode(response.getBrand_code());
        entity.setBrandName(response.getBrand_name());
        entity.setIsSystem(Boolean.TRUE.equals(response.getIs_system()) ? 1 : 0);
        entity.setCreateTime(response.getCreate_time());
        entity.setModifyTime(response.getModify_time());
        return entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TcItemMethodClassesEntity that = (TcItemMethodClassesEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(centerId, that.centerId) && Objects.equals(code, that.code) && Objects.equals(name, that.name) && Objects.equals(pinyin, that.pinyin) && Objects.equals(pid, that.pid) && Objects.equals(delflg, that.delflg) && Objects.equals(parentId, that.parentId) && Objects.equals(isSystem, that.isSystem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, centerId, code, name, pinyin, pid, delflg, parentId, isSystem);
    }
}
