package com.genesis.org.cn.genesismeituanopenapijavasdk.model.mt.api.response;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.mt.api.response.model.MtShopCommentResponseData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 美团门店评价返回结果
 *
 * @author steven
 * &#064;date  2023/10/20
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class MtShopCommentResponse {

    /**
     * data
     */
    private List<MtShopCommentResponseData> data;

    public static MtShopCommentResponse parse(String poiCommentSting) {
        // String 转 JsonObject
        JSONObject jsonObj = new JSONObject(poiCommentSting);

        // JsonObject 转 MtShopCommentResponse
        return JSON.parseObject(jsonObj.toString()
            , MtShopCommentResponse.class);


    }
}
