package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * TcShopBillingDetailItem实体类
 *
 * @author 人工智能
 * @date 2023-12-10 16:13:44
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "tc_shop_billing_detail_item")
public class TcShopBillingDetailItemEntity {
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
     * 门店实时账单明细表id
     */
    @TableField(value = "tc_shop_billing_detail_id")
    private String tcShopBillingDetailId;

    /**
     * 服务ID
     */
    @TableField(value = "sc_id")
    private String scId;

    /**
     * 品项ID
     */
    @TableField(value = "item_id")
    private String itemId;

    /**
     * 品项名称
     */
    @TableField(value = "item_name")
    private String itemName;

    /**
     * 品项单位
     */
    @TableField(value = "unit_name")
    private String unitName;

    /**
     * 品项规格Id
     */
    @TableField(value = "size_id")
    private String sizeId;

    /**
     * 品项规格
     */
    @TableField(value = "size_name")
    private String sizeName;

    /**
     * 最终单价
     */
    @TableField(value = "last_price")
    private BigDecimal lastPrice;

    /**
     * 是否变过价
     */
    @TableField(value = "is_change_price")
    private String isChangePrice;

    /**
     * 最终制作费用（小计）
     */
    @TableField(value = "last_make_fee")
    private BigDecimal lastMakeFee;

    /**
     * 数量
     */
    @TableField(value = "last_qty")
    private BigDecimal lastQty;

    /**
     * 折前小计（应收）
     */
    @TableField(value = "orig_subtotal")
    private BigDecimal origSubtotal;

    /**
     * 折扣金额
     */
    @TableField(value = "disc_money")
    private BigDecimal discMoney;

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
     * 定额优惠
     */
    @TableField(value = "disc_fix")
    private BigDecimal discFix;

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
     * 品项类型
     */
    @TableField(value = "item_type")
    private Integer itemType;

    /**
     * 套餐标志 0：正常品项;1：套餐；2：套餐内品项
     */
    @TableField(value = "pkg_flg")
    private Integer pkgFlg;

    /**
     * 套餐标志名称
     */
    @TableField(value = "pkg_flg_name")
    private String pkgFlgName;

    /**
     * 套餐明细所属的服务内容 该字段若不为空，则对应相应品项消费明细的sc_id（即主套餐）
     */
    @TableField(value = "pkg_sc_id")
    private String pkgScId;

    /**
     * 0：无；1：赠；2：折；3、变；4、促；5、会
     */
    @TableField(value = "disc_flg")
    private Integer discFlg;

    /**
     * 优惠类型名称
     */
    @TableField(value = "disc_name")
    private String discName;

    /**
     * 赠单原因
     */
    @TableField(value = "rz_name")
    private String rzName;

    /**
     * 删除标记	 0表示正常（默认），1表示在回收站，2表示从回收站删除（不可恢复）
     */
    @TableField(value = "delflg")
    private Integer delflg;

    /**
     * 品项小类ID
     */
    @TableField(value = "small_class_id")
    private String smallClassId;

    /**
     * 品项小类编号
     */
    @TableField(value = "small_class_code")
    private String smallClassCode;

    /**
     * 品项小类名称
     */
    @TableField(value = "small_class_name")
    private String smallClassName;

    /**
     * 品项大类ID
     */
    @TableField(value = "big_class_id")
    private String bigClassId;

    /**
     * 品项大类编号
     */
    @TableField(value = "big_class_code")
    private String bigClassCode;

    /**
     * 品项小类名称
     */
    @TableField(value = "big_class_name")
    private String bigClassName;

    /**
     * 品项大类名称
     */
    @TableField(value = "salesman_name")
    private String salesmanName;

    /**
     * 品项小类店内税率
     */
    @TableField(value = "tax_rate_dinein")
    private BigDecimal taxRateDinein;

    /**
     * 品项小类外卖税率
     */
    @TableField(value = "tax_rate_takeout")
    private BigDecimal taxRateTakeout;

    /**
     * 品项小类外带税率
     */
    @TableField(value = "tax_rate_takesale")
    private BigDecimal taxRateTakesale;

    /**
     * 做法ID
     */
    @TableField(value = "method_id")
    private String methodId;

    /**
     * 做法描述
     */
    @TableField(value = "method_text")
    private String methodText;

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
     * 毛利部门ID
     */
    @TableField(value = "profitDeptId")
    private String profitDeptId;

    /**
     * 毛利部门编号
     */
    @TableField(value = "profitDeptCode")
    private String profitDeptCode;

    /**
     * 毛利部门名称
     */
    @TableField(value = "profitDeptName")
    private String profitDeptName;

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
