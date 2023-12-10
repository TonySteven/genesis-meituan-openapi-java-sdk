package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.bty.scm.boot.mybatis.base.BaseEntity;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * TcShop实体类
 *
 * @author 人工智能
 * @date 2023-12-10 16:13:44
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "tc_shop")
public class TcShopEntity extends BaseEntity {

    /**
     * id
     */
    @TableField(value = "id")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 餐饮集团ID
     */
    @TableField(value = "centerId")
    private String centerId;

    /**
     * 门店id
     */
    @TableField(value = "shop_id")
    private String shopId;

    /**
     * 门店编号
     */
    @TableField(value = "shop_code")
    private String shopCode;

    /**
     * 门店名称
     */
    @TableField(value = "shop_name")
    private String shopName;

    /**
     * crm门店号
     */
    @TableField(value = "crm_shop_id")
    private Integer crmShopId;

    /**
     * 云端集团号
     */
    @TableField(value = "gc_id")
    private Integer gcId;

    /**
     * 云端门店号
     */
    @TableField(value = "mc_id")
    private Integer mcId;

    /**
     * 区域ID
     */
    @TableField(value = "region_id")
    private String regionId;

    /**
     * 区域名称
     */
    @TableField(value = "region_name")
    private String regionName;

    /**
     * 省份ID
     */
    @TableField(value = "province_id")
    private String provinceId;

    /**
     * 省份名称
     */
    @TableField(value = "province_name")
    private String provinceName;

    /**
     * 城市ID
     */
    @TableField(value = "city_id")
    private String cityId;

    /**
     * 城市名称
     */
    @TableField(value = "city_name")
    private String cityName;

    /**
     * 区划ID
     */
    @TableField(value = "county_id")
    private String countyId;

    /**
     * 区划名称
     */
    @TableField(value = "county_name")
    private String countyName;

    /**
     * 管理模式
     */
    @TableField(value = "manage_type_name")
    private String manageTypeName;

    /**
     * 品牌ID
     */
    @TableField(value = "brand_id")
    private String brandId;

    /**
     * 品牌编号
     */
    @TableField(value = "brand_code")
    private String brandCode;

    /**
     * 品牌名称
     */
    @TableField(value = "brand_name")
    private String brandName;

    /**
     * 开店时间
     */
    @TableField(value = "open_time")
    private String openTime;

    /**
     * 理位置坐标-纬度
     */
    @TableField(value = "gc_x")
    private BigDecimal gcX;

    /**
     * 地理位置坐标-经度
     */
    @TableField(value = "gc_y")
    private BigDecimal gcY;

    /**
     * 门店地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 联系电话
     */
    @TableField(value = "contact_tel")
    private String contactTel;

    /**
     * 面积
     */
    @TableField(value = "area")
    private String area;

    /**
     * 法人id
     */
    @TableField(value = "legal_person_id")
    private String legalPersonId;

    /**
     * 法人名称
     */
    @TableField(value = "legal_person_name")
    private String legalPersonName;

    /**
     * 法人编码
     */
    @TableField(value = "legal_person_code")
    private String legalPersonCode;

    /**
     * 创建人
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 最后修改人
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 最后修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @TableField(value = "is_deleted")
    @TableLogic(delval = "null", value = "0")
    private Integer isDeleted;

    /**
     * 备注
     */
    @TableField(value = "remarks")
    private String remarks;
}
