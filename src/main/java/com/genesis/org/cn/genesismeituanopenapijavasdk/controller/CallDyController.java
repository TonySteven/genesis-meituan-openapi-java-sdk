package com.genesis.org.cn.genesismeituanopenapijavasdk.controller;

import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy.goodlife.BaseAllCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy.goodlife.fulfilment_verify.FulfilmentVerifyRecordSyncCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy.goodlife.settle_ledger.DySettleLedgerRecordAllSyncCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy.goodlife.settle_ledger.DySettleLedgerRecordSyncCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy.goodlife.shop.ShopAllSyncCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.result.ApiResult;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.dy.executor.DyFulfilmentVerifyRecordSyncCmdExe;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.dy.executor.DySettleLedgerRecordSyncCmdExe;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.dy.executor.DyShopSyncCmdExe;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.oas.annotations.EnableOpenApi;

import javax.annotation.Resource;
import java.util.List;

/**
 * 抖音SaaS 相关接口
 *
 * @author LP
 * &#064;date  2024/01/28
 */
@EnableOpenApi
@Api(tags = "调用抖音接口")
@RestController
@RequestMapping("/dy/api")
public class CallDyController {

    @Resource
    private DyShopSyncCmdExe dyShopSyncCmdExe;

    @Resource
    private DyFulfilmentVerifyRecordSyncCmdExe dyFulfilmentVerifyRecordSyncCmdExe;

    @Resource
    private DySettleLedgerRecordSyncCmdExe dySettleLedgerRecordSyncCmdExe;

    /**
     * 抖音SaaS-同步抖音门店信息api
     * @param cmd 入参
     * @return 结果
     */
    @ApiOperation(value = "抖音SaaS-同步抖音门店信息api", notes = "抖音SaaS-同步抖音门店信息api")
    @GetMapping("/syncShop")
    public ApiResult<Object> syncShop(@Validated ShopAllSyncCmd cmd) {
        return dyShopSyncCmdExe.executeAll(cmd);
    }

    /**
     * 抖音SaaS-同步抖音验券记录信息api
     * @param cmd 入参
     * @return 结果
     */
    @ApiOperation(value = "抖音SaaS-同步抖音验券记录信息api", notes = "抖音SaaS-同步抖音验券记录信息api")
    @GetMapping("/syncFulfilmentVerifyRecord")
    public ApiResult<List<String>> syncFulfilmentVerifyRecord(@Validated FulfilmentVerifyRecordSyncCmd cmd) {
        return dyFulfilmentVerifyRecordSyncCmdExe.execute(cmd);
    }

    /**
     * 抖音SaaS-同步全商户抖音验券记录信息api
     * @param cmd 入参
     * @return 结果
     */
    @ApiOperation(value = "抖音SaaS-同步全商户抖音验券记录信息api", notes = "抖音SaaS-同步全商户抖音验券记录信息api")
    @GetMapping("/syncAllFulfilmentVerifyRecord")
    public ApiResult<Object> syncAllFulfilmentVerifyRecord(@Validated BaseAllCmd cmd) {
        return dyFulfilmentVerifyRecordSyncCmdExe.executeAll(cmd);
    }

    /**
     * 抖音SaaS-同步抖音分账记录信息api
     * @param cmd 入参
     * @return 结果
     */
    @ApiOperation(value = "抖音SaaS-同步抖音分账记录信息api", notes = "抖音SaaS-同步抖音分账记录信息api")
    @GetMapping("/syncSettleLedgerRecord")
    public ApiResult<List<String>> syncSettleLedgerRecord(@Validated DySettleLedgerRecordSyncCmd cmd) {
        return dySettleLedgerRecordSyncCmdExe.execute(cmd);
    }

    /**
     * 抖音SaaS-同步全抖音分账记录信息api
     * @param cmd 入参
     * @return 结果
     */
    @ApiOperation(value = "抖音SaaS-同步全抖音分账记录信息api", notes = "抖音SaaS-同步全抖音分账记录信息api")
    @GetMapping("/syncAllSettleLedgerRecord")
    public ApiResult<Object> syncAllSettleLedgerRecord(@Validated DySettleLedgerRecordAllSyncCmd cmd) {
        return dySettleLedgerRecordSyncCmdExe.executeAll(cmd);
    }
}
