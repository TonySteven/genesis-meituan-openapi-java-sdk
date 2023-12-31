package com.genesis.org.cn.genesismeituanopenapijavasdk.controller;

import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.base.BaseVO;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor.TcShopBillingDetailInRealTimeQueryAndSaveCmdExe;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor.TcShopBillingDetailQueryAndSaveCmdExe;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor.TcShopInfoQueryAndSaveCmdExe;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.request.TcShopBillingDetailQueryCmd;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.oas.annotations.EnableOpenApi;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

/**
 * 天财SaaS 相关接口
 *
 * @author steven
 * &#064;date  2023/10/24
 */
@EnableOpenApi
@Api(tags = "调用天财接口")
@RestController
@RequestMapping("/tc/api")
public class CallTCController {

    @Resource
    TcShopInfoQueryAndSaveCmdExe tcShopInfoQueryAndSaveCmdExe;

    @Resource
    TcShopBillingDetailQueryAndSaveCmdExe tcShopBillingDetailQueryAndSaveCmdExe;

    @Resource
    TcShopBillingDetailInRealTimeQueryAndSaveCmdExe tcShopBillingDetailInRealTimeQueryAndSaveCmdExe;


    /**
     * 天财SaaS-调用查询所有门店详情并落库api
     *
     * @return {@link BaseVO}
     */
    @ApiOperation(value = "天财SaaS-调用查询所有门店详情并落库api", notes = "天财SaaS-调用查询所有门店详情并落库api")
    @GetMapping("/save-shopInfo")
    public BaseVO saveShopInfo() {
        // 在执行器里面执行具体的业务逻辑.
        return tcShopInfoQueryAndSaveCmdExe.execute();
    }

    /**
     * 天财SaaS-调用获取账单明细实时并落库api
     *
     * @return {@link BaseVO}
     */
    @ApiOperation(value = "天财SaaS-调用获取账单明细实时并落库api", notes = "天财SaaS-调用获取账单明细实时并落库api")
    @GetMapping("/save-billing-details-in-real-time")
    public BaseVO saveBillingDetailsInRealTime() {
        // 在执行器里面执行具体的业务逻辑.
        return tcShopBillingDetailInRealTimeQueryAndSaveCmdExe.execute();
    }

    /**
     * 天财SaaS-调用获取账单明细实时并落库api
     *
     * @return {@link BaseVO}
     */
    @ApiOperation(value = "天财SaaS-调用获取账单明细落库api", notes = "天财SaaS-调用获取账单明细实时并落库api")
    @GetMapping("/save-billing-details")
    public BaseVO saveBillingDetails(@RequestBody @Valid TcShopBillingDetailQueryCmd cmd) {
        // 异步执行 tcShopBillingDetailQueryAndSaveCmdExe.execute(cmd)
        CompletableFuture.runAsync(() -> tcShopBillingDetailQueryAndSaveCmdExe.execute(cmd));

        // 返回成功
        return BaseVO.builder()
            .id("1")
            .state("success")
            .msg("异步执行中")
            .build();
    }
}
