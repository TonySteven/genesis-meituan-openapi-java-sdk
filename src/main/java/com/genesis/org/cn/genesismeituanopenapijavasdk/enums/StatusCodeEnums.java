package com.genesis.org.cn.genesismeituanopenapijavasdk.enums;

import lombok.Getter;

/**
 * 错误码
 * 1xxx 成功
 * 2xxx 权限类异常
 * 3xxx 验证类异常
 * 4xxx 客户端错误
 * 5xxx 系统异常
 * 6xxx 业务异常
 *
 * @author liwenchao
 */
@Getter
public enum StatusCodeEnums {

    /**
     * 操作成功
     */
    SUCCESS("成功", 200),

    /**
     * 对象创建成功
     */
    CREATED("对象创建成功", 201),

    /**
     * 请求已经被接受
     */
    ACCEPTED("请求已经被接受", 202),

    /**
     * 操作已经执行成功，但是没有返回数据
     */
    NO_CONTENT("操作已经执行成功，但是没有返回数据", 204),

    /**
     * 资源已被移除
     */
    MOVED_PERM("资源已被移除", 301),

    /**
     * 重定向
     */
    SEE_OTHER("重定向", 303),

    /**
     * 资源没有被修改
     */
    NOT_MODIFIED("资源没有被修改", 304),

    /**
     * 参数列表错误（缺少，格式不匹配）
     */
    BAD_REQUEST("参数列表错误（缺少，格式不匹配）", 400),

    /**
     * 未授权
     */
    UNAUTHORIZED("未授权", 401),

    /**
     * 访问受限，授权过期
     */
    FORBIDDEN("访问受限，授权过期", 403),

    /**
     * 资源，服务未找到
     */
    NOT_FOUND("资源，服务未找到", 404),

    /**
     * 不允许的http方法
     */
    BAD_METHOD("http请求类型错误,拒接访问", 405),

    /**
     * 资源冲突，或者资源被锁
     */
    CONFLICT("资源冲突，或者资源被锁", 409),

    /**
     * 不支持的数据，媒体类型
     */
    UNSUPPORTED_TYPE("不支持的数据，媒体类型", 415),

    /**
     * 系统内部错误
     */
    ERROR("系统内部错误", 500),

    /**
     * 接口未实现
     */
    NOT_IMPLEMENTED("接口未实现", 501),

    /**
     * 请求超时
     */
    REQUEST_REPLAY_ERR("请求过时,请重新发起", 408),

    DUPLICATE_KEY_ERR("数据已经存在,禁止重复添加", 3005),

    HTTP_JSON_PARAMS_TYPE_ERR("请求参数验证失败", 3015),

    SIGN_AUTHENTICATION_FAILED_ERR("通信密钥验证失败", 4002),

    NO_SERVER_NAME_ERR("服务名称未指定", 4003),

    VALIDATE_CODE_ERR("验证码验证失败", 4004),

    UNKNOWN_ERR("未知错误,请联系管理员", 5001),

    HTTP_PARAMS_TYPE_ERR("内部错误,请联系管理员", 5006),

    NO_SIGN_AUTHENTICATION_ERR("未配置通信密钥", 5008),

    APP_ERR("应用不合法", 5011),

    SIGN_ERR("签名失败", 5012),

    NOT_FIND_API_ERR("未发现API", 5013),

    SIGN_AUTH_ERR("签名验证失败", 5014),

    SQL_INJECT_ERR("包含敏感字符,请求失败", 5015),

    AUTHENTICATION_FAIL_ERR("token验证失败,请重新登录", 5016),

    GATEWAY_FAIL_ERR("网关调用异常", 5019),

    /**
     * 操作异常，需要撤销操作 特殊状态码（一般在跨服务调用 下游服务正常返回，上游服务出错时需要撤回下游服务操作时使用）
     */
    RETRACT_OPERATE("操作异常，需要撤销操作", 6001),

    /**
     * 撤销操作异常，需要撤销操作 特殊状态码（一般在跨服务调用 下游服务操作异常时使用， 【比如服务超时、撤销下游服务操作异常】）
     */
    RETRACT_OPERATE_ERROR("操作异常，需要撤销操作", 6002),

    BUSINESS_ERROR_CODE("亲，出错啦，请稍后再试！",51000),;

    private final String name;

    private final Integer code;

    StatusCodeEnums(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    @Override
    public String toString() {
        return this.code + ":" + this.name;
    }

    public static StatusCodeEnums getByCode(Integer code){
        for(StatusCodeEnums statusCodeEnum: StatusCodeEnums.values()){
            if(statusCodeEnum.getCode().equals(code)){
                return statusCodeEnum;
            }
        }
        return null;
    }
}
