package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 品项档案信息
 * @date : 2024-03-16 13:40:24
 */
@Data
public class TcItemResponse{


    /**
     * 品项id
     */
    private String item_id;


    /**
     * 品项代码
     */
    private String item_code;


    /**
     * 品项名称
     */
    private String item_name;


    /**
     * 单位
     */
    private String unit_name;


    /**
     * 标准价格
     */
    private BigDecimal std_price;


    /**
     * 所属大类id
     */
    private String big_class_id;


    /**
     * 所属小类id
     */
    private String small_class_id;


    /**
     * 品牌id
     */
    private String brand_id;


    /**
     * 品牌代码
     */
    private String brand_code;


    /**
     * 品牌名称
     */
    private String brand_name;


    /**
     * 是否为套餐  true:是;false:否
     */
    private Boolean is_package;


    /**
     * 临时品项标志  true:是;false:否
     */
    private Boolean is_temp_item;


    /**
     * 多规格标志  true:是;false:否
     */
    private Boolean enable_muti_size;


    /**
     * 套餐类型  0：常规套餐;1：宴会套餐
     */
    private Integer pkg_type;


    /**
     * 是否启用  true:是;false:否
     */
    private Boolean is_enable;


    /**
     * 大图4:3图片url
     */
    private String big_pic_url;


    /**
     * 大图3:4图片url
     */
    private String small_pic_url;


    /**
     * gif动图  图片url
     */
    private String gif_pic_url;


    /**
     * 品项视频url
     */
    private String video_url;


    /**
     * 大图1:1  图片url
     */
    private String big_pic3_url;


    /**
     * 商品条码
     */
    private String barcode;


    /**
     * 英文名称
     */
    private String english_name;


    /**
     * 商品介绍
     */
    private String intro;


    /**
     * 销售收入类别  -1表示菜品无收入类别
     */
    private String sales_revenue_typeId;


    /**
     * 辅助单位  -1表示菜品无辅助单位
     */
    private String auxiliary_unit_id;


    /**
     * 辣度  0：不辣；1：轻辣;2:微辣;3:中辣;4:特辣
     */
    private String pungency_degree;


    /**
     * 招新荐
     */
    private String zxj_type;


    /**
     * 品牌编号-菜品编号
     */
    private String brand_item_code;


    /**
     * 品牌名称-菜品名称
     */
    private String brand_item_name;


    /**
     * 辅助编码
     */
    private String aid_code;


    /**
     * 删除标记  0表示正常（默认），1表示在回收站，2表示从回收站删除（不可恢复）
     */
    private Integer delflg;


    /**
     * 创建店编号
     */
    private String shop_code;


    /**
     * 创建店名称
     */
    private String shop_name;


    /**
     * 集团编号
     */
    private String center_code;


    /**
     * 集团名称
     */
    private String center_name;


    /**
     * 创建时间
     */
    private LocalDateTime create_time;


    /**
     * 最后修改时间
     */
    private LocalDateTime modify_time;

    /**
     * 品项规格列表
     */
    private List<TcItemSizeResponse> itemSizeList;

    /**
     * 常规套餐品项列表
     */
    private List<TcItemPgkResponse> pgkNormalItemList;

    /**
     * 宴会套餐品项列表
     */
    private List<TcItemPgkResponse> pgkPartyItemList;

    /**
     * 通用做法列表
     */
    private List<TcItemMethodResponse> publicMethodList;

    /**
     * 专用做法列表
     */
    private List<TcItemMethodResponse> privateMethodList;

    /**
     * 品项标签列表
     */
    private List<TcItemLabelResponse> itemLabelList;

    /**
     * 品项多条码列表
     */
    private List<TcItemMultiBarcodeResponse> multiBarcodeList;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TcItemResponse that = (TcItemResponse) o;
        return Objects.equals(item_id, that.item_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item_id);
    }
}
