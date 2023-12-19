package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.base.BaseDataResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * query bill details in real time response
 *
 * @author steven
 * &#064;date  2023/12/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryBillDetailsInRealTimeResponse extends BaseDataResponse {

    /**
     * data
     */
    private QueryBillDetailsInRealTimeDataResponse data;

}
