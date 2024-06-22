package com.genesis.org.cn.genesismeituanopenapijavasdk.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;


/**
 * 本地生活商家账户信息（临时过渡）
 */
@Getter
public enum DyAccountEnums {
    QD_DTH("7047437174853240835","市北区青北大叹号韩式烤肉店","awh1hb9zi8u74mdt"),
    YJ_DTH("7038469403746240550","延吉市建工大叹号美味吧","aw3thojeoe188nh1"),
    SK_DTH("7306031614503421964","珲春市延山葵烤肉店（有限合伙）","awjt4xcaj0u26eqh");

    /**
     * 账户ID
     */
    private final String accountId;

    /**
     * 账户名称
     */
    private final String accountName;

    /**
     * 账户绑定的APP ID
     */
    private final String appId;

    DyAccountEnums(String accountId, String accountName, String appId) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.appId = appId;
    }


    public static List<DyAccountEnums> getByAccountIds(List<String> accountIds)
    {
        return Arrays.stream(DyAccountEnums.values()).filter(item -> accountIds.contains(item.getAccountId())).toList();
    }


    public static DyAccountEnums getByAccountId(String accountId)
    {
        for (DyAccountEnums accountEnum : DyAccountEnums.values()) {
            if (accountEnum.accountId.equals(accountId)) {
                return accountEnum;
            }
        }
        return null;
    }

    public static DyAccountEnums getByAccountName(String accountName)
    {
        for (DyAccountEnums accountEnum : DyAccountEnums.values()) {
            if (accountEnum.accountName.equals(accountName)) {
                return accountEnum;
            }
        }
        return null;
    }
}
