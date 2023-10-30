package com.genesis.org.cn.genesismeituanopenapijavasdk.model.mt.api.response.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 门店评价总评分返回结果
 *
 * @author steven
 * &#064;date  2023/10/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MtShopCommentScoreResponseData {

    /**
     * avg poi score
     */
    private int avgPoiScore;
    /**
     * avg taste score
     */
    private int avgTasteScore;
    /**
     * avg packing score
     */
    private int avgPackingScore;
    /**
     * avg delivery score
     */
    private int avgDeliveryScore;
    /**
     * delivery satisfaction
     */
    private int deliverySatisfaction;

}
