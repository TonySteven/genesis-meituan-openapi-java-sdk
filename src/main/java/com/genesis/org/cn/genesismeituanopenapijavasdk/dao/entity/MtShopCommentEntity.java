package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.bty.scm.boot.mybatis.base.BaseEntity;
import lombok.*;

import java.time.LocalDateTime;


/**
 * MtShopComment实体类
 *
 * @author 人工智能
 * &#064;date  2023-10-29 15:12:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "mt_shop_comment")
public class MtShopCommentEntity extends BaseEntity {

    /**
     * id
     */
    @TableField(value = "id")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 品牌
     */
    @TableField(value = "brand")
    private String brand;

    /**
     * 美团门店id
     */
    @TableField(value = "shop_id")
    private String shopId;

    /**
     * 结果
     */
    @TableField(value = "result")
    private String result;

    /**
     * 评论id
     */
    @TableField(value = "comment_id")
    private String commentId;

    /**
     * 评论内容
     */
    @TableField(value = "comment_content")
    private String commentContent;

    /**
     * 订单评分
     */
    @TableField(value = "order_comment_score")
    private Integer orderCommentScore;

    /**
     * 食物评分
     */
    @TableField(value = "food_comment_score")
    private Integer foodCommentScore;

    /**
     * 快递评分
     */
    @TableField(value = "delivery_comment_score")
    private Integer deliveryCommentScore;

    /**
     * 新增评论
     */
    @TableField(value = "add_comment")
    private String addComment;

    /**
     * 新增评论时间
     */
    @TableField(value = "add_comment_time")
    private String addCommentTime;

    /**
     * 包装打分
     */
    @TableField(value = "packing_score")
    private Integer packingScore;

    /**
     * 评论时间
     */
    @TableField(value = "comment_time")
    private String commentTime;

    /**
     * 评论标签
     */
    @TableField(value = "comment_lables")
    private String commentLables;

    /**
     * 次数
     */
    @TableField(value = "ship_time")
    private Integer shipTime;

    /**
     * 评论图片
     */
    @TableField(value = "comment_pictures")
    private String commentPictures;

    /**
     * 点赞食物列表
     */
    @TableField(value = "praise_food_list")
    private String praiseFoodList;

    /**
     * 吐槽食物列表
     */
    @TableField(value = "critic_food_list")
    private String criticFoodList;

    /**
     * 回复状态
     */
    @TableField(value = "reply_status")
    private Integer replyStatus;

    /**
     * 超时原因描述
     */
    @TableField(value = "over_delivery_time_desc")
    private String overDeliveryTimeDesc;

    /**
     * 商家回复内容
     */
    @TableField(value = "e_reply_content")
    private String eReplyContent;

    /**
     * 商家回复时间
     */
    @TableField(value = "e_reply_time")
    private String eReplyTime;

    /**
     * 评论类型
     */
    @TableField(value = "comment_type")
    private Integer commentType;

    /**
     * 是否有效
     */
    @TableField(value = "valid")
    private Integer valid;

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
