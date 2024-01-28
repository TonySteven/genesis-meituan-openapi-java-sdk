package com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor;

import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcShopDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcShopGroupInfoDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.base.BaseVO;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request.KingdeeSavePayableOrderRequest;
import com.google.gson.Gson;
import com.kingdee.bos.webapi.entity.RepoRet;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static org.jsoup.helper.Validate.fail;

/**
 * 金蝶SaaS-调用保存应付单具体逻辑.
 *
 * @author steven
 * &#064;date  2023/10/29
 */
@Service
@Slf4j
public class KingdeeSavePayableOrderCmdExe {


    @Resource
    private ITcShopDao iTcShopDao;

    @Resource
    private ITcShopGroupInfoDao iTcShopGroupInfoDao;

    /**
     * execute
     * 执行
     *
     * @return {@link BaseVO}
     */
    @SneakyThrows
    public BaseVO execute() {
        // 打印日志 - 开始.
        log.info("KingdeeSavePayableOrderCmdExe.execute() start");

        // 读取配置，初始化SDK
        K3CloudApi client = new K3CloudApi();

        // 拼接入参对象.
        KingdeeSavePayableOrderRequest kingdeeSavePayableOrderRequest = getKingdeeSavePayableOrderRequest();
        // kingdeeSavePayableOrderRequest转jsonString.
        String jsonData = new Gson().toJson(kingdeeSavePayableOrderRequest);

        try {
            // 业务对象标识
            String formId = "AP_Payable";
            // 调用接口
            String resultJson = client.save(formId, jsonData);

            // 用于记录结果
            Gson gson = new Gson();
            // 对返回结果进行解析和校验
            RepoRet repoRet = gson.fromJson(resultJson, RepoRet.class);
            if (repoRet.getResult().getResponseStatus().isIsSuccess()) {
                log.info("接口返回结果: {}", gson.toJson(repoRet.getResult()));
            } else {
                fail("接口返回结果: " + gson.toJson(repoRet.getResult().getResponseStatus()));
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }

        // 返回参数.
        return BaseVO.builder()
            .id("1")
            .state("success")
            .msg("成功")
            .build();
    }

    /**
     * get kingdee save payable order request
     * 拼接KingdeeSavePayableOrderRequest对象方法
     *
     * @return {@link KingdeeSavePayableOrderRequest}
     */
    private KingdeeSavePayableOrderRequest getKingdeeSavePayableOrderRequest() {

        return KingdeeSavePayableOrderRequest.builder()
            .NumberSearch("true")
            .build();
    }
}
