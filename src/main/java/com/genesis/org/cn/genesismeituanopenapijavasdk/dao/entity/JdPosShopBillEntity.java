package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


/**
 * JdPosShopBill实体类
 *
 * @author 人工智能
 * @date 2024-03-22 15:28:32
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "jd_pos_shop_bill")
public class JdPosShopBillEntity {

    /**
     * id
     */
    @TableField(value = "id")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     *
     */
    @TableField(value = "BusMonth")
    private String busMonth;

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
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     *
     */
    @TableField(value = "total_pay_money")
    private BigDecimal totalPayMoney;

    /**
     *
     */
    @TableField(value = "total_income_money")
    private BigDecimal totalIncomeMoney;

    /**
     *
     */
    @TableField(value = "total_not_income_money")
    private BigDecimal totalNotIncomeMoney;

    /**
     *
     */
    @TableField(value = "timestamp")
    private Date timestamp;
}
