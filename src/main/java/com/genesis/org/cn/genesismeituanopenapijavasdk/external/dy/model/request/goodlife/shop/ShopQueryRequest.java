package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.request.goodlife.shop;

import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.request.BasePageQueryRequest;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询抖音门店信息请求模型
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ShopQueryRequest extends BasePageQueryRequest implements BaseRequest {

    /**
     * 本地生活商家账户 ID
     * （account_id和poi_id，二者必填其一，若都填写，account_id优先）
     */
    private String account_id;

    /**
     *抖音门店 ID
     * （account_id和poi_id，二者必填其一，若都填写，account_id优先）
     */
    private String poi_id;

    @Override
    public String getUrl() {
        return "/goodlife/v1/shop/poi/query/";
    }
}
