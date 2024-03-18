package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.base.BasePageInfo;
import lombok.Data;

import java.util.List;

@Data
public class TcItemCategoryQueryResponse {

    /**
     * category
     */
    private List<TcItemCategoryResponse> category;

    /**
     * category list
     */
    private BasePageInfo pageInfo;
}
