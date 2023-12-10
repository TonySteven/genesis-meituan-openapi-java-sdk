package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.base.BaseDataResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * query shop info response
 *
 * @author steven
 * &#064;date  2023/12/10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryShopInfoResponse extends BaseDataResponse {

    /**
     * data
     */
    private QueryShopInfoDataResponse data;

}
