package com.genesis.org.cn.genesismeituanopenapijavasdk.enums;

import lombok.Getter;

/**
 * 本地生活商家账户信息（临时过渡）
 */
@Getter
public enum DyAccountEnums {
    DTH("7047437174853240835","市北区青北大叹号韩式烤肉店");

    private final String accountId;

    private final String accountName;

    DyAccountEnums(String accountId, String accountName) {
        this.accountId = accountId;
        this.accountName = accountName;
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
