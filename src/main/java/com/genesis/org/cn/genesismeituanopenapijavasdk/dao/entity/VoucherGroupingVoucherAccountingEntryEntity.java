package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * VoucherGroupingVoucherAccountingEntry实体类
 *
 * @author 人工智能
 * @date 2024-03-17 16:07:10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "voucher_grouping_voucher_accounting_entry")
public class VoucherGroupingVoucherAccountingEntryEntity {

    /**
     * 唯一id
     */
    @TableField(value = "lmnId")
    private String lmnId;

    /**
     * 集团id
     */
    @TableField(value = "company_id")
    private String companyId;

    /**
     * 品牌id
     */
    @TableField(value = "brand_id")
    private String brandId;

    /**
     * 凭证分组id
     */
    @TableField(value = "v_lmnid")
    private String vLmnid;

    /**
     * 单据类型id
     */
    @TableField(value = "item_type_id")
    private String itemTypeId;

    /**
     * 单据类型
     */
    @TableField(value = "item_type_name")
    private String itemTypeName;

    /**
     * 凭证详情id
     */
    @TableField(value = "d_lmnid")
    private String dLmnid;

    /**
     * 过滤条件 | 改成分组条件
     */
    @TableField(value = "filtration")
    private String filtration;

    /**
     * 摘要
     */
    @TableField(value = "abstract")
    private String abstractString;

    /**
     * 凭证分组id
     */
    @TableField(value = "subject_code")
    private String subjectCode;

    /**
     * 科目名称
     */
    @TableField(value = "subject")
    private String subject;

    /**
     * 核算维度
     */
    @TableField(value = "accounts")
    private String accounts;

    /**
     * 取值金额
     */
    @TableField(value = "amount")
    private String amount;

    /**
     * 借贷标记
     */
    @TableField(value = "debit")
    private Integer debit;

    /**
     * 凭证方向
     */
    @TableField(value = "direction")
    private Integer direction;

    /**
     * 凭证类型
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 状态
     */
    @TableField(value = "status")
    private Integer status;

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
