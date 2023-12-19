package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.bty.scm.boot.mybatis.base.BaseEntity;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * TcShopBillingDetail实体类
 *
 * @author 人工智能
 * @date 2023-12-10 16:13:44
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "tc_shop_billing_detail")
public class TcShopBillingDetailEntity extends BaseEntity {
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
     * 营业流水ID
     */
    @TableField(value = "bs_id")
    private String bsId;

    /**
     * 营业流水号
     */
    @TableField(value = "bs_code")
    private String bsCode;

    /**
     * 区域代码
     */
    @TableField(value = "area_code")
    private String areaCode;

    /**
     * 区域名称
     */
    @TableField(value = "area_name")
    private String areaName;

    /**
     * 客位代码
     */
    @TableField(value = "point_code")
    private String pointCode;

    /**
     * 客位名称
     */
    @TableField(value = "point_name")
    private String pointName;

    /**
     * 人数
     */
    @TableField(value = "people_qty")
    private Integer peopleQty;

    /**
     * 开台时间
     */
    @TableField(value = "open_time")
    private String openTime;

    /**
     * 结算时间
     */
    @TableField(value = "settle_time")
    private String settleTime;

    /**
     * 营业日
     */
    @TableField(value = "settle_biz_date")
    private String settleBizDate;

    /**
     * 0:未结（默认），1:已结（已关账），2:空帐删除（已关账），3:废单
     */
    @TableField(value = "state")
    private Integer state;

    /**
     * 服务员代码
     */
    @TableField(value = "waiter_code")
    private String waiterCode;

    /**
     * 服务员名称
     */
    @TableField(value = "waiter_name")
    private String waiterName;

    /**
     * 营销员代码
     */
    @TableField(value = "salesman_code")
    private String salesmanCode;

    /**
     * 营销员名称
     */
    @TableField(value = "salesman_name")
    private String salesmanName;

    /**
     * 品项折前金额
     */
    @TableField(value = "item_orig_money")
    private BigDecimal itemOrigMoney;

    /**
     * 品项合计金额
     */
    @TableField(value = "item_income_total")
    private BigDecimal itemIncomeTotal;

    /**
     * 服务费应收金额
     */
    @TableField(value = "orig_server_fee")
    private BigDecimal origServerFee;

    /**
     * 服务费纯收金额
     */
    @TableField(value = "service_fee_income_money")
    private BigDecimal serviceFeeIncomeMoney;

    /**
     * 服务费非纯收金额
     */
    @TableField(value = "service_fee_not_income_money")
    private BigDecimal serviceFeeNotIncomeMoney;

    /**
     * 服务费实收金额
     */
    @TableField(value = "service_fee_last_total")
    private BigDecimal serviceFeeLastTotal;

    /**
     * 最低消费补齐金额
     */
    @TableField(value = "orig_zdxfbq")
    private BigDecimal origZdxfbq;

    /**
     * 应收金额
     */
    @TableField(value = "orig_total")
    private BigDecimal origTotal;

    /**
     * 优惠合计金额
     */
    @TableField(value = "disc_total")
    private BigDecimal discTotal;

    /**
     * 折扣优惠金额
     */
    @TableField(value = "disc_money")
    private BigDecimal discMoney;

    /**
     * 定额优惠金额
     */
    @TableField(value = "quota_money")
    private BigDecimal quotaMoney;

    /**
     * 赠送优惠金额
     */
    @TableField(value = "present_money")
    private BigDecimal presentMoney;

    /**
     * 会员价优惠金额
     */
    @TableField(value = "member_money")
    private BigDecimal memberMoney;

    /**
     * 促销优惠金额
     */
    @TableField(value = "promote_money")
    private BigDecimal promoteMoney;

    /**
     * 抹零金额
     */
    @TableField(value = "wipe_money")
    private BigDecimal wipeMoney;

    /**
     * 实收金额
     */
    @TableField(value = "last_total")
    private BigDecimal lastTotal;

    /**
     * 纯收金额
     */
    @TableField(value = "income_total")
    private BigDecimal incomeTotal;

    /**
     * 非纯收金额
     */
    @TableField(value = "not_income_money")
    private BigDecimal notIncomeMoney;

    /**
     * 销售类型ID
     */
    @TableField(value = "sale_type_id")
    private String saleTypeId;

    /**
     * 销售类型名称
     */
    @TableField(value = "sale_type_name")
    private String saleTypeName;

    /**
     * 订单来源ID
     */
    @TableField(value = "order_origin_id")
    private String orderOriginId;

    /**
     * 订单来源名称
     */
    @TableField(value = "order_origin_name")
    private String orderOriginName;

    /**
     * 就餐类型
     */
    @TableField(value = "dinner_type_name")
    private String dinnerTypeName;

    /**
     * 是否挂单
     */
    @TableField(value = "is_designates")
    private String isDesignates;

    /**
     * 是否开具发票
     */
    @TableField(value = "is_give_invoice")
    private String isGiveInvoice;

    /**
     * 结算状态 1表示正常结算，-1表示返位结算，-2表示预结（即结算中）
     */
    @TableField(value = "settle_state")
    private String settleState;

    /**
     * 发票号
     */
    @TableField(value = "invoice_no")
    private String invoiceNo;

    /**
     * 发票代码
     */
    @TableField(value = "invoice_code")
    private String invoiceCode;

    /**
     * 订单类型
     */
    @TableField(value = "order_type")
    private String orderType;

    /**
     * 会员卡号
     */
    @TableField(value = "member_card_no")
    private String memberCardNo;

    /**
     * 会员ID
     */
    @TableField(value = "member_id")
    private String memberId;

    /**
     * 会员名
     */
    @TableField(value = "member_name")
    private String memberName;

    /**
     * 会员手机号
     */
    @TableField(value = "member_mobile")
    private String memberMobile;

    /**
     * 会员卡类型名称
     */
    @TableField(value = "card_kind_name")
    private String cardKindName;

    /**
     * 是否续单 true:续单；false：非续单
     */
    @TableField(value = "is_continued_bill")
    private Boolean isContinuedBill;

    /**
     * 是否续单名称
     */
    @TableField(value = "is_continued_bill_name")
    private String isContinuedBillName;

    /**
     * 税金
     */
    @TableField(value = "tax_money")
    private BigDecimal taxMoney;

    /**
     * 牌号
     */
    @TableField(value = "order_code")
    private String orderCode;

    /**
     * 所属集团代码
     */
    @TableField(value = "center_code")
    private String centerCode;

    /**
     * 所属集团名称
     */
    @TableField(value = "center_name")
    private String centerName;

    /**
     * 所属品牌代码
     */
    @TableField(value = "brand_code")
    private String brandCode;

    /**
     * 所属品牌名称
     */
    @TableField(value = "brand_name")
    private String brandName;

    /**
     * 创建店代码
     */
    @TableField(value = "shop_code")
    private String shopCode;

    /**
     * 创建店名称
     */
    @TableField(value = "shop_name")
    private String shopName;

    /**
     * 删除标记	 0表示正常（默认），1表示在回收站，2表示从回收站删除（不可恢复）
     */
    @TableField(value = "delflg")
    private Integer delflg;

    /**
     * 单据创建时间
     */
    @TableField(value = "bill_create_time")
    private Date billCreateTime;

    /**
     * 单据最后修改时间
     */
    @TableField(value = "bill_modify_time")
    private Date billModifyTime;

    /**
     * 红冲原单id
     */
    @TableField(value = "orig_wd_bs_id")
    private String origWdBsId;

    /**
     * 外卖送餐时间
     */
    @TableField(value = "delivery_time")
    private String deliveryTime;

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
