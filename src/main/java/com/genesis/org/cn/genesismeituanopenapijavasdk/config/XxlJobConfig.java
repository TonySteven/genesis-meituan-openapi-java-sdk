package com.genesis.org.cn.genesismeituanopenapijavasdk.config;

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

    // accessToken
    @Value("${xxl.job.executor.accessToken}")
    private String accessToken;

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
        xxlJobSpringExecutor.setAccessToken(accessToken);

        return xxlJobSpringExecutor;
    }
}
