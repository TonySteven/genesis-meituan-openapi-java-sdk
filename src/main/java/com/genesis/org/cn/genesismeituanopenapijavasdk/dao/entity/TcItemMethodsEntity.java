package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcItemMethodsResponse;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 品项类别信息
 * @date : 2024-03-15 09:42:07
 */
@Data
@TableName("tc_item_methods")
public class TcItemMethodsEntity implements Serializable {

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

    /** 顺序号 */
    @TableField("sort_order")
    private Integer sortOrder;

    /** 品项专用做法  true:是，false:否 */
    @TableField("is_only_for_item")
    private Integer isOnlyForItem;

    /** 所属类别id */
    @TableField("parent_id")
    private String parentId;

    /** 所属类别名称 */
    @TableField("parent_name")
    private String parentName;

    /** 是否计费  true:是，false:否 */
    @TableField("is_need_fee")
    private Integer isNeedFee;

    /** 制作费用 */
    @TableField("fee")
    private BigDecimal fee;

    /** 费用与数量有关 */
    @TableField("is_rela_count")
    private Integer isRelaCount;

    /** 费用是否可修改 */
    @TableField("is_fee_editable")
    private Integer isFeeEditable;

    /** 删除标识  0表示正常（默认），1表示在回收站，2表示从回收站删除（不可恢复） */
    @TableField("delflg")
    private Integer delflg;

    /** 是否可以创建临时品项  true：可以创建临时品项，false:不可创建临时品项 */
    @TableField("is_create_temp_item")
    private Integer isCreateTempItem;

    /** 创建店编号 */
    @TableField("create_shop_code")
    private String createShopCode;

    /** 创建店名称 */
    @TableField("create_shop_name")
    private String createShopName;

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

    /** 创建时间 */
    @TableField("create_time")
    private LocalDateTime createTime;

    /** 最后修改时间 */
    @TableField("modify_time")
    private LocalDateTime modifyTime;

    public static List<TcItemMethodsEntity> toEntityListByResponse(String centerId,List<TcItemMethodsResponse> responseList){
        List<TcItemMethodsEntity> entityList = new ArrayList<>();
        for(TcItemMethodsResponse response : responseList){
            entityList.add(toEntityByResponse(centerId,response));
        }
        return entityList;
    }

    public static TcItemMethodsEntity toEntityByResponse(String centerId,TcItemMethodsResponse response){
        TcItemMethodsEntity entity = new TcItemMethodsEntity();
        entity.setCenterId(centerId);
        entity.setId(response.getId());
        entity.setCode(response.getCode());
        entity.setName(response.getName());
        entity.setPinyin(response.getPinyin());
        entity.setSortOrder(response.getSort_order());
        entity.setIsOnlyForItem(Boolean.TRUE.equals(response.getIs_only_for_item()) ? 1 : 0);
        entity.setParentId(response.getParent_id());
        entity.setParentName(response.getParent_name());
        entity.setIsNeedFee(Boolean.TRUE.equals(response.getIs_need_fee()) ? 1 : 0);
        entity.setFee(response.getFee());
        entity.setIsRelaCount(Boolean.TRUE.equals(response.getIs_rela_count()) ? 1 : 0);
        entity.setIsFeeEditable(Boolean.TRUE.equals(response.getIs_fee_editable()) ? 1 : 0);
        entity.setDelflg(response.getDelflg());
        entity.setIsCreateTempItem(Boolean.TRUE.equals(response.getIs_create_temp_item()) ? 1 : 0);
        entity.setCreateShopCode(response.getCreate_shop_code());
        entity.setCreateShopName(response.getCreate_shop_name());
        entity.setCenterCode(response.getCenter_code());
        entity.setCenterName(response.getCenter_name());
        entity.setBrandCode(response.getBrand_code());
        entity.setBrandName(response.getBrand_name());
        entity.setCreateTime(response.getCreate_time());
        entity.setModifyTime(response.getModify_time());
        return entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TcItemMethodsEntity that = (TcItemMethodsEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(centerId, that.centerId) && Objects.equals(code, that.code) && Objects.equals(name, that.name) && Objects.equals(pinyin, that.pinyin) && Objects.equals(sortOrder, that.sortOrder) && Objects.equals(isOnlyForItem, that.isOnlyForItem) && Objects.equals(parentId, that.parentId) && Objects.equals(parentName, that.parentName) && Objects.equals(isNeedFee, that.isNeedFee) && Objects.equals(fee, that.fee) && Objects.equals(isRelaCount, that.isRelaCount) && Objects.equals(isFeeEditable, that.isFeeEditable) && Objects.equals(delflg, that.delflg) && Objects.equals(isCreateTempItem, that.isCreateTempItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, centerId, code, name, pinyin, sortOrder, isOnlyForItem, parentId, parentName, isNeedFee, fee, isRelaCount, isFeeEditable, delflg, isCreateTempItem);
    }
}
