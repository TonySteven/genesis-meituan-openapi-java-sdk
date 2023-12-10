package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.base;

import lombok.Data;

/**
 * 保存流程返回结果
 *
 * @author steven
 * &#064;date  2023/10/20
 */
@Data
public class BaseDataResponse {

    /**
     * code
     */
    private String code;

    /**
     * msg
     */
    private String msg;

    /**
     * success
     */
    private Boolean success;

}
