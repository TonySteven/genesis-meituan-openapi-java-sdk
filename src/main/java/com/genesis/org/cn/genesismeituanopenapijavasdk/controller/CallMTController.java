package com.genesis.org.cn.genesismeituanopenapijavasdk.controller;

import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.base.BaseVO;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request.MtShopCommentQryCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor.MtShopCommentQueryCmdExe;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @ApiOperation(value = "美团SaaS-定时调用并落库api", notes = "美团SaaS-定时调用并落库api")
    @PostMapping("/saveComment")
    public BaseVO saveComment(@RequestBody @Valid MtShopCommentQryCmd cmd) {
        // 在执行器里面执行具体的业务逻辑.
        return mtShopCommentQueryCmdExe.execute(cmd);
    }
}
