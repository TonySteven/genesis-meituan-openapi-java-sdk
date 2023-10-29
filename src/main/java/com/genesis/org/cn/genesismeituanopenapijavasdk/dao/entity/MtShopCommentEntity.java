package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bty.scm.boot.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * MtShopComment实体类
 *
 * @author 人工智能
 * &#064;date  2023-10-29 15:12:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "mt_shop_comment")
public class MtShopCommentEntity extends BaseEntity {

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
    private Integer commentId;

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
    private Integer addComment;

    /**
     * 新增评论时间
     */
    @TableField(value = "add_comment_time")
    private Integer addCommentTime;

    /**
     * 包装打分
     */
    @TableField(value = "packing_score")
    private Integer packingScore;

    /**
     * 评论时间
     */
    @TableField(value = "comment_time")
    private Date commentTime;

    /**
     * 评论标签
     */
    @TableField(value = "comment_lables")
    private Integer commentLables;

    /**
     * 送达时间
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
    private Integer praiseFoodList;

    /**
     * 吐槽食物列表
     */
    @TableField(value = "critic_food_list")
    private Integer criticFoodList;

    /**
     * 回复状态
     */
    @TableField(value = "reply_status")
    private Integer replyStatus;

    /**
     * 超时原因描述
     */
    @TableField(value = "over_delivery_time_desc")
    private Integer overDeliveryTimeDesc;

    /**
     * 商家回复内容
     */
    @TableField(value = "e_reply_content")
    private String eReplyContent;

    /**
     * 商家回复时间
     */
    @TableField(value = "e_reply_time")
    private Date eReplyTime;

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
