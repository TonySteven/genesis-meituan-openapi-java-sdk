package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * MtShopInfo实体类
 *
 * @author 人工智能
 * &#064;date  2023-10-30 18:41:04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "mt_shop_info")
public class MtShopInfoEntity {

    /**
     * id
     */
    @TableField(value = "id")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 品牌
     */
    @TableField(value = "brand")
    private String brand;

    /**
     * 美团门店id
     */
    @TableField(value = "shop_id")
    private String shopId;

    /**
     * app_poi_code
     */
    @TableField(value = "app_poi_code")
    private String appPoiCode;

    /**
     * 是否上线
     */
    @TableField(value = "is_online")
    private Integer isOnline;

    /**
     * 更新时间
     */
    @TableField(value = "utime")
    private String utime;

    /**
     * 门店电话
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 备用电话
     */
    @TableField(value = "standby_tel")
    private String standbyTel;

    /**
     * 门店图片
     */
    @TableField(value = "pic_url")
    private String picUrl;

    /**
     * 营业状态
     */
    @TableField(value = "open_level")
    private Integer openLevel;

    /**
     * 城市id
     */
    @TableField(value = "city_id")
    private String cityId;

    /**
     * 门店名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 最小预定天数
     */
    @TableField(value = "pre_book_min_days")
    private Integer preBookMinDays;

    /**
     * 经度
     */
    @TableField(value = "longitude")
    private String longitude;

    /**
     * 促销信息
     */
    @TableField(value = "promotion_info")
    private String promotionInfo;

    /**
     * 标签名称
     */
    @TableField(value = "tag_name")
    private String tagName;

    /**
     * 配送费
     */
    @TableField(value = "shipping_fee")
    private String shippingFee;

    /**
     * 创建时间
     */
    @TableField(value = "ctime")
    private String ctime;

    /**
     * location_id
     */
    @TableField(value = "location_id")
    private Integer locationId;

    /**
     * 是否支持预定
     */
    @TableField(value = "pre_book")
    private Integer preBook;

    /**
     * 是否支持开发票
     */
    @TableField(value = "invoice_support")
    private Integer invoiceSupport;

    /**
     * 配送时间
     */
    @TableField(value = "shipping_time")
    private String shippingTime;

    /**
     * 门店地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 最大预定天数
     */
    @TableField(value = "pre_book_max_days")
    private Integer preBookMaxDays;

    /**
     * 开发票最低消费
     */
    @TableField(value = "invoice_min_price")
    private Integer invoiceMinPrice;

    /**
     * 是否支持时间选择
     */
    @TableField(value = "time_select")
    private Integer timeSelect;

    /**
     * 纬度
     */
    @TableField(value = "latitude")
    private String latitude;

    /**
     * 开发票说明
     */
    @TableField(value = "invoice_description")
    private String invoiceDescription;

    /**
     * 第三方标签名称
     */
    @TableField(value = "third_tag_name")
    private String thirdTagName;

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
    @TableLogic(delval = "1", value = "0")
    private Integer isDeleted;

    /**
     * 备注
     */
    @TableField(value = "remarks")
    private String remarks;
}
