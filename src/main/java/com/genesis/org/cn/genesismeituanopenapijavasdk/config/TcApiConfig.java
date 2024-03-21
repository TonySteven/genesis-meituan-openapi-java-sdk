package com.genesis.org.cn.genesismeituanopenapijavasdk.config;

import lombok.Data;

@Data
public class TcApiConfig {

    /**
     * 天财AppId
     */
    private String appId;

    /**
     * 天财accessId
     */
    private String accessId;

    /**
     * 餐饮集团ID
     */
    private String centerId;

    /**
     * 自有服务器账号（目前只有成本卡在用）
     */
    private String username;

    /**
     * 自有服务器密码
     */
    private String password;
}
