package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.config;

import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.request.OauthClientTokenRequest;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.response.OauthClientTokenResponse;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * 抖音配置
 * @author LP
 */
@Slf4j
@AllArgsConstructor
@Data
public class DyConfig {

    /**
     * 日志前缀
     */
    public static final String LOG_PREFIX = "[dy]";
    /**
     * 抖音请求url前缀 抖音域名 正式 https://open.douyin.com
     */
    private static final String PREFIX_URL = "https://open.douyin.com";

    /**
     * 抖音请求url前缀 抖音域名 沙盒 https://open-sandbox.douyin.com
     */
    private static final String DEV_PREFIX_URL = "https://open-sandbox.douyin.com";

    /**
     * 抖音授权获取token url
     */
    private static final String OAUTH_URL = "/oauth/client_token/";

    /**
     * 固定值“client_credential”
     */
    private static final String GRANT_TYPE = "client_credential";

    private static final List<String> DEFAULT_WHITE_URL_LIST = new ArrayList<>() {{
            add(OAUTH_URL);
        }
    };

    public DyConfig(){
        // 初始化白名单默认值
        this.getWhiteUrlList();
    }

    /**
     * 抖音接口请求地址域名部分.
     */
    private String basePrefixUrl;

    private String baseGrantType;

    /**
     * http请求连接超时时间.（毫秒）
     */
    private int connectTimeout = 5000;

    /**
     * http请求读取超时时间.（毫秒）
     */
    private int writeTimeout = 10000;

    /**
     * http请求读取超时时间.（毫秒）
     */
    private int readTimeout = 10000;

    /**
     * 设置缓存初始大小，应该合理设置，后续会扩容
     */
    private int cacheInitialCapacity = 10;

    /**
     * 设置缓存初始大小，应该合理设置，后续会扩容
     */
    private int cacheMaximumSize = 10;

    /**
     * 并发设置
     */
    private int cacheConcurrencyLevel = 5;

    /**
     * 缓存过期时间，写入后一个半小时过期（秒）
     */
    private int cacheExpireAfterWrite = 9000;

    /**
     * 此缓存对象经过多少秒没有被访问则过期（秒）
     */
    private int cacheExpireAfterAccess = 9000;

    /**
     * 是否启用缓存token
     */
    private Boolean isCacheToken = true;

    private LoadingCache<String, String> cache;

    /**
     * http请求工具类
     */
    private RestTemplate restTemplate;

    /**
     * 白名单url列表
     */
    private List<String> whiteUrlList = new ArrayList<>();

    /**
     * 抖音应用唯一标识
     */
    private String appId;

    /**
     * 应用唯一标识对应的密钥
     */
    private String appSecret;

    /**
     * 抖音授权token
     */
    private String accessToken;

    /**
     * 是否自动获取token
     */
    private Boolean isAutoOauth = true;

    /**
     * 最大重试次数
     */
    private int maxRetryCount = 1;

    public String getBasePrefixUrl() {
        if(StringUtils.isBlank(this.basePrefixUrl)){
            this.basePrefixUrl = PREFIX_URL;
        }
        return basePrefixUrl;
    }

    public String getBaseGrantType() {
        if(StringUtils.isBlank(this.baseGrantType)){
            this.baseGrantType = GRANT_TYPE;
        }
        return baseGrantType;
    }

    public List<String> getWhiteUrlList() {
        if(ObjectUtils.isEmpty(this.whiteUrlList)){
            this.whiteUrlList = new ArrayList<>();
            this.whiteUrlList.addAll(DEFAULT_WHITE_URL_LIST);
        }
        return whiteUrlList;
    }

    public RestTemplate getRestTemplate() {
        if(ObjectUtils.isEmpty(this.restTemplate)){
            this.restTemplate = new RestTemplate(getClientHttpRequestFactory());
        }
        return restTemplate;
    }

    public Boolean getIsCacheToken() {
        if(ObjectUtils.isEmpty(this.isCacheToken)){
            this.isCacheToken = true;
        }
        return isCacheToken;
    }

    public Boolean getIsAutoOauth() {
        if(ObjectUtils.isEmpty(this.isAutoOauth)){
            this.isAutoOauth = true;
        }
        return isAutoOauth;
    }

    public void setAccessToken(String accessToken) {
        if(this.getIsCacheToken()){
            // 设置token到缓存中
            this.getCache().put(this.getAppId(), accessToken);
        }else{
            this.accessToken = accessToken;
        }
    }

    public String getAccessToken() {
        try {
            if(this.getIsCacheToken()){
                return this.getCache().get(this.getAppId());
            }else{
                return this.accessToken;
            }
        }catch (Exception e){
            log.warn("{}getAccessToken异常, e:{}",LOG_PREFIX,e.getMessage(),e);
        }
        return null;
    }

    public LoadingCache<String, String> getCache(){
        if(ObjectUtils.isEmpty(this.cache)){
            this.cache = CacheBuilder.newBuilder()
                // 设置缓存初始大小，应该合理设置，后续会扩容
                .initialCapacity(this.getCacheInitialCapacity())
                // 最大值
                .maximumSize(this.getCacheMaximumSize())
                // 并发数设置
                .concurrencyLevel(this.getCacheConcurrencyLevel())
                // 缓存过期时间，写入后一个半小时过期
                .expireAfterWrite(this.getCacheExpireAfterWrite(), TimeUnit.SECONDS)
                // 此缓存对象经过多少秒没有被访问则过期。
                .expireAfterAccess(this.getCacheExpireAfterAccess(),TimeUnit.SECONDS)
                //统计缓存命中率
                .recordStats()// 指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
                .build(new CacheLoader<>() {
                    @Override
                    public String load(String key) {
                        return "";
                    }
                });
        }
        return cache;
    }

    /**
     * 删除缓存
     */
    public void removeCache() {

        if(this.getIsCacheToken()){
            this.getCache().invalidate(this.getAppId());
        }else{
            this.setAccessToken(null);
        }
    }

    /**
     * 设置抖音token
     * @param function 获取抖音授权的方法
     * @return clientToken
     */
    public String setClientToken(Function<OauthClientTokenRequest,OauthClientTokenResponse> function) {
        try {
            synchronized (this.getAppId()){
                // 获取token
                String clientToken = getAccessToken();
                if(StringUtils.isNotBlank(clientToken)){
                    return clientToken;
                }

                OauthClientTokenRequest request = new OauthClientTokenRequest();
                request.setClient_key(this.getAppId());
                request.setClient_secret(this.getAppSecret());
                request.setGrant_type(this.getBaseGrantType());

                OauthClientTokenResponse response = function.apply(request);

                if(ObjectUtils.isEmpty(response) || StringUtils.isBlank(response.getAccess_token())){
                    log.error("{},request:{},response:{},获取token失败，数据为空",LOG_PREFIX,request, response);
                    throw new RuntimeException("获取token失败，数据为空");
                }

                // 保存token
                this.setAccessToken(response.getAccess_token());

                return response.getAccess_token();
            }
        }catch (Exception e){
            log.error("{},e:{}",LOG_PREFIX,e.getMessage(),e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取抖音token
     * @param function 获取抖音授权的方法
     * @return clientToken
     */
    public String getClientToken(Function<OauthClientTokenRequest,OauthClientTokenResponse> function){
        try {
            // 获取token
            String clientToken = getAccessToken();

            if(StringUtils.isBlank(clientToken)){
                clientToken = setClientToken(function);
            }

            return clientToken;
        }catch (Exception e){
            log.error("{},e:{}",LOG_PREFIX,e.getMessage(),e);
            throw new RuntimeException(e);
        }
    }

    public ClientHttpRequestFactory getClientHttpRequestFactory() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
            .writeTimeout(writeTimeout, TimeUnit.MILLISECONDS)
            .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
            .build();
        return new OkHttp3ClientHttpRequestFactory(okHttpClient);
    }
}
