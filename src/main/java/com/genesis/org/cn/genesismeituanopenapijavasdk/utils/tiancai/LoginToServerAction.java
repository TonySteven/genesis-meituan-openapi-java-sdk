package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai;

import com.alibaba.fastjson.JSON;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.HttpPostRequestUtil;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.enums.ResponseStatusEnum;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.LoginResult;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * 此示例演示如何使用java发送Http请求来登录o2server，获取登录认证xtoken信息
 *
 * @author O2OA
 */
@Slf4j
public class LoginToServerAction {

    /**
     * url login
     */
    static final String URL_LOGIN = "/api/auth/accesstoken";

    /**
     * 服务器请求协议
     */
    @Value("${tiancai.protocol}")
    private static String protocol = "https";

    /**
     * oa服务器地址
     */
    @Value("${tiancai.url}")
    private static String applicationServer = "cysms.wuuxiang.com";
    /**
     * oa服务器端口
     */
    @Value("${tiancai.port}")
    private static Integer applicationPort = 443;
    /**
     * oa用户名
     */
    @Value("${tiancai.api.appId}")
    private static String appId = "2a36bf799e6b4c90941bf84b67789111";
    /**
     * oa密码
     */
    @Value("${tiancai.api.accessId}")
    private static String accessId = "a1fca758ed104119889f46df3528d9fa";

    public static void main(String[] args) {
        try {
            LoginResult result = login(protocol, applicationServer, applicationPort, appId, accessId);
            if (ResponseStatusEnum.SUCCESS.getValue().equals(result.getMsg())) {
                log.info("xtoken=" + result.getAccessToken());
            } else {
                log.info("message:" + result.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * login
     *
     * @param applicationServer 127.0.0.1
     * @param applicationPort   20020
     * @param protocol          protocol
     * @param appId             app id
     * @param accessId          access id
     * @return {@link LoginResult}
     */
    public static LoginResult login(String protocol, String applicationServer, Integer applicationPort
        , String appId, String accessId) {
        // 参数
        String loginUrl = protocol + "://" + applicationServer + ":" + applicationPort + URL_LOGIN;
        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("appid", appId);
        loginParams.put("accessid", accessId);
        loginParams.put("response_type", "token");

        // 将xtoken添加到httpHeader里，调用服务一定要添加认证过的token
        Map<String, String> headMap = getBaseHeader();
        // 打印loginUrl
        log.info("loginUrl=" + loginUrl);
        String responseData = HttpPostRequestUtil.sendPostWithParams(loginUrl, loginParams, headMap);
        // responseData如果blank，说明请求失败
        if (org.apache.commons.lang3.StringUtils.isBlank(responseData)) {
            return LoginResult.builder().msg("error").accessToken(null).build();
        }
        JSONObject jsonObj = new JSONObject(responseData);
        return JSON.parseObject(jsonObj.toString()
            , LoginResult.class);
    }

    /**
     * get base header
     *
     * @return {@link Map}<{@link String}, {@link String}>
     */
    public static Map<String, String> getBaseHeader() {
        Map<String, String> headMap = new HashMap<>();
        headMap.put("accept", "*/*");
        headMap.put("connection", "Keep-Alive");
        headMap.put("Content-Type", "application/json; charset=utf-8");
        headMap.put("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        return headMap;
    }
}
