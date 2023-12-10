package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.bty.scm.boot.mybatis.base.BaseEntity;
import lombok.*;

import java.time.LocalDateTime;


/**
 * TcShopGroupInfo实体类
 *
 * @author 人工智能
 * @date 2023-12-10 16:13:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "tc_shop_group_info")
public class TcShopGroupInfoEntity extends BaseEntity {
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
     * 本地天财门店详情表id
     */
    @TableField(value = "tc_shop_id")
    private String tcShopId;

    /**
     * 一级分组id
     */
    @TableField(value = "one_level_id")
    private String oneLevelId;

    /**
     * 一级分组code
     */
    @TableField(value = "one_level_code")
    private String oneLevelCode;

    /**
     * 一级分组名称
     */
    @TableField(value = "one_level_name")
    private String oneLevelName;

    /**
     * 二级分组id
     */
    @TableField(value = "two_level_id")
    private String twoLevelId;

    /**
     * 二级分组code
     */
    @TableField(value = "two_level_code")
    private String twoLevelCode;

    /**
     * 二级分组名称
     */
    @TableField(value = "two_level_name")
    private String twoLevelName;

    /**
     * 三级分组id
     */
    @TableField(value = "three_level_id")
    private String threeLevelId;

    /**
     * 三级分组code
     */
    @TableField(value = "three_level_code")
    private String threeLevelCode;

    /**
     * 三级分组名称
     */
    @TableField(value = "three_level_name")
    private String threeLevelName;

    /**
     * 四级分组id
     */
    @TableField(value = "four_level_id")
    private String fourLevelId;

    /**
     * 四级分组code
     */
    @TableField(value = "four_level_code")
    private String fourLevelCode;

    /**
     * 四级分组名称
     */
    @TableField(value = "four_level_name")
    private String fourLevelName;

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
