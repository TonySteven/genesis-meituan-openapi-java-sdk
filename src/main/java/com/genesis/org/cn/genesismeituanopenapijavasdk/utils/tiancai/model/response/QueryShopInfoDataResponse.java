package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.base.BaseDataResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.base.BasePageInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * query shop info data response
 *
 * @author steven
 * &#064;date  2023/12/10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryShopInfoDataResponse extends BaseDataResponse {

    /**
     * shop list
     */
    private List<QueryShopInfoDataResponseShopList> shopList;

    /**
     * shop list
     */
    private BasePageInfo pageInfo;

}
