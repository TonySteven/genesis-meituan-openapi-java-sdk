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
    QD_DTH("awh1hb9zi8u74mdt","c1a14404b5564f745cb567bfbc9e7f98","市北区青北大叹号韩式烤肉店"),
    YJ_DTH("aw3thojeoe188nh1","e5853c2a57913438ea61cea99666025e","延吉市建工大叹号美味吧"),
    SK_DTH("awjt4xcaj0u26eqh","adf5e1710ba05e21376ccb4c173d4af8","珲春市延山葵烤肉店（有限合伙）");

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
