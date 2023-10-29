package com.genesis.org.cn.genesismeituanopenapijavasdk.model.mt.api.response.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 门店评价返回结果
 *
 * @author steven
 * &#064;date  2023/10/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MtShopCommentResponseData {

    /**
     * result
     */
    private String result;
    /**
     * comment id
     */
    private String commentId;
    /**
     * comment content
     */
    private String commentContent;
    /**
     * order comment score
     */
    private int orderCommentScore;
    /**
     * food comment score
     */
    private int foodCommentScore;
    /**
     * delivery comment score
     */
    private int deliveryCommentScore;
    /**
     * add comment
     */
    private String addComment;
    /**
     * add comment time
     */
    private String addCommentTime;
    /**
     * packing score
     */
    private int packingScore;
    /**
     * comment time
     */
    private String commentTime;
    /**
     * comment lables
     */
    private String commentLables;
    /**
     * ship time
     */
    private int shipTime;
    /**
     * comment pictures
     */
    private String commentPictures;
    /**
     * praise food list
     */
    private String praiseFoodList;
    /**
     * critic food list
     */
    private String criticFoodList;
    /**
     * reply status
     */
    private int replyStatus;
    /**
     * comment order detail
     */
    private List<String> commentOrderDetail;
    /**
     * over delivery time desc
     */
    private String overDeliveryTimeDesc;
    /**
     * e reply content
     */
    private String eReplyContent;
    /**
     * e reply time
     */
    private String eReplyTime;
    /**
     * comment type
     */
    private int commentType;
    /**
     * valid
     */
    private int valid;

}
