package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * VoucherCalsstype实体类
 *
 * @author 人工智能
 * &#064;date  2024-02-25 18:03:15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "voucher_calsstype")
public class VoucherClassTypeEntity {

    /**
     *
     */
    @TableField(value = "ItemBigClassCode")
    private String ItemBigClassCode;

    /**
     *
     */
    @TableField(value = "calsscode")
    private String classcode;

    /**
     *
     */
    @TableField(value = "classname")
    private String classname;

    /**
     *
     */
    @TableField(value = "billtype")
    private String billtype;

    /**
     *
     */
    @TableField(value = "billtypecode")
    private String billtypecode;

    /**
     *
     */
    @TableField(value = "type")
    private String type;

    /**
     *
     */
    @TableField(value = "amountcode")
    private String amountcode;
}
