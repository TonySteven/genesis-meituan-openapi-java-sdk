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
     * status
     */
    private Integer status;

    /**
     * msg
     */
    private String msg;

    /**
     * message
     */
    private String message;

    /**
     * success
     */
    private Boolean success;

    private Boolean result;

}
