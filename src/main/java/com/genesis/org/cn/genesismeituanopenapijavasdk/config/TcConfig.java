package com.genesis.org.cn.genesismeituanopenapijavasdk.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "tiancai")
public class TcConfig {

    /**
     * 服务器请求协议
     */
    private String protocol;

    /**
     * 服务器地址
     */
    private String url;

    /**
     * 特殊地址
     */
    private String url2;

    /**
     * 服务器端口
     */
    private Integer port;

    private TcApiConfig api;
}
