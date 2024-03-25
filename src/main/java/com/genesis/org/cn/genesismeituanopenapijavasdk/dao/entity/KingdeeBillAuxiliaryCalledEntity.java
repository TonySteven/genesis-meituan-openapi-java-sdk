package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * KingdeeBillAuxiliaryCalled实体类
 *
 * @author 人工智能
 * &#064;date  2024-03-16 13:01:54
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "kingdee_bill_auxiliary_called")
public class KingdeeBillAuxiliaryCalledEntity {
    /**
     * id
     */
    @TableField(value = "id")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 集团id
     */
    @TableField(value = "company_id")
    private String companyId;

    /**
     * jd_scm_shop_bill.no
     */
    @TableField(value = "jd_scm_shop_bill_no")
    private String jdScmShopBillNo;

    /**
     * 金蝶单据id
     */
    @TableField(value = "kingdee_bill_id")
    private String kingdeeBillId;

    /**
     * 金蝶单据number
     */
    @TableField(value = "kingdee_bill_number")
    private String kingdeeBillNumber;

    /**
     * 金蝶回调错误
     */
    @TableField(value = "kingdee_bill_error_msg")
    private String kingdeeBillErrorMsg;

    /**
     * 回调状态 0失败1成功
     */
    @TableField(value = "status")
    private Integer status;

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
    @TableLogic(delval = "1", value = "0")
    private Integer isDeleted;

    /**
     * 备注
     */
    @TableField(value = "remarks")
    private String remarks;
}

