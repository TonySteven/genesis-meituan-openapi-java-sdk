package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.base.BaseDataResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * query bill details response
 *
 * @author steven
 * &#064;date  2023/12/30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryBillDetailsResponse extends BaseDataResponse {

    /**
     * data
     */
    private QueryBillDetailsDataResponse data;

}
