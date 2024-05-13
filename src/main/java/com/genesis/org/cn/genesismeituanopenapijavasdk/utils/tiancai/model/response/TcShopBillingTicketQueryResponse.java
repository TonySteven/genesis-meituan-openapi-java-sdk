package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.base.BasePageInfo;
import lombok.Data;

import java.util.List;

@Data
public class TcShopBillingTicketQueryResponse {

    /**
     * ticket
     */
    private List<TcShopBillingTicketResponse> ticketDataList;

    /**
     * category list
     */
    private BasePageInfo pageInfo;
}
