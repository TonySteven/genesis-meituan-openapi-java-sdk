package com.genesis.org.cn.genesismeituanopenapijavasdk.config;

import cn.hutool.json.JSONUtil;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor.TcShopBillingDetailQueryAndSaveCmdExe;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.request.TcShopBillingDetailQueryCmd;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * xxl-job bean模式
 * 根据bean name在界面配置定时任务
 *
 * @author xuzhen97
 */
@Component
@Slf4j
public class XxlJobBean {

    @Resource
    private TcShopBillingDetailQueryAndSaveCmdExe tcShopBillingDetailQueryAndSaveCmdExe;

    /**
     * 实时1分钟一次定时抽取
     */
    @XxlJob("syncTcShopBillDetailHandler")
    public void syncOABillStatusHandler() {
        String jobParam = XxlJobHelper.getJobParam();
        log.info("tcShopBillingDetailQueryAndSaveCmdExe jobParam:{}", jobParam);
        // 如果jobParam.isBlank，直接返回
        if (StringUtils.isBlank(jobParam)) {
            return;
        }
        // jobParam转ScStBillAuxiliaryOaQueryCmd
        TcShopBillingDetailQueryCmd cmd = JSONUtil.toBean(jobParam, TcShopBillingDetailQueryCmd.class);
        tcShopBillingDetailQueryAndSaveCmdExe.execute(cmd);
    }


}
