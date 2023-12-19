package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import lombok.Data;

import java.util.List;

/**
 * query bill details in real time response
 *
 * @author steven
 * &#064;date  2023/12/19
 */
@Data
public class QueryBillDetailsInRealTimeDataResponse {

    /**
     * shop bill list
     */
    private List<ShopBillItem> shopBillList;

}
