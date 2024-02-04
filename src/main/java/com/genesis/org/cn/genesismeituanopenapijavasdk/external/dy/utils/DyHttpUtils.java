package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.config.DyConfig;
import com.genesis.org.cn.genesismeituanopenapijavasdk.constant.AppConstant;
import com.genesis.org.cn.genesismeituanopenapijavasdk.constant.DyBaseConstant;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.enums.DyHttpStatusEnum;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.exception.DyBusinessException;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.ApiDyResult;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.request.BaseRequest;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.request.OauthClientTokenRequest;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.BaseRecordResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.OauthClientTokenResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * 抖音http请求工具类
 */
@Slf4j
@Getter
public class DyHttpUtils {

    /**
     * restTemplate
     */
    private final RestTemplate restTemplate;

    /**
     * 抖音配置
     */
    private final DyConfig dyConfig;

    /**
     * 抖音请求url白名单 不需要token的请求
     */
    private final List<String> whiteUrlList = new ArrayList<>();

    public DyHttpUtils(DyConfig dyConfig){
        this.restTemplate = dyConfig.getRestTemplate();
        this.dyConfig = dyConfig;

        whiteUrlList.addAll(this.dyConfig.getWhiteUrlList());
    }

    /**
     * 生成 client_token
     *
     * @param request 入参
     * @return 结果
     */
    public OauthClientTokenResponse oauthClientToken(OauthClientTokenRequest request) {
        return this.post(request,OauthClientTokenResponse.class);
    }

    /**
     * 刷新token
     * @param url 当前访问的url
     * @return 刷新后的token
     */
    private String refreshToken(String url) {
        // 验证是否需要获取token 如果指定token为空或url不在白名单中，则需要获取token
        if(!whiteUrlList.contains(url) && !this.dyConfig.getWhiteUrlList().contains(url)){
            return this.dyConfig.getClientToken(this::oauthClientToken);
        }
        return null;
    }

    /**
     * 公共请求头获取
     * @param accessToken 抖音的授权token
     * @return 结果
     */
    public HttpHeaders commonHeader(String url,String accessToken) {
        return commonHeader(url,accessToken,null);
    }

    /**
     * 公共请求头获取
     * @param headers 已存在的请求头
     * @return 结果
     */
    public HttpHeaders commonHeader(String url,HttpHeaders headers) {
        return commonHeader(url,null,headers);
    }

    /**
     * 公共请求头获取
     * @param accessToken 抖音的授权token
     * @param headers 已存在的请求头
     * @return 结果
     */
    public HttpHeaders commonHeader(String url,String accessToken,HttpHeaders headers) {
        if(ObjectUtils.isEmpty(headers)){
            headers = new HttpHeaders();
        }
        headers.add(AppConstant.CONTENT_TYPE,AppConstant.CONTENT_TYPE_JSON);

        if(StringUtils.isBlank(accessToken)){
            accessToken = refreshToken(url);
        }
        if(StringUtils.isNotBlank(accessToken)){
            headers.add(DyBaseConstant.ACCESS_TOKEN,accessToken);
        }
        return headers;
    }

    public String getUrl(String url){
        return dyConfig.getBasePrefixUrl() + url;
    }

    /**
     * http请求统一封装
     * @param request 请求入参
     * @param method 请求方法
     * @param headers 请求头
     * @param retryCount 当前重试次数
     * @return 结果
     * @param <T> 请求入参泛型
     */
    public <T extends BaseRequest,R> ApiDyResult<R> httpForObject(T request, HttpMethod method, HttpHeaders headers, int retryCount){
        return ExceptionUtils.retry(() -> {

            String url = request.getUrl();
            //封装请求头
            HttpHeaders newHeaders = commonHeader(url,headers);
            HttpEntity<T> formEntity;

            if(HttpMethod.GET.equals(method)){
                formEntity = new HttpEntity<>(newHeaders);

                if(ObjectUtils.isNotEmpty(request)){
                    url = url + "?" +this.getPostParams(request);
                }
            }else{
                formEntity = new HttpEntity<>(request,newHeaders);
            }

            ResponseEntity<String> response = restTemplate.exchange(this.getUrl(url), method,formEntity,String.class);

            ApiDyResult<R> result = JSON.parseObject(response.getBody(),ApiDyResult.class);

            if(ObjectUtils.isNotEmpty(result) && ObjectUtils.isNotEmpty(result.getExtra())){

                // 判断当前配置是否为自动刷新token
                if(this.dyConfig.getIsAutoOauth()){
                    // 校验token是否过期
                    if(!DyHttpStatusEnum.isTokenExpired(result.getExtra().getError_code())){
                        // 判断是否还可以重试
                        if(retryCount < this.dyConfig.getMaxRetryCount()){
                            // 刷新token
                            this.refreshToken(request.getUrl());
                            // 重新请求
                            return this.httpForObject(request,method,newHeaders,retryCount + 1);
                        }
                    }
                }
            }

            // 验证响应内容
            verifyResponse(result);

            return result;
        }, retryCount, this.dyConfig.getMaxRetryCount());
    }

    /**
     * get请求
     * @param request 请求入参
     * @param clazz 响应结果class
     * @return 结果
     * @param <T> 请求入参泛型
     * @param <R> 响应出参泛型
     */
    public <T extends BaseRequest,R> R get(T request, Class<R> clazz){
        return this.get(request,clazz,null);
    }

    /**
     * get请求
     * @param request 请求入参
     * @param headers 请求headers
     * @param clazz 响应结果class
     * @return 结果
     * @param <T> 请求入参泛型
     * @param <R> 响应出参泛型
     */
    public <T extends BaseRequest,R> R get(T request,Class<R> clazz,HttpHeaders headers){
        ApiDyResult<JSONObject> result = getForObject(request, headers);

        // 转换为出参对象
        return toResponse(clazz, result);
    }

    /**
     * get请求
     * @param request 请求入参
     * @param clazz 响应结果class
     * @return 结果
     * @param <T> 请求入参泛型
     * @param <R> 响应出参泛型
     */
    public <T extends BaseRequest,R> List<R> getList(T request,Class<R> clazz){
        return this.getList(request,clazz,null);
    }

    /**
     * get请求
     * @param request 请求入参
     * @param headers 请求headers
     * @param clazz 响应结果class
     * @return 结果
     * @param <T> 请求入参泛型
     * @param <R> 响应出参泛型
     */
    public <T extends BaseRequest,R> List<R> getList(T request,Class<R> clazz,HttpHeaders headers){
        ApiDyResult<JSONArray> result = getForObject(request, headers);

        // 转换为出参对象
        return toResponseList(clazz, result);
    }

    private <T extends BaseRequest,R> ApiDyResult<R> getForObject(T request, HttpHeaders headers) {
        return httpForObject(request, HttpMethod.GET, headers,0);
    }

    /**
     * post请求
     * @param request 请求入参
     * @param clazz 响应结果class
     * @return 结果
     * @param <T> 请求入参泛型
     * @param <R> 响应出参泛型
     */
    public <T extends BaseRequest,R> R post(T request,Class<R> clazz){
        return this.post(request,clazz,null);
    }

    /**
     * post请求
     * @param request 请求入参
     * @param headers 请求headers
     * @param clazz 响应结果class
     * @return 结果
     * @param <T> 请求入参泛型
     * @param <R> 响应出参泛型
     */
    public <T extends BaseRequest,R> R post(T request,Class<R> clazz,HttpHeaders headers){
        ApiDyResult<JSONObject> result = postForObject(request, headers);

        // 转换为出参对象
        return toResponse(clazz, result);
    }

    /**
     * post请求 结果转list
     * @param request 请求入参
     * @param clazz 响应结果class
     * @return 结果
     * @param <T> 请求入参泛型
     * @param <R> 响应出参泛型
     */
    public <T extends BaseRequest,R> List<R> postList(T request,Class<R> clazz){
        return this.postList(request,clazz,null);
    }

    /**
     * post请求 结果转list
     * @param request 请求入参
     * @param headers 请求headers
     * @param clazz 响应结果class
     * @return 结果
     * @param <T> 请求入参泛型
     * @param <R> 响应出参泛型
     */
    public <T extends BaseRequest,R> List<R> postList(T request, Class<R> clazz, HttpHeaders headers){
        ApiDyResult<JSONArray> result = postForObject(request, headers);

        // 转换为出参对象
        return toResponseList(clazz, result);
    }

    private <T extends BaseRequest,R> ApiDyResult<R> postForObject(T request, HttpHeaders headers) {
        return httpForObject(request, HttpMethod.POST, headers,0);
    }

    /**
     * 转换为出参对象
     * @param clazz 要转换测出参类
     * @param result 响应内容
     * @return 结果
     * @param <R> 要转换的出参对象泛型
     */
    @Nullable
    private static <R> R toResponse(Class<R> clazz, ApiDyResult<JSONObject> result) {
        if (ObjectUtils.isNotEmpty(result)) {
            return result.getData().toJavaObject(clazz);
        }
        return null;
    }

    /**
     * 转换为出参对象
     * @param clazz 要转换测出参类
     * @param result 响应内容
     * @return 结果
     * @param <R> 要转换的出参对象泛型
     */
    @Nullable
    private static <R> List<R> toResponseList(Class<R> clazz, ApiDyResult<JSONArray> result) {
        if (ObjectUtils.isNotEmpty(result)) {
            return result.getData().toJavaList(clazz);
        }
        return null;
    }

    /**
     * 验证响应结果
     */
    public static <T> void verifyResponse(ApiDyResult<T> result){
        if(ObjectUtils.isEmpty(result)){
            throw new DyBusinessException("抖音响应结果为空，请检查请求是否正确");
        }

        if(ObjectUtils.isEmpty(result.getExtra()) && ObjectUtils.isEmpty(result.getData())){
            throw new DyBusinessException("抖音响应结果为空，请检查请求是否正确");
        }

        // 如果响应错误码不为0，说明请求异常，直接抛出异常
        if(ObjectUtils.isNotEmpty(result.getExtra()) && !DyHttpStatusEnum.SUCCESS.getCode().equals(result.getExtra().getError_code())){
            throw new DyBusinessException(result.getExtra().getError_code(),"抖音响应异常，错误码：" +
                result.getExtra().getError_code() +
                "，错误描述：" +
                StringUtils.defaultString(result.getExtra().getDescription(),
                    DyHttpStatusEnum.getEnumByCode(result.getExtra().getError_code()).getValue()));
        }

        // 如果响应错误码不为0，说明请求异常，直接抛出异常
        if(ObjectUtils.isNotEmpty(result.getData()) && result.getData() instanceof BaseRecordResponse baseRecordResponse){
            throw new DyBusinessException(result.getExtra().getError_code(),"抖音响应异常，错误码：" +
                baseRecordResponse.getError_code() +
                "，错误描述：" +
                StringUtils.defaultString(baseRecordResponse.getDescription(),
                    DyHttpStatusEnum.getEnumByCode(baseRecordResponse.getError_code()).getValue()));
        }
    }

    /**
     * 获取当前对象和上级对象属性名和属性值 拼接为url参数
     * @param request 入参
     * @param requestURL 要拼接的url
     * @param <T> 当前对象泛型
     */
    public <T> void getObjectFields(T request,StringBuilder requestURL) {
        // 遍历属性类、属性值
        Field[] fields = request.getClass().getDeclaredFields();

        try {
            for (Field field : fields) {
                // 允许访问私有变量
                field.setAccessible(true);

                // 属性名
                String property = field.getName();
                // 属性值
                Object value = field.get(request);

                if(ObjectUtils.isNotEmpty(value)){
                    String valueStr = "";
                    if(value instanceof List){
                        List<Object> list = (List) value;
                        for(Object obj : list){
                            if(StringUtils.isNotBlank(valueStr)){
                                valueStr = valueStr + ",";
                            }
                            valueStr = valueStr + obj.toString();
                        }
                    }else if(value instanceof Object[]){
                        valueStr = StringUtils.join(value);
                    }else{
                        valueStr = value.toString();
                    }

                    String params = property + "=" + valueStr;
                    if (StringUtils.isBlank(requestURL)) {
                        requestURL.append(params);
                    } else {
                        requestURL.append("&").append(params);
                    }
                }
            }

            // 获取上级对象的属性和属性值
            Class<?> superClazz = request.getClass().getSuperclass();
            if (superClazz != null && !superClazz.equals(Object.class)) {
                Object superObj = superClazz.getDeclaredConstructor().newInstance();
                BeanUtils.copyProperties(request,superObj);
                getObjectFields(superObj,requestURL);
            }
        } catch (Exception e) {
            log.error("URL参数为：{}", request);
        }
    }


    /**
     *
     * 将实体类clazz的属性转换为url参数
     * @param request	参数实体类
     * @return
     * String
     */
    private <T> String getPostParams(T request) {

        StringBuilder requestURL = new StringBuilder();

        // 遍历属性类、属性值
        getObjectFields(request,requestURL);

        return requestURL.toString();
    }
}
