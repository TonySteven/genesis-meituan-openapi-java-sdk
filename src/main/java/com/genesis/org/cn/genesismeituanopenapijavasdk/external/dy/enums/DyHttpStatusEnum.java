package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.enums;

import lombok.Getter;

/**
 * 抖音请求状态码枚举
 */
@Getter
public enum DyHttpStatusEnum {
    /**
     * 成功
     */
    SUCCESS(0,"成功"),
    /**
     * access_token无效
     */
    INVALID_ACCESS_TOKEN(2190002,"access_token无效"),
    /**
     * 应用未获得该能力
     */
    NOT_PUBLIC_CAPACITY(2190004,"应用未获得该能力, 请去https://open.douyin.com/申请"),
    /**
     * access_token过期,请刷新或重新授权
     */
    TOKEN_EXPIRED(2190008,"access_token过期,请刷新或重新授权"),
    /**
     * 参数不合法
     */
    INVALID_PARAMETER(2119001,"参数不合法"),
    /**
     * 系统繁忙，请稍候再试
     */
    SYSTEM_BUSY(2119002,"系统繁忙，请稍候再试"),
    /**
     * 请求太过频繁，请稍后再试
     */
    TOO_FREQUENTLY(2119003,"请求太过频繁，请稍后再试"),
    /**
     * 应用未获商家授权
     */
    NOT_AUTHORIZED(2119005,"应用未获商家授权"),
    /**
     * 根据实际业务错误返回-对照接口文档规范参数并重试
     */
    INVALID_PARAMETER_1(3000001,"对参照接口文档规范参数并重试"),
    /**
     * 根据实际业务错误返回-补充参数
     */
    INVALID_PARAMETER_2(4000001,"补充参数"),
    /**
     * 根据实际业务错误返回-对照接口文档规范参数并重试
     */
    INVALID_PARAMETER_3(4000002,"对照接口文档规范参数并重试"),
    /**
     * 根据实际业务错误返回-联系抖音处理
     */
    INVALID_PARAMETER_4(5000001,"联系抖音处理"),
    /**
     * 门店未查询到商家
     */
    SHOP_NOT_FOUND(3000004,"门店未查询到商家"),
    /**
     * account_id，third_id，poi_id必须选择一个进行查询
     */
    INVALID_PARAMETER_5(3000005,"account_id，third_id，poi_id必须选择一个进行查询"),
    /**
     * 未知异常
     */
    UNKNOWN(9999999,"未知异常"),
    ;

    private final Integer code;

    private final String value;

    DyHttpStatusEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static DyHttpStatusEnum getEnumByCode(Integer code) {
        for (DyHttpStatusEnum item : DyHttpStatusEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return DyHttpStatusEnum.UNKNOWN;
    }

    public static DyHttpStatusEnum getEnumByValue(String value) {
        for (DyHttpStatusEnum item : DyHttpStatusEnum.values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return DyHttpStatusEnum.UNKNOWN;
    }

    /**
     * 判断是否是token过期
     * @param code 入参
     * @return 结果
     */
    public static boolean isTokenExpired(Integer code) {
        return DyHttpStatusEnum.INVALID_ACCESS_TOKEN.getCode().equals(code) || DyHttpStatusEnum.TOKEN_EXPIRED.getCode().equals(code);
    }
}
