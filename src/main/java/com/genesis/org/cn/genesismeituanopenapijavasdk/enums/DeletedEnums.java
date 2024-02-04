package com.genesis.org.cn.genesismeituanopenapijavasdk.enums;

import lombok.Getter;

@Getter
public enum DeletedEnums {


    NONE_DELETE(0,"未删除"),
    DELETE(1,"已删除"),;

    private final Integer code;
    private final String value;

    DeletedEnums(Integer code, String value){
        this.code = code;
        this.value = value;
    }


    public static boolean isDeleted(Integer code) {
        return NONE_DELETE.code.equals(code);
    }


}
