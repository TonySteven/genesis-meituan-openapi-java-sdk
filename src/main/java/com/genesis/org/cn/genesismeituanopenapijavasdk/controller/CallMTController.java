package com.genesis.org.cn.genesismeituanopenapijavasdk.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * 调用美团接口
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

    @GetMapping("/auth")
    public String getJsapiTicket() {
        log.info("调用美团接口");
        return "success";
    }
}
