package com.genesis.org.cn.genesismeituanopenapijavasdk.config;

import cn.hutool.core.util.StrUtil;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * xxl job config
 *
 * @author steven
 * &#064;date  2024/01/07
 */
@Configuration
@Slf4j
public class XxlJobConfig {

    @Value("${xxl.job.admin.addresses}")
    private String adminAddresses;

    @Value("${xxl.job.executor.appName}")
    private String appname;

    @Value("${xxl.job.executor.logPath}")
    private String logPath;

    @Value("${xxl.job.executor.ip}")
    private String ip;

    @Value("${xxl.job.executor.port}")
    private String port;

    @Value("${xxl.job.executor.intentionalities}")
    private int logRetentionDays;


    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info("====== xxl-job config init. ======");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(adminAddresses);

        xxlJobSpringExecutor.setAppname(appname);
        xxlJobSpringExecutor.setLogPath(logPath);
        xxlJobSpringExecutor.setLogRetentionDays(logRetentionDays);

        if (StrUtil.isNotBlank(ip)) {
            xxlJobSpringExecutor.setIp(ip);
            xxlJobSpringExecutor.setPort(Integer.parseInt(port));
        }

        return xxlJobSpringExecutor;
    }
}
