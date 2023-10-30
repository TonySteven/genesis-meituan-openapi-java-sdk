package com.genesis.org.cn.genesismeituanopenapijavasdk.controller;

import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.base.BaseVO;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request.MtShopCommentQryCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor.MtShopCommentQueryCmdExe;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor.MtShopCommentScoreQueryAndSaveCmdExe;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor.MtShopIdQueryAndSaveCmdExe;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor.MtShopInfoQueryAndSaveCmdExe;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.oas.annotations.EnableOpenApi;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 美团SaaS 相关接口
 *
 * @author steven
 * &#064;date  2023/10/24
 */
@EnableOpenApi
@Api(tags = "调用美团接口")
@RestController
@RequestMapping("/mt/api")
public class CallMTController {

    @Resource
    MtShopCommentQueryCmdExe mtShopCommentQueryCmdExe;

    @Resource
    MtShopIdQueryAndSaveCmdExe mtShopIdQueryAndSaveCmdExe;

    @Resource
    MtShopInfoQueryAndSaveCmdExe mtShopInfoQueryAndSaveCmdExe;

    @Resource
    MtShopCommentScoreQueryAndSaveCmdExe mtShopCommentScoreQueryAndSaveCmdExe;

    @ApiOperation(value = "美团SaaS-调用查询所有门店并落库api", notes = "美团SaaS-调用查询所有门店并落库api")
    @GetMapping("/save-shopId")
    public BaseVO saveShopId() {
        // 在执行器里面执行具体的业务逻辑.
        return mtShopIdQueryAndSaveCmdExe.execute();
    }


    /**
     * 美团SaaS-调用查询所有门店详情并落库api
     *
     * @return {@link BaseVO}
     */
    @ApiOperation(value = "美团SaaS-调用查询所有门店详情并落库api", notes = "美团SaaS-调用查询所有门店详情并落库api")
    @GetMapping("/save-shopInfo")
    public BaseVO saveShopInfo() {
        // 在执行器里面执行具体的业务逻辑.
        return mtShopInfoQueryAndSaveCmdExe.execute();
    }

    @ApiOperation(value = "美团SaaS-定时调用评论并落库api", notes = "美团SaaS-定时调用评论并落库api")
    @PostMapping("/save-comment")
    public BaseVO saveComment(@RequestBody @Valid MtShopCommentQryCmd cmd) {
        // 在执行器里面执行具体的业务逻辑.
        return mtShopCommentQueryCmdExe.execute(cmd);
    }

    /**
     * 美团SaaS-调用查询所有门店总评分并落库api
     *
     * @return {@link BaseVO}
     */
    @ApiOperation(value = "美团SaaS-调用查询所有门店总评分并落库api", notes = "美团SaaS-调用查询所有门店总评分并落库api")
    @GetMapping("/save-comment-score")
    public BaseVO saveCommentScore() {
        // 在执行器里面执行具体的业务逻辑.
        return mtShopCommentScoreQueryAndSaveCmdExe.execute();
    }
}
