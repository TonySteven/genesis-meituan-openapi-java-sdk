package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * TcShopBillingFullOffDetail实体类
 *
 * @author 人工智能
 * &#064;date  2023-12-10 16:13:44
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "tc_shop_billing_full_off_detail")
public class TcShopBillingFullOffDetailEntity {
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
     * 营业流水ID
     */
    @TableField(value = "bs_id")
    private String bsId;

    /**
     * 营业流水ID
     */
    @TableField(value = "disc_id")
    private String discId;

    /**
     * 折扣类型 0:无优惠;1:比例优惠;2:打折方案;3:单品优惠;4:全单优惠;5:类别优惠-1:手动定额优惠;-2:一卡通（会员优惠）;-3:满减定额优惠方案;-4:每满减定额优惠方案;-5:线上优惠
     */
    @TableField(value = "disc_type")
    private String discType;

    /**
     * 满减定额优惠ID
     */
    @TableField(value = "fo_id")
    private String foId;

    /**
     * 满减定额优惠编号
     */
    @TableField(value = "fo_code")
    private String foCode;

    /**
     * 满减定额优惠名称
     */
    @TableField(value = "fo_name")
    private String foName;

    /**
     * 折扣比例
     */
    @TableField(value = "disc_scale")
    private String discScale;

    /**
     * 折扣金额
     */
    @TableField(value = "disc_money")
    private BigDecimal discMoney;

    /**
     * 删除标记	 0表示正常（默认），1表示在回收站，2表示从回收站删除（不可恢复）
     */
    @TableField(value = "delflg")
    private Integer delflg;

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
