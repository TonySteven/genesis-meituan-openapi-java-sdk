package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.genesis.org.cn.genesismeituanopenapijavasdk.config.TcConfig;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.HttpPostRequestUtil;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.enums.ResponseStatusEnum;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.request.TcItemQueryRequest;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.request.TcRecipeCardQueryRequest;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.request.TcScmDjmxRequest;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.LoginResult;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.QueryBillDetailsInRealTimeResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.QueryBillDetailsResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.QueryShopInfoResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcItemCategoryDataResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcItemDataResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcItemMethodClassesDataResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcItemMethodsDataResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcItemUnitDataResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcPayTypeDataResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcPaywayDetailDataResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcRecipeCardDataResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcScmDjmxDataResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcScmGysDataResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcScmPxDataResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * query shop info action
 *
 * @author steven
 * &#064;date  2023/12/10
 */
@Slf4j
public class QueryShopInfoAction {

    /**
     * url query shop info
     */
    static final String URL_QUERY_SHOP_INFO = "/api/datatransfer/getshops";

    /**
     * url query bill in realtime
     */
    static final String URL_QUERY_BILL_IN_REALTIME = "/api/datatransfer/getserialdatalivewithpage";

    /**
     * url query bill info
     */
    static final String URL_QUERY_BILL = "/api/datatransfer/getserialdata";

    /**
     * url query category list
     */
    static final String URL_QUERY_ITEM_CATEGORY_LIST = "/api/datatransfer/getitemcategoryinfo";

    /**
     * url query item list
     */
    static final String URL_QUERY_ITEM_LIST = "/api/datatransfer/getitems";

    /**
     * url query method classes list
     */
    static final String URL_QUERY_ITEM_METHOD_CLASS_LIST = "/api/datatransfer/getitemmethodclasses";

    /**
     * url query methods list
     */
    static final String URL_QUERY_ITEM_METHODS_LIST = "/api/datatransfer/getitemmethods";

    /**
     * url query unit list
     */
    static final String URL_QUERY_ITEM_UNIT_LIST = "/api/datatransfer/getItemUnitData";

    /**
     * url query methods list
     */
    static final String URL_QUERY_PAY_TYPE_LIST = "/api/datatransfer/getpaytypeinfo";

    /**
     * url query methods list
     */
    static final String URL_QUERY_PAYWAY_DETAIL_LIST = "/api/datatransfer/getpaywaydetailinfo";

    /**
     * url query recipeCard list
     */
    static final String URL_QUERY_RECIPE_CARD_LIST = "/cldpoint/openservice/recipeCardInfoBatch.do";

    /**
     * url query scm px list
     */
    static final String URL_QUERY_SCM_PX_LIST = "/cldpoint/getItemNew.do";

    /**
     * url query scm px list
     */
    static final String URL_QUERY_SCM_GYS_LIST = "/cldpoint/getSupplierMore.do";

    /**
     * url query scm djmx delete list
     */
    static final String URL_QUERY_SCM_DJMX_DELETE_LIST = "/cldpoint/getDeleteBillList.do";

    /**
     * url query scm djmx list
     */
    static final String URL_QUERY_SCM_DJMX_LIST = "/cldpoint/getDsstorebill.do";

    /**
     * 服务器请求协议
     */
    @Value("${tiancai.protocol}")
    private static String protocol = "https";

    /**
     * 服务器地址
     */
    @Value("${tiancai.url}")
    private static String applicationServer = "cysms.wuuxiang.com";

    /**
     * 服务器地址
     */
    @Value("${tiancai.url2}")
    private static String applicationServer2 = "http://dthzb.sm5.fxscm.net";

    /**
     * 服务器端口
     */
    @Value("${tiancai.port}")
    private static Integer applicationPort = 443;

    /**
     * appId
     */
    @Value("${tiancai.api.appId}")
    private static String appId = "2a36bf799e6b4c90941bf84b67789111";

    /**
     * accessId
     */
    @Value("${tiancai.api.accessId}")
    private static String accessId = "a1fca758ed104119889f46df3528d9fa";

    /**
     * 餐饮集团ID
     */
    @Value("${tiancai.api.centerId}")
    private static String centerId = "94630";

    public static void main(String[] args) {
        // try {
        //     LoginResult result = LoginToServerAction.login(protocol, applicationServer, applicationPort, appId, accessId);
        //     if (ResponseStatusEnum.SUCCESS.getValue().equals(result.getMsg())) {
        //         // 成功登录 到O2Server
        //         log.info("成功鉴权！token=" + result.getAccessToken());
        //         // 启动指定的流程
        //         QueryShopInfoResponse queryShopInfoResponse = queryShopInfo(protocol
        //             , applicationServer, applicationPort, accessId, result.getAccessToken(), 1, 10);
        //         if (Boolean.TRUE.equals(queryShopInfoResponse.getSuccess())) {
        //             log.info("查询店铺信息成功！data=" + JSONUtil.toJsonStr(queryShopInfoResponse.getData()));
        //         } else {
        //             log.info("查询店铺信息失败！message=" + queryShopInfoResponse.getMsg());
        //         }
        //     } else {
        //         // 登录失败
        //         log.info("登录失败！message:" + result.getMsg());
        //     }
        // } catch (Exception e) {
        //     e.fillInStackTrace();
        // }

        try {
            LoginResult result = LoginToServerAction.login(protocol, applicationServer, applicationPort, appId, accessId);
            if (ResponseStatusEnum.SUCCESS.getValue().equals(result.getMsg())) {
                // 成功登录 到O2Server
                log.info("成功鉴权！token=" + result.getAccessToken());
                // 启动指定的流程
                // 自定义开始时间和结束时间
                String beginDate = "2021-12-01 00:00:00";
                String endDate = "2021-12-10 23:59:59";
                QueryBillDetailsResponse queryBillDetailsResponse = queryBillingDetails
                    (protocol, applicationServer, applicationPort, accessId, result.getAccessToken()
                        , 1, 50, "94631", beginDate, endDate);
                if (Boolean.TRUE.equals(queryBillDetailsResponse.getSuccess())) {
                    log.info("查询店铺实时账单成功！data=" + JSONUtil.toJsonStr(queryBillDetailsResponse.getData()));
                } else {
                    log.info("查询店铺实时账单失败！message=" + queryBillDetailsResponse.getMsg());
                }
            } else {
                // 登录失败
                log.info("登录失败！message:" + result.getMsg());
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    /**
     * query shop info
     *
     * @param applicationServer 127.0.0.1
     * @param applicationPort   20020
     * @param protocol          protocol
     * @param accessId          access id
     * @param token             token
     * @param pageNo            page no
     * @param pageSize          page size
     * @return {@link QueryShopInfoResponse}
     */
    public static QueryShopInfoResponse queryShopInfo(String protocol, String applicationServer, Integer applicationPort
        , String accessId, String token, Integer pageNo, Integer pageSize) {

        // 参数
        String url = protocol + "://" + applicationServer + ":" + applicationPort + URL_QUERY_SHOP_INFO;
        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("centerId", centerId);
        loginParams.put("pageNo", String.valueOf(pageNo));
        loginParams.put("pageSize", String.valueOf(pageSize));

        // 将xtoken添加到httpHeader里，调用服务一定要添加认证过的token
        Map<String, String> headMap = getHeader(token, accessId);
        // 打印loginUrl
        log.info("requestUrl=" + url);
        String responseData = httpRetry(() -> HttpPostRequestUtil.sendPostWithParams(url, loginParams, headMap));
        JSONObject jsonObj = new JSONObject(responseData);
        return JSON.parseObject(jsonObj.toString()
            , QueryShopInfoResponse.class);
    }

    /**
     * query billing details in real time
     *
     * @param protocol          protocol
     * @param applicationServer application server
     * @param applicationPort   application port
     * @param accessId          access id
     * @param token             token
     * @param pageNo            page no
     * @param pageSize          page size
     * @return {@link QueryShopInfoResponse}
     */
    public static QueryBillDetailsInRealTimeResponse queryBillingDetailsInRealTime(String protocol
        , String applicationServer, Integer applicationPort
        , String accessId, String token, Integer pageNo, Integer pageSize, String shopId) {

        // 参数
        String url = protocol + "://" + applicationServer + ":" + applicationPort + URL_QUERY_BILL_IN_REALTIME;
        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("centerId", centerId);
        loginParams.put("pageNo", String.valueOf(pageNo));
        loginParams.put("pageSize", String.valueOf(pageSize));
        loginParams.put("shopId", shopId);
        // 初始化开始时间 当前时间前15分钟 转化为2023-12-19 20:20:00格式
        String beginTime = getBeginTime();
        loginParams.put("timeBegin", beginTime);
        // 将xtoken添加到httpHeader里，调用服务一定要添加认证过的token
        Map<String, String> headMap = getHeader(token, accessId);
        // 打印loginUrl
        log.info("requestUrl=" + url);
        String responseData = httpRetry(() -> HttpPostRequestUtil.sendPostWithParams(url, loginParams, headMap));
        JSONObject jsonObj = new JSONObject(responseData);
        return JSON.parseObject(jsonObj.toString()
            , QueryBillDetailsInRealTimeResponse.class);
    }


    /**
     * query billing details
     *
     * @param protocol          protocol
     * @param applicationServer application server
     * @param applicationPort   application port
     * @param accessId          access id
     * @param token             token
     * @param pageNo            page no
     * @param pageSize          page size
     * @return {@link QueryShopInfoResponse}
     */
    public static QueryBillDetailsResponse queryBillingDetails(String protocol
        , String applicationServer, Integer applicationPort
        , String accessId, String token, Integer pageNo, Integer pageSize, String shopId
        , String beginDateStr, String endDateStr) {

        // 参数
        String url = protocol + "://" + applicationServer + ":" + applicationPort + URL_QUERY_BILL;
        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("centerId", centerId);
        loginParams.put("pageNo", String.valueOf(pageNo));
        loginParams.put("pageSize", String.valueOf(pageSize));
        loginParams.put("shopId", shopId);
        loginParams.put("dateType", "1");
        loginParams.put("beginDate", beginDateStr);
        loginParams.put("endDate", endDateStr);
        // needPkgDetail 0:不返回套餐明细数据，1：返回套餐明细数据,define:0
        loginParams.put("needPkgDetail", "1");
        // 将xtoken添加到httpHeader里，调用服务一定要添加认证过的token
        Map<String, String> headMap = getHeader(token, accessId);
        // 打印loginUrl
        log.info("requestUrl=" + url);
        String responseData = httpRetry(() -> HttpPostRequestUtil.sendPostWithParams(url, loginParams, headMap));

        JSONObject jsonObj = new JSONObject(responseData);
        return JSON.parseObject(jsonObj.toString()
            , QueryBillDetailsResponse.class);
    }

    /**
     * query category list
     *
     * @param config            config
     * @param token             token
     * @param pageNo            page no
     * @param pageSize          page size
     * @return {@link QueryShopInfoResponse}
     */
    public static TcItemCategoryDataResponse queryItemCategoryList(TcConfig config, String token, Integer pageNo, Integer pageSize, String shopId) {

        // 参数
        String url = getUrl(config) + URL_QUERY_ITEM_CATEGORY_LIST;

        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("centerId", config.getApi().getCenterId());
        loginParams.put("pageNo", String.valueOf(pageNo));
        loginParams.put("pageSize", String.valueOf(pageSize));
        if(StringUtils.isNotEmpty(shopId)){
            loginParams.put("shopId", shopId);
        }
        // 将xtoken添加到httpHeader里，调用服务一定要添加认证过的token
        Map<String, String> headMap = getHeader(token, config.getApi().getAccessId());
        // 打印loginUrl
        log.info("requestUrl=" + url);
        String responseData = httpRetry(() -> HttpPostRequestUtil.sendPostWithParams(url, loginParams, headMap));
        JSONObject jsonObj = new JSONObject(responseData);
        return JSON.parseObject(jsonObj.toString()
            , TcItemCategoryDataResponse.class);
    }

    /**
     * query category list
     *
     * @param config            config
     * @param token             token
     * @param request            request
     * @return {@link QueryShopInfoResponse}
     */
    public static TcItemDataResponse queryItemList(TcConfig config, String token, TcItemQueryRequest request) {

        // 参数
        String url = getUrl(config) + URL_QUERY_ITEM_LIST;

        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("centerId", config.getApi().getCenterId());
        loginParams.put("pageNo", String.valueOf(request.getPageNo()));
        loginParams.put("pageSize", String.valueOf(request.getPageSize()));
        if(StringUtils.isNotEmpty(request.getShopId())){
            loginParams.put("shopId", request.getShopId());
        }
        if(StringUtils.isNotEmpty(request.getBigClassId())){
            loginParams.put("bigClassId", request.getBigClassId());
        }
        if(StringUtils.isNotEmpty(request.getSmallClassId())){
            loginParams.put("smallClassId", request.getSmallClassId());
        }
        if(StringUtils.isNotEmpty(request.getBrandId())){
            loginParams.put("brandId", request.getBrandId());
        }
        if(StringUtils.isNotEmpty(request.getLastTime())){
            loginParams.put("lastTime", request.getLastTime());
        }
        // 将xtoken添加到httpHeader里，调用服务一定要添加认证过的token
        Map<String, String> headMap = getHeader(token, config.getApi().getAccessId());
        // 打印loginUrl
        log.info("requestUrl=" + url);
        String responseData = httpRetry(() -> HttpPostRequestUtil.sendPostWithParams(url, loginParams, headMap));
        JSONObject jsonObj = new JSONObject(responseData);
        return JSON.parseObject(jsonObj.toString()
            , TcItemDataResponse.class);
    }

    /**
     * query ItemMethodClasses list
     *
     * @param config            config
     * @param token             token
     * @param pageNo            page no
     * @param pageSize          page size
     * @return {@link QueryShopInfoResponse}
     */
    public static TcItemMethodClassesDataResponse queryItemMethodClassesList(TcConfig config, String token, Integer pageNo, Integer pageSize, String shopId) {

        // 参数
        String url = getUrl(config) + URL_QUERY_ITEM_METHOD_CLASS_LIST;

        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("centerId", config.getApi().getCenterId());
        loginParams.put("pageNo", String.valueOf(pageNo));
        loginParams.put("pageSize", String.valueOf(pageSize));
        if(StringUtils.isNotEmpty(shopId)){
            loginParams.put("shopId", shopId);
        }
        // 将xtoken添加到httpHeader里，调用服务一定要添加认证过的token
        Map<String, String> headMap = getHeader(token, config.getApi().getAccessId());
        // 打印loginUrl
        log.info("requestUrl=" + url);
        String responseData = httpRetry(() -> HttpPostRequestUtil.sendPostWithParams(url, loginParams, headMap));
        JSONObject jsonObj = new JSONObject(responseData);
        return JSON.parseObject(jsonObj.toString()
            , TcItemMethodClassesDataResponse.class);
    }

    /**
     * query ItemMethods list
     *
     * @param config            config
     * @param token             token
     * @param pageNo            page no
     * @param pageSize          page size
     * @param classId           class id
     * @param shopId            shop id
     * @return {@link QueryShopInfoResponse}
     */
    public static TcItemMethodsDataResponse queryItemMethodsList(TcConfig config, String token, Integer pageNo, Integer pageSize, String shopId, String classId) {

        // 参数
        String url = getUrl(config) + URL_QUERY_ITEM_METHODS_LIST;

        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("centerId", config.getApi().getCenterId());
        loginParams.put("pageNo", String.valueOf(pageNo));
        loginParams.put("pageSize", String.valueOf(pageSize));
        if(StringUtils.isNotEmpty(shopId)){
            loginParams.put("shopId", shopId);
        }
        if(StringUtils.isNotEmpty(classId)){
            loginParams.put("classId", classId);
        }
        // 将xtoken添加到httpHeader里，调用服务一定要添加认证过的token
        Map<String, String> headMap = getHeader(token, config.getApi().getAccessId());
        // 打印loginUrl
        log.info("requestUrl=" + url);
        String responseData = httpRetry(() -> HttpPostRequestUtil.sendPostWithParams(url, loginParams, headMap));
        JSONObject jsonObj = new JSONObject(responseData);
        return JSON.parseObject(jsonObj.toString()
            , TcItemMethodsDataResponse.class);
    }

    /**
     * query ItemUnit list
     *
     * @param config            config
     * @param token             token
     * @param pageNo            page no
     * @param pageSize          page size
     * @param shopId            shop id
     * @return {@link QueryShopInfoResponse}
     */
    public static TcItemUnitDataResponse queryItemUnitList(TcConfig config, String token, Integer pageNo, Integer pageSize, String shopId) {

        // 参数
        String url = getUrl(config) + URL_QUERY_ITEM_UNIT_LIST;

        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("centerId", config.getApi().getCenterId());
//        loginParams.put("pageNo", String.valueOf(pageNo));
//        loginParams.put("pageSize", String.valueOf(pageSize));
        if(StringUtils.isNotEmpty(shopId)){
            loginParams.put("shopId", shopId);
        }
        // 将xtoken添加到httpHeader里，调用服务一定要添加认证过的token
        Map<String, String> headMap = getHeader(token, config.getApi().getAccessId());
        // 打印loginUrl
        log.info("requestUrl=" + url);
        String responseData = httpRetry(() -> HttpPostRequestUtil.sendPostWithParams(url, loginParams, headMap));
        JSONObject jsonObj = new JSONObject(responseData);
        return JSON.parseObject(jsonObj.toString()
            , TcItemUnitDataResponse.class);
    }

    /**
     * query payType list
     *
     * @param config            config
     * @param token             token
     * @param pageNo            page no
     * @param pageSize          page size
     * @return {@link QueryShopInfoResponse}
     */
    public static TcPayTypeDataResponse queryPayTypeList(TcConfig config, String token, Integer pageNo, Integer pageSize) {

        // 参数
        String url = getUrl(config) + URL_QUERY_PAY_TYPE_LIST;

        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("centerId", config.getApi().getCenterId());
        loginParams.put("pageNo", String.valueOf(pageNo));
        loginParams.put("pageSize", String.valueOf(pageSize));
        // 将xtoken添加到httpHeader里，调用服务一定要添加认证过的token
        Map<String, String> headMap = getHeader(token, config.getApi().getAccessId());
        // 打印loginUrl
        log.info("requestUrl=" + url);
        String responseData = httpRetry(() -> HttpPostRequestUtil.sendPostWithParams(url, loginParams, headMap));

        JSONObject jsonObj = new JSONObject(responseData);
        return JSON.parseObject(jsonObj.toString()
            , TcPayTypeDataResponse.class);
    }

    /**
     * query ItemMethods list
     *
     * @param config            config
     * @param token             token
     * @param pageNo            page no
     * @param pageSize          page size
     * @param shopId            shop id
     * @return {@link QueryShopInfoResponse}
     */
    public static TcPaywayDetailDataResponse queryPaywayDetailList(TcConfig config, String token, Integer pageNo, Integer pageSize, String shopId) {

        // 参数
        String url = getUrl(config) + URL_QUERY_PAYWAY_DETAIL_LIST;

        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("centerId", config.getApi().getCenterId());
        loginParams.put("pageNo", String.valueOf(pageNo));
        loginParams.put("pageSize", String.valueOf(pageSize));
        if(StringUtils.isNotEmpty(shopId)){
            loginParams.put("shopId", shopId);
        }
        // 将xtoken添加到httpHeader里，调用服务一定要添加认证过的token
        Map<String, String> headMap = getHeader(token, config.getApi().getAccessId());
        // 打印loginUrl
        log.info("requestUrl=" + url);
        String responseData = httpRetry(() -> HttpPostRequestUtil.sendPostWithParams(url, loginParams, headMap));

        JSONObject jsonObj = new JSONObject(responseData);
        return JSON.parseObject(jsonObj.toString()
            , TcPaywayDetailDataResponse.class);
    }

    /**
     * query RecipeCard
     *
     * @param config            config
     * @param request             request
     * @return {@link QueryShopInfoResponse}
     */
    public static TcRecipeCardDataResponse queryRecipeCardList(TcConfig config, TcRecipeCardQueryRequest request) {

        // 参数
        String url = applicationServer2 + URL_QUERY_RECIPE_CARD_LIST;

        Map<String, String> loginParams = new LinkedHashMap<>();
        loginParams.put("ent", "ENTa5bv");
        loginParams.put("username", config.getApi().getUsername());
        loginParams.put("password", config.getApi().getPassword());
        if(StringUtils.isNotBlank(request.getDishJkid())){
            loginParams.put("dishJkid", request.getDishJkid());
        }
        if(StringUtils.isNotBlank(request.getContainsDel())) {
            loginParams.put("containsDel", request.getContainsDel());
        }
        if(StringUtils.isNotBlank(request.getContainsEmptyDt())) {
            loginParams.put("containsEmptyDt", request.getContainsEmptyDt());
        }
        if(StringUtils.isNotBlank(request.getContainsAllDt())) {
            loginParams.put("containsAllDt", request.getContainsAllDt());
        }
        // 将xtoken添加到httpHeader里，调用服务一定要添加认证过的token
        Map<String, String> headMap = getHeader("", config.getApi().getAccessId());
        // 打印loginUrl
        log.info("requestUrl=" + url);
        String responseData = httpRetry(() -> HttpPostRequestUtil.sendGetWithParams(url, loginParams, headMap));

        JSONObject jsonObj = new JSONObject(responseData);
        TcRecipeCardDataResponse dataResponse = JSON.parseObject(jsonObj.toString()
            , TcRecipeCardDataResponse.class);

        if(dataResponse.getStatus() != 1){
            throw new RuntimeException("查询成本卡失败：" + dataResponse.getMessage());
        }
        return dataResponse;
    }

    /**
     * query scm px
     *
     * @param config            config
     * @return {@link QueryShopInfoResponse}
     */
    public static TcScmPxDataResponse queryScmPxList(TcConfig config) {

        // 参数
        String url = applicationServer2 + URL_QUERY_SCM_PX_LIST;

        Map<String, String> loginParams = initScmParams(config);

        // 将xtoken添加到httpHeader里，调用服务一定要添加认证过的token
        Map<String, String> headMap = getHeader("", config.getApi().getAccessId());
        // 打印loginUrl
        log.info("requestUrl=" + url);
        String responseData = httpRetry(() -> HttpPostRequestUtil.sendGetWithParams(url, loginParams, headMap));

        JSONObject jsonObj = new JSONObject(responseData);
        TcScmPxDataResponse dataResponse = JSON.parseObject(jsonObj.toString()
            , TcScmPxDataResponse.class);

        if(!"200".equals(dataResponse.getCode())){
            throw new RuntimeException("查询品项失败：" + dataResponse.getMessage());
        }
        return dataResponse;
    }

    /**
     * query scm px
     *
     * @param config            config
     * @return {@link QueryShopInfoResponse}
     */
    public static TcScmDjmxDataResponse queryScmDjmxList(TcConfig config, TcScmDjmxRequest request) {

        // 参数
        StringBuilder url = new StringBuilder(applicationServer2);

        if(request.getOrderStatusType() == 0){
            url.append(URL_QUERY_SCM_DJMX_LIST);
        }else{
            url.append(URL_QUERY_SCM_DJMX_DELETE_LIST);
        }

        Map<String, String> loginParams = initScmParams(config);

        if(StringUtils.isNotBlank(request.getBeginDate())){
            loginParams.put("beginDate", request.getBeginDate());
        }

        if(StringUtils.isNotBlank(request.getEndDate())){
            loginParams.put("endDate", request.getEndDate());
        }

        if(StringUtils.isNotBlank(request.getLastModifyTime())){
            loginParams.put("busdate", request.getLastModifyTime());
        }

        // 将xtoken添加到httpHeader里，调用服务一定要添加认证过的token
        Map<String, String> headMap = getHeader("", config.getApi().getAccessId());
        // 打印loginUrl
        log.info("requestUrl=" + url);
        String responseData = httpRetry(() -> HttpPostRequestUtil.sendGetWithParams(url.toString(), loginParams, headMap));

        JSONObject jsonObj = new JSONObject(responseData);
        TcScmDjmxDataResponse dataResponse = JSON.parseObject(jsonObj.toString()
            , TcScmDjmxDataResponse.class);

        if(Boolean.FALSE.equals(dataResponse.getResult())){
            throw new RuntimeException("查询品项失败：" + dataResponse.getMessage());
        }
        return dataResponse;
    }

    /**
     * query scm px
     *
     * @param config            config
     * @return {@link QueryShopInfoResponse}
     */
    public static TcScmGysDataResponse queryScmGysList(TcConfig config) {

        // 参数
        String url = applicationServer2 + URL_QUERY_SCM_GYS_LIST;

        Map<String, String> loginParams = initScmParams(config);

        // 将xtoken添加到httpHeader里，调用服务一定要添加认证过的token
        Map<String, String> headMap = getHeader("", config.getApi().getAccessId());
        // 打印loginUrl
        log.info("requestUrl=" + url);
        String responseData = httpRetry(() -> HttpPostRequestUtil.sendGetWithParams(url, loginParams, headMap));

        JSONObject jsonObj = new JSONObject(responseData);
        TcScmGysDataResponse dataResponse = JSON.parseObject(jsonObj.toString()
            , TcScmGysDataResponse.class);

        if(!"200".equals(dataResponse.getCode())){
            throw new RuntimeException("查询品项失败：" + dataResponse.getMessage());
        }
        return dataResponse;
    }

    private static Map<String, String> initScmParams(TcConfig config) {
        Map<String, String> loginParams = new LinkedHashMap<>();
        loginParams.put("ent", "ENTa5bv");
        loginParams.put("username", "admin");
        loginParams.put("password", "tcsl123456");
        return loginParams;
    }

    /**
     * httpRetry
     * @param supplier 重试方法
     * @return 结果
     */
    public static String httpRetry(Supplier<String> supplier){
        return httpRetry(supplier,0,5);
    }

    /**
     * httpRetry 默认重试5次
     * @param supplier 重试方法
     * @param count 当前重试次数
     * @return 结果
     */
    public static String httpRetry(Supplier<String> supplier,int count){
        return httpRetry(supplier,count,5);
    }


    /**
     * httpRetry
     * @param supplier 重试方法
     * @param count 当前重试次数
     * @param maxCount 最大重试次数
     * @return 结果
     */
    public static String httpRetry(Supplier<String> supplier,int count,int maxCount){
        String result = supplier.get();

        while (StringUtils.isBlank(result) && count < maxCount) {
            // 警告日志 - 请求失败.
            log.warn("请求失败,正在重试!");
            // 重试.
            result = httpRetry(supplier, count++,maxCount);

            count++;
        }
        return result;
    }

    private static String getUrl(TcConfig config) {
        return config.getProtocol() + "://" + config.getUrl() + ":" + config.getPort();
    }

    /**
     * get begin time
     *
     * @return {@link String}
     */
    private static String getBeginTime() {
        // 获取当前时间
        String currentDateStr = DateUtil.now();

        // 将当前时间字符串解析为日期对象
        Date currentDate = DateUtil.parse(currentDateStr);

        // 将时间减去15分钟
        Date fifteenMinutesAgo = DateUtil.offset(currentDate, DateField.MINUTE, -14);

        // 格式化日期时间并返回.
        return DateUtil.format(fifteenMinutesAgo, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * get header
     *
     * @param token    token
     * @param accessId access id
     * @return {@link Map}<{@link String}, {@link String}>
     */
    public static Map<String, String> getHeader(String token, String accessId) {
        Map<String, String> headMap = new HashMap<>();
        headMap.put("access_token", token);
        headMap.put("accessid", accessId);
        headMap.put("granttype", "client");
        headMap.put("accept", "*/*");
        headMap.put("connection", "Keep-Alive");
        headMap.put("Content-Type", "application/json; charset=utf-8");
        headMap.put("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        return headMap;
    }
}
