package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.dy;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.goodlife.settle_ledger.SettleLedgerRecordQueryResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.goodlife.settle_ledger.SettleLedgerRecordResponse;
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
 * 分账明细记录
 * @date : 2024-02-21 10:11:48
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("dy_settle_ledger_record")
public class DySettleLedgerRecordEntity implements Serializable {

    /** id */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 企业号商家总店id */
    @TableField("account_id")
    private String accountId;

    /** 门店id列表 */
    @TableField("poi_id")
    private String poiId;

    /** 分账记录id */
    @TableField("ledger_id")
    private String ledgerId;

    /** 核销记录id */
    @TableField("verify_id")
    private String verifyId;

    /** 券id */
    @TableField("certificate_id")
    private String certificateId;

    /** 订单id */
    @TableField("order_id")
    private String orderId;

    /** 核销时间 */
    @TableField("verify_time")
    private LocalDateTime verifyTime;

    /** 分账单状态，0表示初始化、1表示分账中、2表示分账成功、3表示分账失败、4表示分账单已废弃 */
    @TableField("status")
    private Integer status;

    /** 券码 */
    @TableField("code")
    private String code;

    /** 券原始金额，单位分 */
    @TableField("original")
    private Integer original;

    /** 用户实付金额，单位分 */
    @TableField("pay")
    private Integer pay;

    /** 商家营销金额，单位分 */
    @TableField("merchant_ticket")
    private Integer merchantTicket;

    /** 实付保险费用。由保司收取，单位分 */
    @TableField("actual_insured")
    private Integer actualInsured;

    /** 商家货款金额，单位分  （商家实际收到金额，已经扣除支付手续费和佣金费用） */
    @TableField("goods")
    private Integer goods;

    /** 支付手续费，单位分  （平台收取的千分之六支付手续费） */
    @TableField("pay_handling")
    private Integer payHandling;

    /** 达人佣金，单位分  （团购上设置或者服务商引入的才会有佣金） */
    @TableField("talent_commission")
    private Integer talentCommission;

    /** 服务商分佣总金额，单位分 */
    @TableField("total_agent_merchant")
    private Integer totalAgentMerchant;

    /** 软件服务费。已包含支付手续费，单位：分 */
    @TableField("total_merchant_platform_service")
    private Integer totalMerchantPlatformService;

    /** 团购sku  id */
    @TableField("sku_id")
    private String skuId;

    /** 团购名称 */
    @TableField("title")
    private String title;

    /** 团购市场价 */
    @TableField("market_price")
    private Integer marketPrice;

    /** 团购售卖开始时间 */
    @TableField("sold_start_time")
    private LocalDateTime soldStartTime;

    /** 创建时间 */
    @TableField("create_time")
    private LocalDateTime createTime;

    /** 修改时间 */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /** 是否删除  0-否  1-是 */
    @TableField("is_deleted")
    private Integer isDeleted;

    public static List<DySettleLedgerRecordEntity> toEntityListByResponse(String accountId, String poiId, SettleLedgerRecordQueryResponse response){
        List<DySettleLedgerRecordEntity> entityList = new ArrayList<>();
        for(SettleLedgerRecordResponse record : response.getRecords()){
            entityList.add(DySettleLedgerRecordEntity.toEntityByResponse(accountId,poiId,record));
        }
        return entityList;
    }

    public static DySettleLedgerRecordEntity toEntityByResponse(String accountId,String poiId,SettleLedgerRecordResponse response){
        DySettleLedgerRecordEntity entity = new DySettleLedgerRecordEntity();
        entity.setAccountId(accountId);
        entity.setPoiId(poiId);
        entity.setLedgerId(response.getLedger_id());
        entity.setVerifyId(response.getVerify_id());
        entity.setCertificateId(response.getCertificate_id());
        entity.setOrderId(response.getOrder_id());
        if(response.getVerify_time() != null){
            entity.setVerifyTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(response.getVerify_time()), ZoneId.systemDefault()));
        }
        entity.setStatus(response.getStatus());
        entity.setCode(response.getCode());

        if(ObjectUtils.isNotEmpty(response.getAmount())){
            entity.setOriginal(response.getAmount().getOriginal());
            entity.setPay(response.getAmount().getPay());
            entity.setMerchantTicket(response.getAmount().getMerchant_ticket());
            entity.setActualInsured(response.getAmount().getActual_insured());
        }

        if(ObjectUtils.isNotEmpty(response.getFund_amount())){
            entity.setGoods(response.getFund_amount().getGoods());
            entity.setPayHandling(response.getFund_amount().getPay_handling());
            entity.setTalentCommission(response.getFund_amount().getTalent_commission());
            entity.setTotalAgentMerchant(response.getFund_amount().getTotal_agent_merchant());
            entity.setTotalMerchantPlatformService(response.getFund_amount().getTotal_merchant_platform_service());
        }

        if(ObjectUtils.isNotEmpty(response.getSku())){
            entity.setSkuId(response.getSku().getSku_id());
            entity.setTitle(response.getSku().getTitle());
            entity.setMarketPrice(response.getSku().getMarket_price());
            if(response.getSku().getSold_start_time() != null){
                entity.setSoldStartTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(response.getSku().getSold_start_time()), ZoneId.systemDefault()));
            }
        }

        return entity;
    }

}
