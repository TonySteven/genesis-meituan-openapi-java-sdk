package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.goodlife.shop;

import lombok.Data;

/**
 * 抖音门店信息响应模型
 */
@Data
public class ShopPoiResponse {

    /**
     * 抖音门店 ID
     */
    private String poi_id;

    /**
     * 抖音门店名称
     */
    private String poi_name;

    /**
     * 门店地址
     */
    private String address;

    /**
     * 门店坐标 - 纬度
     */
    private String latitude;

    /**
     * 门店坐标 - 经度
     */
    private String longitude;
}
