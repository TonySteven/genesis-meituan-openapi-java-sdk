package com.genesis.org.cn.genesismeituanopenapijavasdk.controller;

import com.bty.scm.boot.jointblock.core.ExecutorContext;
import com.genesis.org.cn.genesismeituanopenapijavasdk.api.base.BaseVO;
import com.genesis.org.cn.genesismeituanopenapijavasdk.api.request.MtShopCommentQryCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor.MtShopCommentQueryCmdExe;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.oas.annotations.EnableOpenApi;

import javax.validation.Valid;

/**
 * 美团SaaS 相关接口
 *
 * @author steven
 * &#064;date  2023/10/24
 */
@EnableOpenApi
@Slf4j
@AllArgsConstructor
@RestController
@Api(tags = "调用美团接口")
@RequestMapping("/mt/api")
public class CallMTController {

    @ApiOperation(value = "美团SaaS-  定时调用并落库api", notes = "美团SaaS-  定时调用并落库api")
    @PostMapping("/saveComment")
    public BaseVO saveComment(@RequestBody @Valid MtShopCommentQryCmd cmd) {
        // 在执行器里面执行具体的业务逻辑.
        return ExecutorContext.get(MtShopCommentQueryCmdExe.class).execute(cmd);
    }
}
