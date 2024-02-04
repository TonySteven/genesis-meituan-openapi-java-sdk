package com.genesis.org.cn.genesismeituanopenapijavasdk.result;


import com.alibaba.fastjson.JSONObject;
import com.genesis.org.cn.genesismeituanopenapijavasdk.enums.StatusCodeEnums;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;


/**
 * 封装结果
 *
 * @author liwenchao
 */
@Data
public class ApiResult<T> {

    /**
     * 服务名称
     */
    private String serverName;

    /**
     * 跟踪id
     */
    private String traceId;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 结果代码
     */
    private Integer code;

    /**
     * 结果说明
     */
    private String message;

    /**
     * 返回参数信息(分页请求的话分页参数放进data)
     */
    private T data;

    /**
     * 此构造方法应用于data为null的场景
     */
    public ApiResult() {
        this.code = StatusCodeEnums.SUCCESS.getCode();
        this.message = StatusCodeEnums.SUCCESS.getName();
        this.success = true;
        traceId = MDC.get("tid");
    }

    /**
     * 有具体业务数据返回时,使用此构造方法
     */
    public ApiResult(T data) {
        this();
        this.data = data;
    }

    /**
     * 出现异常以后要调用此方法封装异常信息
     *
     * @param t 异常类
     */
    public ApiResult(Throwable t) {
        this.code = StatusCodeEnums.UNKNOWN_ERR.getCode();
        this.message = t.getMessage();
        this.success = false;
        this.traceId = MDC.get("tid");
    }

    public static <T> ApiResult<T> restResult(T data, Integer code, String msg, String serverName, String traceId, boolean success) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setSuccess(success);
        apiResult.setData(data);
        apiResult.setServerName(serverName);

        if(code != null){
            apiResult.setCode(code);
        }
        if(StringUtils.isNotBlank(msg)){
            apiResult.setMessage(msg);
        }
        if(StringUtils.isNotBlank(traceId)){
            apiResult.setTraceId(traceId);
        }
        return apiResult;
    }

    public static <T> ApiResult<T> restResult(T data, Integer code, String msg, String serverName, boolean success) {
        return restResult(data,code,msg,serverName,null,success);
    }

    public static <T> ApiResult<T> restResult(T data, Integer code, String msg, boolean success) {
        return restResult(data,code,msg,null,null,success);
    }

    public static <T> ApiResult<T> success() {
        return restResult(null, StatusCodeEnums.SUCCESS.getCode(), null,null,null,true);
    }

    public static <T> ApiResult<T> success(T data) {
        return restResult(data, StatusCodeEnums.SUCCESS.getCode(), null,null,null,true);
    }

    public static <T> ApiResult<T> success(String msg) {
        return restResult(null, StatusCodeEnums.SUCCESS.getCode(), msg,null,null,true);
    }

    public static <T> ApiResult<T> successData(String data) {
        return (ApiResult<T>) restResult(data, StatusCodeEnums.SUCCESS.getCode(), null,null,null,true);
    }

    public static <T> ApiResult<T> success(String msg, T data) {
        return restResult(data, StatusCodeEnums.SUCCESS.getCode(), msg,null,null,true);
    }

    public static <T> ApiResult<T> error() {
        return restResult(null, StatusCodeEnums.UNKNOWN_ERR.getCode(), StatusCodeEnums.UNKNOWN_ERR.getName(),null,null,false);
    }

    public static <T> ApiResult<T> error(T data) {
        return restResult(data, StatusCodeEnums.ERROR.getCode(), null,null,null,false);
    }

    public static <T> ApiResult<T> error(String msg) {
        return restResult(null, StatusCodeEnums.ERROR.getCode(), msg,null,null,false);
    }

    public static <T> ApiResult<T> error(String msg, T data) {
        return restResult(data, StatusCodeEnums.ERROR.getCode(), msg,null,null,false);
    }

    public static <T> ApiResult<T> error(int code, String msg) {
        return restResult(null,code,msg,null,null,false);
    }

    public static <T> ApiResult<T> error(String msg, T data, int code) {
        return restResult(data,code,msg,null,null,false);
    }


    public static <T> ApiResult<T> buildByResponseBody(String responseBody) {
        if(StringUtils.isEmpty(responseBody)){
            return ApiResult.error();
        }
        JSONObject json = JSONObject.parseObject(responseBody);
        return restResult((T) json.get("data"),json.getInteger("code"),json.getString("msg"),null,null,false);
    }

}




