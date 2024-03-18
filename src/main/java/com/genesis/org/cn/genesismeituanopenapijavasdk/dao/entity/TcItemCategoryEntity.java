package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcItemCategoryResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 品项类别信息
 * </p>
 *
 * @author LiPei
 * @since 2024-03-10
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("tc_item_category")
public class TcItemCategoryEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 类别ID
     */
    @TableId("class_id")
    private String classId;

    /**
     * 集团ID
     */
    @TableField("center_id")
    private String centerId;

    /**
     * 类别编号
     */
    @TableField("class_code")
    private String classCode;

    /**
     * 类别名称
     */
    @TableField("class_name")
    private String className;

    /**
     * 删除标识 0表示正常（默认），1表示在回收站，2表示从回收站删除（不可恢复）
     */
    @TableField("delflg")
    private Integer delflg;

    /**
     * 级别 0:大类；1:小类
     */
    @TableField("level")
    private Integer level;

    /**
     * 上级类别ID 顶级默认为0
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 是否可以创建临时品项 true：可以创建临时品项，false:不可创建临时品项
     */
    @TableField("is_create_temp_item")
    private Integer isCreateTempItem;

    /**
     * 创建店编号
     */
    @TableField("create_shop_code")
    private String createShopCode;

    /**
     * 创建店名称
     */
    @TableField("create_shop_name")
    private String createShopName;

    /**
     * 集团编号
     */
    @TableField("center_code")
    private String centerCode;

    /**
     * 集团名称
     */
    @TableField("center_name")
    private String centerName;

    /**
     * 品牌编号
     */
    @TableField("brand_code")
    private String brandCode;

    /**
     * 品牌名称
     */
    @TableField("brand_name")
    private String brandName;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 最后修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("modify_time")
    private LocalDateTime modifyTime;

    public static List<TcItemCategoryEntity> toEntityListByResponse(String centerId,List<TcItemCategoryResponse> responseList) {
        List<TcItemCategoryEntity> list = new ArrayList<>();
        for(TcItemCategoryResponse response : responseList){
            list.add(toEntityByResponse(centerId,response));
        }
        return list;
    }

    public static TcItemCategoryEntity toEntityByResponse(String centerId,TcItemCategoryResponse categoryResponse) {
        TcItemCategoryEntity tcCategoryEntity = new TcItemCategoryEntity();
        tcCategoryEntity.setCenterId(centerId);
        tcCategoryEntity.setClassId(categoryResponse.getClass_id());
        tcCategoryEntity.setClassCode(categoryResponse.getClass_code());
        tcCategoryEntity.setClassName(categoryResponse.getClass_name());
        tcCategoryEntity.setDelflg(categoryResponse.getDelflg());
        tcCategoryEntity.setLevel(categoryResponse.getLevel());
        tcCategoryEntity.setParentId(categoryResponse.getParent_id());
        tcCategoryEntity.setIsCreateTempItem(Boolean.TRUE.equals(categoryResponse.getIs_create_temp_item()) ? 1 : 0);
        tcCategoryEntity.setCreateShopCode(categoryResponse.getCreate_shop_code());
        tcCategoryEntity.setCreateShopName(categoryResponse.getCreate_shop_name());
        tcCategoryEntity.setCenterCode(categoryResponse.getCenter_code());
        tcCategoryEntity.setCenterName(categoryResponse.getCenter_name());
        tcCategoryEntity.setBrandCode(categoryResponse.getBrand_code());
        tcCategoryEntity.setBrandName(categoryResponse.getBrand_name());
        tcCategoryEntity.setCreateTime(categoryResponse.getCreate_time());
        tcCategoryEntity.setModifyTime(categoryResponse.getModify_time());
        return tcCategoryEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TcItemCategoryEntity that = (TcItemCategoryEntity) o;
        return Objects.equals(classId, that.classId) && Objects.equals(centerId, that.centerId) && Objects.equals(classCode, that.classCode) && Objects.equals(className, that.className) && Objects.equals(delflg, that.delflg) && Objects.equals(level, that.level) && Objects.equals(parentId, that.parentId) && Objects.equals(isCreateTempItem, that.isCreateTempItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classId, centerId, classCode, className, delflg, level, parentId, isCreateTempItem);
    }
}
