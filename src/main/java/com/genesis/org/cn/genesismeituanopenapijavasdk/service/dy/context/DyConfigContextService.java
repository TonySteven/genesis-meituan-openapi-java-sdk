package com.genesis.org.cn.genesismeituanopenapijavasdk.service.dy.context;

import com.genesis.org.cn.genesismeituanopenapijavasdk.enums.DyAppEnums;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.config.DyConfig;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.service.DyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 抖音 配置信息上下文服务
 */
@Slf4j
@Service
public class DyConfigContextService {

    /**
     * 抖音服务配置信息
     */
    private static final Map<String, DyService> dyServiceMap = new ConcurrentHashMap<>();

    public synchronized void initDyService(DyConfig dyConfig) {
        log.info("{}初始化抖音服务配置信息, dyConfig:{}",DyConfig.LOG_PREFIX,dyConfig);

        DyService dyService = DyService.dyBaseService(dyConfig);

        dyServiceMap.put(dyConfig.getAppId(), dyService);
    }

    public synchronized void initDyService(String appId,String appSecret) {
        DyConfig dyConfig = new DyConfig();
        dyConfig.setAppId(appId);
        dyConfig.setAppSecret(appSecret);
        initDyService(dyConfig);
    }

    public synchronized void initDyService(String appId) {
        log.info("{}初始化抖音服务配置信息, appId:{}",DyConfig.LOG_PREFIX,appId);

        // 获取APP ID对应的抖音服务配置信息
        DyAppEnums dyAppEnum = DyAppEnums.getByAppId(appId);
        if(ObjectUtils.isEmpty(dyAppEnum)){
            log.warn("{}未找到抖音服务配置信息, appId:{}",DyConfig.LOG_PREFIX,appId);
            throw new RuntimeException("未找到抖音服务配置信息, appId: " + appId);
        }

        // 初始化抖音服务配置信息
        initDyService(dyAppEnum.getAppId(),dyAppEnum.getAppSecret());

    }

    public DyService getDyService(String appId) {
        DyService dyService = dyServiceMap.get(appId);
        if(ObjectUtils.isEmpty(dyService)){
            initDyService(appId);
        }
        return dyServiceMap.get(appId);
    }
}
