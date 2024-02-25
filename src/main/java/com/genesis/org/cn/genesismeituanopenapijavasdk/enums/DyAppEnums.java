package com.genesis.org.cn.genesismeituanopenapijavasdk.enums;

import lombok.Getter;

/**
 * 抖音APP信息枚举（临时过渡）
 */
@Getter
public enum DyAppEnums {
    /**
     * 大叹号
     */
    DTH("awh1hb9zi8u74mdt","c1a14404b5564f745cb567bfbc9e7f98","大叹号");

    private final String appId;

    private final String appSecret;

    private final String appName;

    DyAppEnums(String appId, String appSecret, String appName) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.appName = appName;
    }

    public static DyAppEnums getByAppId(String appId) {
        for (DyAppEnums item : DyAppEnums.values()) {
            if (item.getAppId().equals(appId)) {
                return item;
            }
        }
        return null;
    }

    public static DyAppEnums getByAppSecret(String appSecret) {
        for (DyAppEnums item : DyAppEnums.values()) {
            if (item.getAppSecret().equals(appSecret)) {
                return item;
            }
        }
        return null;
    }


}
