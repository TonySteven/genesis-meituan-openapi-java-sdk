package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model;

import com.alibaba.fastjson.JSON;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.HttpPostRequestUtil;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.LoginToServerAction;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.enums.ResponseStatusEnum;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.LoginResult;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.QueryShopInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

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
     * url login
     */
    static final String URL_LOGIN = "/api/datatransfer/getshops";

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
        try {
            LoginResult result = LoginToServerAction.login(protocol, applicationServer, applicationPort, appId, accessId);
            if (ResponseStatusEnum.SUCCESS.getValue().equals(result.getMsg())) {
                // 成功登录 到O2Server
                log.info("成功鉴权！token=" + result.getAccessToken());
                // 启动指定的流程
                QueryShopInfoResponse queryShopInfoResponse = queryShopInfo(protocol
                    , applicationServer, applicationPort, accessId, result.getAccessToken(), 1, 10);
                if (Boolean.TRUE.equals(queryShopInfoResponse.getSuccess())) {
                    log.info("查询店铺信息成功！data=" + JSON.toJSONString(queryShopInfoResponse.getData()));
                } else {
                    log.info("查询店铺信息失败！message=" + queryShopInfoResponse.getMsg());
                }
            } else {
                // 登录失败
                log.info("登录失败！message:" + result.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        String url = protocol + "://" + applicationServer + ":" + applicationPort + URL_LOGIN;
        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("centerId", centerId);
        loginParams.put("pageNo", String.valueOf(pageNo));
        loginParams.put("pageSize", String.valueOf(pageSize));

        // 将xtoken添加到httpHeader里，调用服务一定要添加认证过的token
        Map<String, String> headMap = getHeader(token, accessId);
        // 打印loginUrl
        log.info("requestUrl=" + url);
        String responseData = HttpPostRequestUtil.sendPostWithParams(url, loginParams, headMap);
        // responseData如果blank，说明请求失败
        if (org.apache.commons.lang3.StringUtils.isBlank(responseData)) {
            // 如果responseData为空, 则抛出异常.
            throw new RuntimeException("请求店铺信息失败!");
        }
        JSONObject jsonObj = new JSONObject(responseData);
        return JSON.parseObject(jsonObj.toString()
            , QueryShopInfoResponse.class);
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
