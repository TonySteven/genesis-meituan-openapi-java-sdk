package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.base.BasePageInfo;
import lombok.Data;

import java.util.List;

/**
 * query shop info data response
 *
 * @author steven
 * &#064;date  2023/12/10
 */
@Data
public class QueryShopInfoDataResponse {

    /**
     * shop list
     */
    private List<QueryShopInfoDataResponseShopList> shopList;

    /**
     * shop list
     */
    private BasePageInfo pageInfo;

}
