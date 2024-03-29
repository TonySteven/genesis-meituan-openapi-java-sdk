package com.genesis.org.cn.genesismeituanopenapijavasdk.controller;

import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.base.BaseVO;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request.KingdeeCredentialBillCalledCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request.KingdeePayableBillCalledCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor.KingdeeSaveCashCredentialOrderCmdExe;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor.KingdeeSaveCredentialOrderCmdExe;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor.KingdeeSavePayableOrderCmdExe;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.oas.annotations.EnableOpenApi;

import javax.annotation.Resource;

/**
 * 金蝶SaaS 相关接口
 *
 * @author steven
 * &#064;date  2023/10/24
 */
@EnableOpenApi
@Api(tags = "调用金蝶接口")
@RestController
@RequestMapping("/kingdee/api")
public class CallKingdeeController {

    @Resource
    KingdeeSavePayableOrderCmdExe kingdeeSavePayableOrderCmdExe;

    @Resource
    KingdeeSaveCredentialOrderCmdExe kingdeeSaveCredentialOrderCmdExe;

    @Resource
    KingdeeSaveCashCredentialOrderCmdExe kingdeeSaveCashCredentialOrderCmdExe;


    /**
     * 金蝶SaaS-调用应付单
     *
     * @return {@link BaseVO}
     */
    @ApiOperation(value = "金蝶SaaS-调用应付单", notes = "金蝶SaaS-调用应付单")
    @PostMapping("/save-payable-order")
    public BaseVO savePayableOrder(@RequestBody @Validated KingdeePayableBillCalledCmd cmd) {
        // 在执行器里面执行具体的业务逻辑.
        return kingdeeSavePayableOrderCmdExe.execute(cmd);
    }

    /**
     * 金蝶SaaS-调用生成凭证API
     *
     * @return {@link BaseVO}
     */
    @ApiOperation(value = "金蝶SaaS-调用生成凭证API", notes = "金蝶SaaS-调用生成凭证API")
    @PostMapping("/save-credential-order")
    public BaseVO saveCredentialOrder(@RequestBody @Validated KingdeeCredentialBillCalledCmd cmd) {
        // 在执行器里面执行具体的业务逻辑.
        return kingdeeSaveCredentialOrderCmdExe.execute(cmd);
    }

    /**
     * 金蝶SaaS-调用生成收银凭证API
     *
     * @return {@link BaseVO}
     */
    @ApiOperation(value = "金蝶SaaS-调用生成收银凭证API", notes = "金蝶SaaS-调用生成收银凭证API")
    @PostMapping("/save-cash-credential-order")
    public BaseVO saveCashCredentialOrder(@RequestBody @Validated KingdeeCredentialBillCalledCmd cmd) {
        // 在执行器里面执行具体的业务逻辑.
        return kingdeeSaveCashCredentialOrderCmdExe.execute(cmd);
    }

}
