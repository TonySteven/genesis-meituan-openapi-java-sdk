package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response;

import lombok.Data;

/**
 * 抖音授权响应模型
 */
@Data
public class OauthClientTokenResponse {

    /**
     * client_token 接口调用凭证
     */
    private String access_token;

    private String captcha;

    private String desc_url;

    /**
     * 错误码描述
     */
    private String description;

    /**
     * 错误码
     */
    private int error_code;

    /**
     * client_token 接口调用凭证超时时间，单位（秒）
     */
    private int expires_in;

    /**
     * 请求id
     */
    private String log_id;
}
