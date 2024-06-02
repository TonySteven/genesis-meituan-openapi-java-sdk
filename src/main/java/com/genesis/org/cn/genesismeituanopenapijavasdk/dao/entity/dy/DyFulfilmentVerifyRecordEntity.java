package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.dy;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.goodlife.fulfilment_verify.FulfilmentVerifyRecordQueryResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.goodlife.fulfilment_verify.FulfilmentVerifyRecordResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 抖音验券历史记录
 * </p>
 *
 * @author LiPei
 * @since 2024-02-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("dy_fulfilment_verify_record")
public class DyFulfilmentVerifyRecordEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 企业号商家总店id
     */
    @TableField("account_id")
    private String accountId;

    /**
     * 门店id列表
     */
    @TableField("poi_id")
    private String poiId;

    /**
     * 核销记录id
     */
    @TableField("verify_id")
    private String verifyId;

    /**
     * 券id
     */
    @TableField("certificate_id")
    private String certificateId;

    /**
     * 核销时间
     */
    @TableField("verify_time")
    private LocalDateTime verifyTime;

    /**
     * 是否可撤销
     */
    @TableField("can_cancel")
    private Integer canCancel;

    /**
     * 核销类型，0默认值，1用户自验，2商家扫二维码，3商家手动输入，4开放平台API
     */
    @TableField("verify_type")
    private Integer verifyType;

    /**
     * 券码
     */
    @TableField("code")
    private String code;

    /**
     * 核销记录的状态，1表示已核销，2表示已撤销
     */
    @TableField("status")
    private Integer status;

    /**
     * 撤销时间（已撤销时返回）
     */
    @TableField("cancel_time")
    private LocalDateTime cancelTime;

    /**
     * 次卡单次券原始金额, 单位分
     */
    @TableField("original_amount")
    private Integer originalAmount;

    /**
     * 次卡单次用户实付金额, 单位分
     */
    @TableField("pay_amount")
    private Integer payAmount;

    /**
     * 次卡单次商家营销金额, 单位分
     */
    @TableField("merchant_ticket_amount")
    private Integer merchantTicketAmount;

    /**
     * 划线价，单位分
     */
    @TableField("list_market_amount")
    private Integer listMarketAmount;

    /**
     * 平台优惠金额，单位分
     */
    @TableField("platform_discount_amount")
    private Integer platformDiscountAmount;

    /**
     * 支付优惠金额，单位分
     */
    @TableField("payment_discount_amount")
    private Integer paymentDiscountAmount;

    /**
     * 券实付金额（=用户实付金额+支付优惠金额），单位分
     */
    @TableField("coupon_pay_amount")
    private Integer couponPayAmount;

    /**
     * 团购SKU ID
     */
    @TableField("sku_id")
    private String skuId;

    /**
     * 团购名称
     */
    @TableField("title")
    private String title;

    /**
     * 团购类型（type=1团餐券; type=2代金券;type=3次卡）
     */
    @TableField("groupon_type")
    private Integer grouponType;

    /**
     * 团购市场价
     */
    @TableField("market_price")
    private Integer marketPrice;

    /**
     * 团购售卖开始时间
     */
    @TableField("sold_start_time")
    private LocalDateTime soldStartTime;

    /**
     * 商家系统（第三方）团购id
     */
    @TableField("third_sku_id")
    private String thirdSkuId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 是否删除 0-否 1-是
     */
    @TableField("is_deleted")
    private Integer isDeleted;

    public static List<DyFulfilmentVerifyRecordEntity> toEntityListByResponse(String accountId, String poiId, FulfilmentVerifyRecordQueryResponse response){
        List<DyFulfilmentVerifyRecordEntity> entityList = new ArrayList<>();
        for(FulfilmentVerifyRecordResponse record : response.getRecords()){
            entityList.add(DyFulfilmentVerifyRecordEntity.toEntityByResponse(accountId,poiId,record));
        }
        return entityList;
    }

    public static DyFulfilmentVerifyRecordEntity toEntityByResponse(String accountId,String poiId,FulfilmentVerifyRecordResponse response){
        DyFulfilmentVerifyRecordEntity entity = new DyFulfilmentVerifyRecordEntity();
        entity.setAccountId(accountId);
        entity.setPoiId(poiId);
        entity.setVerifyId(response.getVerify_id());
        entity.setCertificateId(response.getCertificate_id());
        if(response.getVerify_time() != null){
            entity.setVerifyTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(response.getVerify_time()), ZoneId.systemDefault()));
        }
        entity.setCanCancel(response.getCan_cancel() != null && response.getCan_cancel() ? 1 : 0);
        entity.setVerifyType(response.getVerify_type());
        entity.setCode(response.getCode());
        entity.setStatus(response.getStatus());
        if(response.getCancel_time() != null){
            entity.setCancelTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(response.getCancel_time()), ZoneId.systemDefault()));
        }
        if(ObjectUtils.isNotEmpty(response.getAmount())){
            entity.setOriginalAmount(response.getAmount().getOriginal_amount());
            entity.setPayAmount(response.getAmount().getPay_amount());
            entity.setMerchantTicketAmount(response.getAmount().getMerchant_ticket_amount());
            entity.setListMarketAmount(response.getAmount().getList_market_amount());
            entity.setPlatformDiscountAmount(response.getAmount().getPlatform_discount_amount());
            entity.setPaymentDiscountAmount(response.getAmount().getPayment_discount_amount());
            entity.setCouponPayAmount(response.getAmount().getCoupon_pay_amount());
        }

        if(ObjectUtils.isNotEmpty(response.getSku())){
            entity.setSkuId(response.getSku().getSku_id());
            entity.setTitle(response.getSku().getTitle());
            entity.setGrouponType(response.getSku().getGroupon_type());
            entity.setMarketPrice(response.getSku().getMarket_price());
            if(response.getSku().getSold_start_time() != null){
                entity.setSoldStartTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(response.getSku().getSold_start_time()), ZoneId.systemDefault()));
            }
            entity.setThirdSkuId(response.getSku().getThird_sku_id());
        }
        return entity;
    }
}
