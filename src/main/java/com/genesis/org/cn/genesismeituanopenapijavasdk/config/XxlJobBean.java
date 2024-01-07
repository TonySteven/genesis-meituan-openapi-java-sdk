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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        // 校验cmd.getBeginDate()和cmd.getEndDate()是否为空,如果为空,则默认查询前一天的数据.
        if (StringUtils.isBlank(cmd.getBeginDate().toString()) || StringUtils.isBlank(cmd.getEndDate().toString())) {
            // 获取前一天的时间的0点和24点.
            // 获取当前时间
            LocalDateTime now = LocalDateTime.now();
            // 获取前一天的时间
            LocalDateTime previousDay = now.minusDays(1);
            // 获取前一天的0点
            LocalDateTime startOfDay = previousDay.withHour(0).withMinute(0).withSecond(0);
            // 获取前一天的24点
            LocalDateTime endOfDay = previousDay.withHour(23).withMinute(59).withSecond(59);
            // 转换成Date
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            cmd.setBeginDate(java.sql.Timestamp.valueOf(startOfDay.format(df)));
            cmd.setEndDate(java.sql.Timestamp.valueOf(endOfDay.format(df)));
        }
        tcShopBillingDetailQueryAndSaveCmdExe.execute(cmd);
    }


}
