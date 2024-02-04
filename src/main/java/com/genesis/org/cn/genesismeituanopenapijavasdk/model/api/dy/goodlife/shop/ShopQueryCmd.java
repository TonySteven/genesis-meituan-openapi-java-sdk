package com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy.goodlife.shop;

import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.request.BasePageQueryRequest;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.request.goodlife.shop.ShopQueryRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询抖音门店信息请求模型
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ShopQueryCmd extends BasePageQueryRequest {

    /**
     * 本地生活商家账户 ID
     * （account_id和poi_id，二者必填其一，若都填写，account_id优先）
     */
    private String accountId;

    /**
     *抖音门店 ID
     * （account_id和poi_id，二者必填其一，若都填写，account_id优先）
     */
    private String poiId;

    public ShopQueryRequest toRequest(){
        ShopQueryRequest request = new ShopQueryRequest();
        request.setAccount_id(this.getAccountId());
        request.setPoi_id(this.getPoiId());
        request.setPage(this.getPage());
        request.setSize(this.getSize());
        return request;
    }
}
