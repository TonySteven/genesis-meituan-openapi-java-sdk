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
 * JdScmShopBillPz实体类
 *
 * @author 人工智能
 * @date 2024-03-17 14:34:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "jd_scm_shop_bill_pz")
public class JdScmShopBillPzEntity {

    /**
     * id
     */
    @TableField(value = "id")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 业务月份
     */
    @TableField(value = "BusMonth")
    private String BusMonth;

    /**
     * 门店id
     */
    @TableField(value = "ShopID")
    private String ShopID;

    /**
     * 门店编码
     */
    @TableField(value = "ShopCode")
    private String ShopCode;

    /**
     *
     */
    @TableField(value = "FAccountBookID")
    private byte[] FAccountBookID;

    /**
     * 机构名称
     */
    @TableField(value = "ShopName")
    private String ShopName;

    /**
     * 对方机构编码
     */
    @TableField(value = "OtherSideCode")
    private String OtherSideCode;

    /**
     * 对方机构名称
     */
    @TableField(value = "OtherSideName")
    private String OtherSideName;

    /**
     * 对方机构类型
     */
    @TableField(value = "OtherSideType")
    private String OtherSideType;

    /**
     *
     */
    @TableField(value = "billTypeId")
    private String billTypeId;

    /**
     *
     */
    @TableField(value = "BillBusTypeId")
    private String BillBusTypeId;

    /**
     * 单据类型
     */
    @TableField(value = "BillType")
    private String BillType;

    /**
     *
     */
    @TableField(value = "BillType2")
    private String BillType2;

    /**
     * 品项大类编码
     */
    @TableField(value = "FinancetypeCode")
    private String FinancetypeCode;

    /**
     * 品项大类名称
     */
    @TableField(value = "FinanceTypeName")
    private String FinanceTypeName;

    /**
     *
     */
    @TableField(value = "totalstoremoney")
    private BigDecimal totalstoremoney;

    /**
     *
     */
    @TableField(value = "totalTaxMoney")
    private BigDecimal totalTaxMoney;

    /**
     *
     */
    @TableField(value = "totalIncludeTaxMoney")
    private BigDecimal totalIncludeTaxMoney;

    /**
     *
     */
    @TableField(value = "isvoucher")
    private Integer isvoucher;

    /**
     *
     */
    @TableField(value = "voucherNo")
    private byte[] voucherNo;

    /**
     *
     */
    @TableField(value = "accountPeriodType")
    private String accountPeriodType;

    /**
     *
     */
    @TableField(value = "errorlog")
    private byte[] errorlog;

    /**
     *
     */
    @TableField(value = "timestamp")
    private Date timestamp;
}
