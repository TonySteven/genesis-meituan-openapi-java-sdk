package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.request;

import lombok.Data;

/**
 * 抖音授权请求模型
 */
@Data
public class OauthClientTokenRequest implements BaseRequest {

    /**
     * 应用唯一标识
     */
    private String client_key;

    /**
     * 应用唯一标识对应的密钥
     */
    private String client_secret;

    /**
     * 固定值“client_credential”
     */
    private String grant_type;

    @Override
    public String getUrl() {
        return "/oauth/client_token/";
    }
}
