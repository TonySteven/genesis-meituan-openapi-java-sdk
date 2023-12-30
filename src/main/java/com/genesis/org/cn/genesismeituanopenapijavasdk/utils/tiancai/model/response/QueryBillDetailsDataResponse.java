package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.base.BasePageInfo;
import lombok.Data;

import java.util.List;

/**
 * query bill details data response
 *
 * @author steven
 * &#064;date  2023/12/30
 */
@Data
public class QueryBillDetailsDataResponse {

    /**
     * bill list
     */
    private List<BillListItem> billList;

    /**
     * shop list
     */
    private BasePageInfo pageInfo;

}
