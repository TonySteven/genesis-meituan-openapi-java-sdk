package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.DateUtils;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcShopBillingTicketResponse;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 天才账单明细数据
 * </p>
 *
 * @author LiPei
 * @since 2024-05-13
 */
@Data
@TableName("tc_shop_billing_ticket")
public class TcShopBillingTicketEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 营业流水ID
     */
    @TableField("bs_id")
    private String bsId;

    /**
     * 营业流水号
     */
    @TableField("bs_code")
    private String bsCode;

    /**
     * 集团ID
     */
    @TableField("center_id")
    private String centerId;

    /**
     * 门店id
     */
    @TableField("shop_id")
    private String shopId;

    /**
     * 门店名称
     */
    @TableField("shop_name")
    private String shopName;

    /**
     * 平台id
     */
    @TableField("biz_type")
    private String bizType;

    /**
     * 平台名称
     */
    @TableField("de_from_name")
    private String deFromName;

    /**
     * 营业日
     */
    @TableField("settle_biz_date")
    private LocalDateTime settleBizDate;

    /**
     * 团券名称
     */
    @TableField("ticket_name")
    private String ticketName;

    /**
     * 是否次卡
     */
    @TableField("is_times")
    private String isTimes;

    /**
     * 券面值
     */
    @TableField("market_price")
    private BigDecimal marketPrice;

    /**
     * 券售价
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 折现率
     */
    @TableField("discount_rate")
    private BigDecimal discountRate;

    /**
     * 折现金额
     */
    @TableField("ticket_income_money")
    private BigDecimal ticketIncomeMoney;

    /**
     * 实收金额
     */
    @TableField("pay_money")
    private BigDecimal payMoney;

    /**
     * 优惠金额
     */
    @TableField("disc_money")
    private BigDecimal discMoney;

    /**
     * 纯收金额
     */
    @TableField("income_money")
    private BigDecimal incomeMoney;

    /**
     * 纯收超收金额
     */
    @TableField("income_over_change")
    private BigDecimal incomeOverChange;

    /**
     * 商家实收金额
     */
    @TableField("business_last")
    private BigDecimal businessLast;

    /**
     * 不找零金额
     */
    @TableField("no_give_change")
    private String noGiveChange;

    /**
     * 使用张数 0:未结（默认），1:已结（已关账），2:空帐删除（已关账），3:废单
     */
    @TableField("ticket_count")
    private String ticketCount;

    /**
     * 核销时间
     */
    @TableField("use_time")
    private LocalDateTime useTime;

    /**
     * 券码
     */
    @TableField("ticket_code")
    private String ticketCode;

    /**
     * 平台核销标识
     */
    @TableField("verify_id")
    private String verifyId;

    /**
     * 核销状态
     */
    @TableField("ticket_state")
    private String ticketState;

    /**
     * 结算状态
     */
    @TableField("bill_state")
    private String billState;

    public static List<TcShopBillingTicketEntity> toEntityListByResponse(String centerId,List<TcShopBillingTicketResponse> responseList)
    {
        return responseList.stream().map(response -> toEntityByResponse(centerId,response)).toList();
    }


    public static TcShopBillingTicketEntity toEntityByResponse(String centerId,TcShopBillingTicketResponse response)
    {
        TcShopBillingTicketEntity entity = new TcShopBillingTicketEntity();
        entity.setBizType(response.getBizType());
        entity.setCenterId(centerId);
        entity.setDeFromName(response.getDeFromName());
        entity.setDiscMoney(response.getDiscMoney());
        entity.setDiscountRate(response.getDiscountRate());
        entity.setIncomeMoney(response.getIncomeMoney());
        entity.setIncomeOverChange(response.getIncomeOverChange());
        entity.setBusinessLast(response.getBusinessLast());
        entity.setNoGiveChange(response.getNoGiveChange());
        entity.setPayMoney(response.getPayMoney());
        entity.setMarketPrice(response.getMarketPrice());
        entity.setPrice(response.getPrice());
        entity.setShopId(response.getShopId());
        entity.setShopName(response.getShopName());
        entity.setSettleBizDate(DateUtils.getLocalDateTime(response.getSettleBizDate()));
        entity.setTicketCode(response.getTicketCode());
        entity.setTicketIncomeMoney(response.getTicketIncomeMoney());
        entity.setTicketName(response.getTicketName());
        entity.setTicketState(response.getTicketState());
        entity.setUseTime(DateUtils.getLocalDateTime(response.getUseTime()));
        entity.setVerifyId(response.getVerifyId());
        entity.setBillState(response.getBillState());
        entity.setBsCode(response.getBsCode());
        entity.setBsId(response.getBsId());
        entity.setIsTimes(response.getIsTimes());
        return entity;
    }
}
