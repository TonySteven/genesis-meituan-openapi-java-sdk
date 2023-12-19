package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * settle detail
 *
 * @author steven
 * &#064;date  2023/12/19
 */
@Data
public class SettleDetail {

    /**
     * ts id
     */
    private String tsId;
    /**
     * payway code
     */
    private String paywayCode;
    /**
     * payway id
     */
    private String paywayId;
    /**
     * modify time
     */
    private String modifyTime;
    /**
     * payway name
     */
    private String paywayName;
    /**
     * pay money
     */
    private BigDecimal payMoney;
    /**
     * income money
     */
    private BigDecimal incomeMoney;
    /**
     * delflg
     */
    private int delflg;
    /**
     * settle state
     */
    private int settleState;
    /**
     * pw id
     */
    private String pwId;
    /**
     * payway out code
     */
    private String paywayOutCode;
    /**
     * bs id
     */
    private String bsId;
    /**
     * consumer name
     */
    private String consumerName;
    /**
     * card no
     */
    private String cardNo;
    /**
     * consumer code
     */
    private String consumerCode;
    /**
     * crm mobile
     */
    private String crmMobile;
    /**
     * not income money
     */
    private BigDecimal notIncomeMoney;

}
