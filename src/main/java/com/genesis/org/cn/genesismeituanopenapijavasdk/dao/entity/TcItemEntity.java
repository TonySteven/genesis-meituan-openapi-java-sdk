package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcItemResponse;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 品项档案信息
 * </p>
 *
 * @author LiPei
 * @since 2024-03-16
 */
@Data
@TableName("tc_item")
public class TcItemEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 品项ID
     */
    @TableId("item_id")
    private String itemId;

    /**
     * 集团ID
     */
    @TableField("center_id")
    private String centerId;

    /**
     * 品项代码
     */
    @TableField("item_code")
    private String itemCode;

    /**
     * 品项名称
     */
    @TableField("item_name")
    private String itemName;

    /**
     * 单位
     */
    @TableField("unit_name")
    private String unitName;

    /**
     * 标准价格
     */
    @TableField("std_price")
    private BigDecimal stdPrice;

    /**
     * 所属大类ID
     */
    @TableField("big_class_id")
    private String bigClassId;

    /**
     * 所属小类ID
     */
    @TableField("small_class_id")
    private String smallClassId;

    /**
     * 品牌ID
     */
    @TableField("brand_id")
    private String brandId;

    /**
     * 品牌代码
     */
    @TableField("brand_code")
    private String brandCode;

    /**
     * 品牌名称
     */
    @TableField("brand_name")
    private String brandName;

    /**
     * 是否为套餐 true:是;false:否
     */
    @TableField("is_package")
    private Integer isPackage;

    /**
     * 临时品项标志 true:是;false:否
     */
    @TableField("is_temp_item")
    private Integer isTempItem;

    /**
     * 多规格标志 true:是;false:否
     */
    @TableField("enable_muti_size")
    private Integer enableMutiSize;

    /**
     * 套餐类型 0：常规套餐;1：宴会套餐
     */
    @TableField("pkg_type")
    private Integer pkgType;

    /**
     * 是否启用 true:是;false:否
     */
    @TableField("is_enable")
    private Integer isEnable;

    /**
     * 大图4:3图片URL
     */
    @TableField("big_pic_url")
    private String bigPicUrl;

    /**
     * 大图3:4图片URL
     */
    @TableField("small_pic_url")
    private String smallPicUrl;

    /**
     * GIF动图 图片URL
     */
    @TableField("gif_pic_url")
    private String gifPicUrl;

    /**
     * 品项视频URL
     */
    @TableField("video_url")
    private String videoUrl;

    /**
     * 大图1:1 图片URL
     */
    @TableField("big_pic3_url")
    private String bigPic3Url;

    /**
     * 商品条码
     */
    @TableField("barcode")
    private String barcode;

    /**
     * 英文名称
     */
    @TableField("english_name")
    private String englishName;

    /**
     * 商品介绍
     */
    @TableField("intro")
    private String intro;

    /**
     * 销售收入类别 -1表示菜品无收入类别
     */
    @TableField("sales_revenue_typeId")
    private String salesRevenueTypeid;

    /**
     * 辅助单位 -1表示菜品无辅助单位
     */
    @TableField("auxiliary_unit_id")
    private String auxiliaryUnitId;

    /**
     * 辣度 0：不辣；1：轻辣;2:微辣;3:中辣;4:特辣
     */
    @TableField("pungency_degree")
    private String pungencyDegree;

    /**
     * 招新荐
     */
    @TableField("zxj_type")
    private String zxjType;

    /**
     * 品牌编号-菜品编号
     */
    @TableField("brand_item_code")
    private String brandItemCode;

    /**
     * 品牌名称-菜品名称
     */
    @TableField("brand_item_name")
    private String brandItemName;

    /**
     * 辅助编码
     */
    @TableField("aid_code")
    private String aidCode;

    /**
     * 删除标记 0表示正常（默认），1表示在回收站，2表示从回收站删除（不可恢复）
     */
    @TableField("delflg")
    private Integer delflg;

    /**
     * 创建店编号
     */
    @TableField("shop_code")
    private String shopCode;

    /**
     * 创建店名称
     */
    @TableField("shop_name")
    private String shopName;

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
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 最后修改时间
     */
    @TableField("modify_time")
    private LocalDateTime modifyTime;

    /**
     * 品项规格列表
     */
    @TableField(exist = false)
    private List<TcItemSizeEntity> itemSizeList;

    /**
     * 常规套餐品项列表
     */
    @TableField(exist = false)
    private List<TcItemPgkEntity> pgkNormalItemList;

    /**
     * 宴会套餐品项列表
     */
    @TableField(exist = false)
    private List<TcItemPgkEntity> pgkPartyItemList;

    /**
     * 通用做法列表
     */
    @TableField(exist = false)
    private List<TcItemMethodEntity> publicMethodList;

    /**
     * 专用做法列表
     */
    @TableField(exist = false)
    private List<TcItemMethodEntity> privateMethodList;

    /**
     * 品项标签列表
     */
    @TableField(exist = false)
    private List<TcItemLabelEntity> itemLabelList;

    /**
     * 品项多条码列表
     */
    @TableField(exist = false)
    private List<TcItemMultiBarcodeEntity> multiBarcodeList;

    public static List<TcItemEntity> toEntityListByResponse(String centerId, List<TcItemResponse> responseList){
        List<TcItemEntity> list = new ArrayList<>();
        for (TcItemResponse tcItemResponse : responseList) {
            list.add(toEntityByResponse(centerId,tcItemResponse));
        }
        return list;
    }

    public static TcItemEntity toEntityByResponse(String centerId,TcItemResponse response){
        TcItemEntity entity = new TcItemEntity();
        entity.setCenterId(centerId);
        entity.setItemId(response.getItem_id());
        entity.setItemCode(response.getItem_code());
        entity.setItemName(response.getItem_name());
        entity.setUnitName(response.getUnit_name());
        entity.setStdPrice(response.getStd_price());
        entity.setBigClassId(response.getBig_class_id());
        entity.setSmallClassId(response.getSmall_class_id());
        entity.setBrandId(response.getBrand_id());
        entity.setBrandCode(response.getBrand_code());
        entity.setBrandName(response.getBrand_name());
        entity.setIsPackage(Boolean.TRUE.equals(response.getIs_package()) ? 1 : 0);
        entity.setIsTempItem(Boolean.TRUE.equals(response.getIs_temp_item()) ? 1 : 0);
        entity.setEnableMutiSize(Boolean.TRUE.equals(response.getEnable_muti_size()) ? 1 : 0);
        entity.setPkgType(response.getPkg_type());
        entity.setIsEnable(Boolean.TRUE.equals(response.getIs_enable()) ? 1 : 0);
        entity.setBigPicUrl(response.getBig_pic_url());
        entity.setSmallPicUrl(response.getSmall_pic_url());
        entity.setGifPicUrl(response.getGif_pic_url());
        entity.setVideoUrl(response.getVideo_url());
        entity.setBigPic3Url(response.getBig_pic3_url());
        entity.setBarcode(response.getBarcode());
        entity.setEnglishName(response.getEnglish_name());
        entity.setIntro(response.getIntro());
        entity.setSalesRevenueTypeid(response.getSales_revenue_typeId());
        entity.setAuxiliaryUnitId(response.getAuxiliary_unit_id());
        entity.setPungencyDegree(response.getPungency_degree());
        entity.setZxjType(response.getZxj_type());
        entity.setBrandItemCode(response.getBrand_item_code());
        entity.setBrandItemName(response.getBrand_item_name());
        entity.setAidCode(response.getAid_code());
        entity.setDelflg(response.getDelflg());
        entity.setShopCode(response.getShop_code());
        entity.setShopName(response.getShop_name());
        entity.setCenterCode(response.getCenter_code());
        entity.setCenterName(response.getCenter_name());
        entity.setCreateTime(LocalDateTime.now());
        entity.setModifyTime(LocalDateTime.now());
        entity.setItemSizeList(TcItemSizeEntity.toEntityListByResponse(entity,response.getItemSizeList()));
        entity.setPgkNormalItemList(TcItemPgkEntity.toEntityListByResponse(entity,response.getPgkNormalItemList(),0));
        entity.setPgkPartyItemList(TcItemPgkEntity.toEntityListByResponse(entity,response.getPgkPartyItemList(),1));
        entity.setPublicMethodList(TcItemMethodEntity.toEntityListByResponse(entity,response.getPublicMethodList(),0));
        entity.setPrivateMethodList(TcItemMethodEntity.toEntityListByResponse(entity,response.getPrivateMethodList(),1));
        entity.setMultiBarcodeList(TcItemMultiBarcodeEntity.toEntityListByResponse(entity,response.getMultiBarcodeList()));
        entity.setItemLabelList(TcItemLabelEntity.toEntityListByResponse(entity,response.getItemLabelList()));
        return entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TcItemEntity that = (TcItemEntity) o;
        return Objects.equals(itemId, that.itemId) && Objects.equals(centerId, that.centerId) && Objects.equals(itemCode, that.itemCode) && Objects.equals(itemName, that.itemName) && Objects.equals(unitName, that.unitName) && Objects.equals(stdPrice, that.stdPrice) && Objects.equals(bigClassId, that.bigClassId) && Objects.equals(smallClassId, that.smallClassId) && Objects.equals(brandId, that.brandId) && Objects.equals(brandCode, that.brandCode) && Objects.equals(brandName, that.brandName) && Objects.equals(isPackage, that.isPackage) && Objects.equals(isTempItem, that.isTempItem) && Objects.equals(enableMutiSize, that.enableMutiSize) && Objects.equals(pkgType, that.pkgType) && Objects.equals(isEnable, that.isEnable) && Objects.equals(bigPicUrl, that.bigPicUrl) && Objects.equals(smallPicUrl, that.smallPicUrl) && Objects.equals(gifPicUrl, that.gifPicUrl) && Objects.equals(videoUrl, that.videoUrl) && Objects.equals(bigPic3Url, that.bigPic3Url) && Objects.equals(barcode, that.barcode) && Objects.equals(englishName, that.englishName) && Objects.equals(intro, that.intro) && Objects.equals(salesRevenueTypeid, that.salesRevenueTypeid) && Objects.equals(auxiliaryUnitId, that.auxiliaryUnitId) && Objects.equals(pungencyDegree, that.pungencyDegree) && Objects.equals(zxjType, that.zxjType) && Objects.equals(brandItemCode, that.brandItemCode) && Objects.equals(brandItemName, that.brandItemName) && Objects.equals(aidCode, that.aidCode) && Objects.equals(delflg, that.delflg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, centerId, itemCode, itemName, unitName, stdPrice, bigClassId, smallClassId, brandId, brandCode, brandName, isPackage, isTempItem, enableMutiSize, pkgType, isEnable, bigPicUrl, smallPicUrl, gifPicUrl, videoUrl, bigPic3Url, barcode, englishName, intro, salesRevenueTypeid, auxiliaryUnitId, pungencyDegree, zxjType, brandItemCode, brandItemName, aidCode, delflg);
    }
}
