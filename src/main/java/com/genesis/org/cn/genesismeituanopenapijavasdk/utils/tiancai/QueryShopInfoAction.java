package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.HttpPostRequestUtil;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.enums.ResponseStatusEnum;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.LoginResult;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.QueryBillDetailsInRealTimeResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.QueryBillDetailsResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.QueryShopInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        String responseData = HttpPostRequestUtil.sendPostWithParams(url, loginParams, headMap);

        // responseData如果blank，说明请求失败,并一直请求,直到请求成功.
        while (StringUtils.isBlank(responseData)) {
            // 警告日志 - 请求店铺实时账单失败.
            log.warn("请求店铺信息失败,正在重试!");
            // 重试.
            responseData = HttpPostRequestUtil.sendPostWithParams(url, loginParams, headMap);
        }
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
        String responseData = HttpPostRequestUtil.sendPostWithParams(url, loginParams, headMap);
        // responseData如果blank，说明请求失败,并一直请求,直到请求成功.
        while (StringUtils.isBlank(responseData)) {
            // 警告日志 - 请求店铺实时账单失败.
            log.warn("请求店铺实时账单失败,正在重试!");
            // 重试.
            responseData = HttpPostRequestUtil.sendPostWithParams(url, loginParams, headMap);
        }
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
        String responseData = HttpPostRequestUtil.sendPostWithParams(url, loginParams, headMap);
        // responseData如果blank，说明请求失败,并一直请求,直到请求成功.
        while (StringUtils.isBlank(responseData)) {
            // 警告日志 - 请求店铺实时账单失败.
            log.warn("请求店铺实时账单失败,正在重试!");
            // 重试.
            responseData = HttpPostRequestUtil.sendPostWithParams(url, loginParams, headMap);
        }

        JSONObject jsonObj = new JSONObject(responseData);
        return JSON.parseObject(jsonObj.toString()
            , QueryBillDetailsResponse.class);
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
