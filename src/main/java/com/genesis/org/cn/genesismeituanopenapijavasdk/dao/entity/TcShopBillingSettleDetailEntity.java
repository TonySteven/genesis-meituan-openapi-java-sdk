package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bty.scm.boot.mybatis.base.BaseEntity;
import lombok.*;

import java.math.BigDecimal;


/**
 * TcShopBillingSettleDetail实体类
 *
 * @author 人工智能
 * @date 2023-12-10 16:13:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "tc_shop_billing_settle_detail")
public class TcShopBillingSettleDetailEntity extends BaseEntity {

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
     * 门店实时账单明细表id
     */
    @TableField(value = "tc_shop_billing_detail_id")
    private String tcShopBillingDetailId;

    /**
     * 结算流水ID
     */
    @TableField(value = "ts_id")
    private String tsId;

    /**
     * 所属营业流水
     */
    @TableField(value = "bs_id")
    private String bsId;

    /**
     * 结算状态 1:正常结算;-1:表示返位结算;-2:表示预结（即结算中）
     */
    @TableField(value = "settle_state")
    private Integer settleState;

    /**
     * 结算方式ID
     */
    @TableField(value = "payway_id")
    private String paywayId;

    /**
     * 结算方式代码
     */
    @TableField(value = "payway_code")
    private String paywayCode;

    /**
     * 结算方式名称
     */
    @TableField(value = "payway_name")
    private String paywayName;

    /**
     * 支付金额（实收）
     */
    @TableField(value = "pay_money")
    private BigDecimal payMoney;

    /**
     * 收入金额(纯收)
     */
    @TableField(value = "income_money")
    private BigDecimal incomeMoney;

    /**
     * 非收入金额
     */
    @TableField(value = "not_income_money")
    private BigDecimal notIncomeMoney;

    /**
     * 会员手机号
     */
    @TableField(value = "crm_mobile")
    private String crmMobile;

    /**
     * 会员卡号
     */
    @TableField(value = "card_no")
    private String cardNo;

    /**
     * 删除标记	 0表示正常（默认），1表示在回收站，2表示从回收站删除（不可恢复）
     */
    @TableField(value = "delflg")
    private Integer delflg;

    /**
     * 宴请原因ID
     */
    @TableField(value = "feteId")
    private String feteId;

    /**
     * 宴请原因Code
     */
    @TableField(value = "feteCode")
    private String feteCode;

    /**
     * 宴请原因名称
     */
    @TableField(value = "feteName")
    private String feteName;

    /**
     * 宴请原因备注
     */
    @TableField(value = "feteRemark")
    private String feteRemark;

    /**
     * 是否删除
     */
    @TableField(value = "is_deleted")
    private Boolean isDeleted;

    /**
     * 备注
     */
    @TableField(value = "remarks")
    private String remarks;
}
