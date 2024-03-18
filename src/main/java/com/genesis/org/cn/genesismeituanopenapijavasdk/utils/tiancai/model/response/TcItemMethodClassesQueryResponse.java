package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.base.BasePageInfo;
import lombok.Data;

import java.util.List;

/**
 * query item_method_classes info data response
 *
 * @author LP
 */
@Data
public class TcItemMethodClassesQueryResponse {

    /**
     * category
     */
    private List<TcItemMethodClassesResponse> methodClass;

    /**
     * category list
     */
    private BasePageInfo pageInfo;
}
