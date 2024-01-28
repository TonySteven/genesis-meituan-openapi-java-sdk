package com.genesis.org.cn.genesismeituanopenapijavasdk.controller;

import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.base.BaseVO;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor.TcShopInfoQueryAndSaveCmdExe;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
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
    TcShopInfoQueryAndSaveCmdExe tcShopInfoQueryAndSaveCmdExe;


    /**
     * 金蝶SaaS-调用应付单
     *
     * @return {@link BaseVO}
     */
    @ApiOperation(value = "金蝶SaaS-调用应付单", notes = "金蝶SaaS-调用应付单")
    @GetMapping("/save-payable-order")
    public BaseVO savePayableOrder() {
        // 在执行器里面执行具体的业务逻辑.
        return tcShopInfoQueryAndSaveCmdExe.execute();
    }

}
