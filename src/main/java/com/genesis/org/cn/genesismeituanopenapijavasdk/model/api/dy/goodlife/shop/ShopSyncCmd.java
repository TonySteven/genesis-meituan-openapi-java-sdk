package com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy.goodlife.shop;

import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.request.goodlife.shop.ShopQueryRequest;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy.BaseCmd;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 查询抖音门店信息请求模型
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ShopSyncCmd extends BaseCmd {

    /**
     * 抖音 APP ID
     */
    @NotBlank(message = "抖音appId不能为空")
    private String appId;

    /**
     * 本地生活商家账户 ID
     * （account_id和poi_id，二者必填其一，若都填写，account_id优先）
     */
    @NotBlank(message = "本地生活商家账户 ID不能为空")
    private String accountId;

    /**
     *抖音门店 ID
     * （account_id和poi_id，二者必填其一，若都填写，account_id优先）
     */
    private String poiId;

    public ShopQueryRequest toRequest() {
        ShopQueryRequest request = new ShopQueryRequest();
        request.setAccount_id(this.getAccountId());
        request.setPoi_id(this.getPoiId());
        return request;
    }
}
