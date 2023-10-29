package com.genesis.org.cn.genesismeituanopenapijavasdk.model.mt.api.response;

import com.genesis.org.cn.genesismeituanopenapijavasdk.model.mt.api.response.model.MtShopCommentResponseData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 美团门店评价返回结果
 *
 * @author steven
 * &#064;date  2023/10/20
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class MtShopCommentResponse {

    /**
     * data
     */
    private List<MtShopCommentResponseData> data;

}
