package com.genesis.org.cn.genesismeituanopenapijavasdk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@MapperScan("com.genesis.org.cn.genesismeituanopenapijavasdk.dao.mapper")
public class GenesisMeituanOpenapiJavaSdkApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenesisMeituanOpenapiJavaSdkApplication.class, args);
    }

}
