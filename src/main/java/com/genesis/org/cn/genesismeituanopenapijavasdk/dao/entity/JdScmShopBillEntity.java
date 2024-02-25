package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


/**
 * JdScmShopBill实体类
 *
 * @author 人工智能
 * &#064;date  2024-02-25 16:01:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "jd_scm_shop_bill")
public class JdScmShopBillEntity {

    /**
     * 业务时间
     */
    @TableField(value = "busDate")
    private Date busDate;

    /**
     * 单号
     */
    @TableField(value = "BillNO")
    private String BillNO;

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
     * 暂时没用
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
     * 单据类型
     */
    @TableField(value = "BillType")
    private String BillType;

    /**
     * 单据类型2
     */
    @TableField(value = "BillType2")
    private String BillType2;

    /**
     * 品项大类编码
     */
    @TableField(value = "ItemBigClassCode")
    private String ItemBigClassCode;

    /**
     * 品项大类名称
     */
    @TableField(value = "ItemBigClassname")
    private String ItemBigClassname;

    /**
     * 出库金额（成本）
     */
    @TableField(value = "OutStoreMoney")
    private BigDecimal OutStoreMoney;

    /**
     * 入库金额（成本）
     */
    @TableField(value = "InStoreMoney")
    private String InStoreMoney;

    /**
     * 税额（成本）
     */
    @TableField(value = "TaxMoney")
    private String TaxMoney;

    /**
     * 含税金额（成本）
     */
    @TableField(value = "IncludeTaxMoney")
    private String IncludeTaxMoney;

    /**
     * 是否是出库类型，0入1出
     */
    @TableField(value = "isOut")
    private String isOut;

    /**
     * 是否已生成凭证
     */
    @TableField(value = "isvoucher")
    private Integer isvoucher;

    /**
     * 凭证号
     */
    @TableField(value = "voucherNo")
    private byte[] voucherNo;

    /**
     * 供应商账期，账期类型 0或者空:不控制 1:日结，2:5天，3:周结，4:10天，5:半月，6:月结，7:季结，8:年结
     */
    @TableField(value = "accountPeriodType")
    private String accountPeriodType;

    /**
     * 错误日志
     */
    @TableField(value = "errorlog")
    private byte[] errorlog;
}
