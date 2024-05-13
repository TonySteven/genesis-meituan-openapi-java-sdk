package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 天才账单明细数据
 * </p>
 *
 * @author LiPei
 * @since 2024-05-13
 */
@Data
public class TcShopBillingTicketResponse {

    /**
     * 营业流水ID
     */
    private String bsId;

    /**
     * 营业流水号
     */
    private String bsCode;

    /**
     * 门店id
     */
    private String shopId;

    /**
     * 门店名称
     */
    private String shopName;

    /**
     * 平台id
     */
    private String bizType;

    /**
     * 平台名称
     */
    private String deFromName;

    /**
     * 营业日
     */
    private String settleBizDate;

    /**
     * 团券名称
     */
    private String ticketName;

    /**
     * 是否次卡
     */
    private String isTimes;

    /**
     * 券面值
     */
    private BigDecimal marketPrice;

    /**
     * 券售价
     */
    private BigDecimal price;

    /**
     * 折现率
     */
    private BigDecimal discountRate;

    /**
     * 折现金额
     */
    private BigDecimal ticketIncomeMoney;

    /**
     * 实收金额
     */
    private BigDecimal payMoney;

    /**
     * 优惠金额
     */
    private BigDecimal discMoney;

    /**
     * 纯收金额
     */
    private BigDecimal incomeMoney;

    /**
     * 纯收超收金额
     */
    private BigDecimal incomeOverChange;

    /**
     * 商家实收金额
     */
    private BigDecimal businessLast;

    /**
     * 不找零金额
     */
    private String noGiveChange;

    /**
     * 使用张数 0:未结（默认），1:已结（已关账），2:空帐删除（已关账），3:废单
     */
    private String ticketCount;

    /**
     * 核销时间
     */
    private String useTime;

    /**
     * 券码
     */
    private String ticketCode;

    /**
     * 平台核销标识
     */
    private String verifyId;

    /**
     * 核销状态
     */
    private String ticketState;

    /**
     * 结算状态
     */
    private String billState;
}
