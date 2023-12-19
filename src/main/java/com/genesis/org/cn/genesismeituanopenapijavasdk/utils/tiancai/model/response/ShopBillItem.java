package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.base.BasePageInfo;
import lombok.Data;

import java.util.List;

/**
 * shop bill item
 *
 * @author steven
 * &#064;date  2023/12/19
 */
@Data
public class ShopBillItem {

    /**
     * bill list
     */
    private List<BillListItem> billList;

    /**
     * shop list
     */
    private BasePageInfo pageInfo;

    /**
     * shop id
     */
    private String shopId;

}
