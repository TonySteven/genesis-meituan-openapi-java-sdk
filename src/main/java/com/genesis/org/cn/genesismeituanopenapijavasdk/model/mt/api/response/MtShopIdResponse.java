package com.genesis.org.cn.genesismeituanopenapijavasdk.model.mt.api.response;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 美团门店id返回结果
 *
 * @author steven
 * &#064;date  2023/10/20
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class MtShopIdResponse {

    /**
     * data
     */
    private List<String> data;

    public static MtShopIdResponse parse(String parseString) {
        // String 转 JsonObject
        JSONObject jsonObj = new JSONObject(parseString);

        // JsonObject 转 MtShopIdResponse
        return JSON.parseObject(jsonObj.toString()
            , MtShopIdResponse.class);


    }

}
