package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.bty.scm.boot.mybatis.base.BaseEntity;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * TcShopBillingPromoteDetail实体类
 *
 * @author 人工智能
 * @date 2023-12-10 16:13:44
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "tc_shop_billing_promote_detail")
public class TcShopBillingPromoteDetailEntity extends BaseEntity {
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
     * 促销方案编号
     */
    @TableField(value = "bp_id")
    private String bpId;

    /**
     * 营业流水ID
     */
    @TableField(value = "bp_code")
    private String bpCode;

    /**
     * 促销方案名称
     */
    @TableField(value = "bp_name")
    private String bpName;

    /**
     * 折扣金额
     */
    @TableField(value = "disc_money")
    private BigDecimal discMoney;

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
