package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;


/**
 * TcShopBillingErrorInfo实体类
 *
 * @author 人工智能
 * @date 2024-04-07 09:55:27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "tc_shop_billing_error_info")
public class TcShopBillingErrorInfoEntity {

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
     * 错误信息
     */
    @TableField(value = "error_msg")
    private String errorMsg;

    /**
     * 账单开始日期
     */
    @TableField(value = "beginDate")
    private Date beginDate;

    /**
     * 账单结束日期
     */
    @TableField(value = "endDate")
    private Date endDate;

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
