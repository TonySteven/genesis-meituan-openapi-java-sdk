package com.genesis.org.cn.genesismeituanopenapijavasdk.controller;

import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.base.BaseVO;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor.TcShopBillingDetailQueryAndSaveCmdExe;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor.TcShopInfoQueryAndSaveCmdExe;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.oas.annotations.EnableOpenApi;

import javax.annotation.Resource;

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
    public BaseVO saveCommentScore() {
        // 在执行器里面执行具体的业务逻辑.
        return tcShopBillingDetailQueryAndSaveCmdExe.execute();
    }
}
