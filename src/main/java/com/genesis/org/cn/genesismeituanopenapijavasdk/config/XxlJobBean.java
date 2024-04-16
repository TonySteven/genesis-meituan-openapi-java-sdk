package com.genesis.org.cn.genesismeituanopenapijavasdk.config;

import cn.hutool.core.date.DatePattern;
import cn.hutool.json.JSONUtil;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy.goodlife.BaseAllCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy.goodlife.settle_ledger.DySettleLedgerRecordAllSyncCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy.goodlife.shop.ShopAllSyncCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request.KingdeeCredentialBillCalledCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request.KingdeePayableBillCalledCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request.TcBaseDataQryCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request.TcScmDjmxCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.dy.executor.DyFulfilmentVerifyRecordSyncCmdExe;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.dy.executor.DySettleLedgerRecordSyncCmdExe;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.dy.executor.DyShopSyncCmdExe;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor.*;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.request.TcShopBillingDetailQueryCmd;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * xxl-job bean模式
 * 根据bean name在界面配置定时任务
 *
 * @author steven
 * &#064;date  2024/02/12
 */
@Component
@Slf4j
public class XxlJobBean {

    @Resource
    TcShopInfoQueryAndSaveCmdExe tcShopInfoQueryAndSaveCmdExe;


    @Resource
    TcShopErrorBillingQueryAndSaveCmdExe tcShopErrorBillingQueryAndSaveCmdExe;

    @Resource
    private TcShopBillingDetailQueryAndSaveCmdExe tcShopBillingDetailQueryAndSaveCmdExe;

    @Resource
    private DyFulfilmentVerifyRecordSyncCmdExe dyFulfilmentVerifyRecordSyncCmdExe;

    @Resource
    private DyShopSyncCmdExe dySyncCmdExe;

    @Resource
    private DySettleLedgerRecordSyncCmdExe dySettleLedgerRecordSyncCmdExe;

    @Resource
    KingdeeSavePayableOrderCmdExe kingdeeSavePayableOrderCmdExe;

    @Resource
    KingdeeSaveCredentialOrderCmdExe kingdeeSaveCredentialOrderCmdExe;

    @Resource
    private TcBaseDataQueryAndSaveCmdExe tcBaseDataQueryAndSaveCmdExe;

    @Resource
    KingdeeSaveCashCredentialOrderCmdExe kingdeeSaveCashCredentialOrderCmdExe;

    @Resource
    TcScmDjmxQueryAndSaveCmdExe tcScmDjmxQueryAndSaveCmdExe;

    /**
     * 天财定时拉取门店信息任务.
     */
    @XxlJob("syncTcShopDataHandler")
    public void syncTcShopDataHandler() {
        String jobParam = XxlJobHelper.getJobParam();
        log.info("syncTcShopDataHandler jobParam:{}", jobParam);
        tcShopInfoQueryAndSaveCmdExe.execute();
    }

    /**
     * 天财定时修复门店异常pos账单任务.
     */
    @XxlJob("autoFixTcShopErrorBillHandler")
    public void autoFixTcShopErrorBillHandler() {
        String jobParam = XxlJobHelper.getJobParam();
        log.info("autoFixTcShopErrorBillHandler jobParam:{}", jobParam);
        tcShopErrorBillingQueryAndSaveCmdExe.execute();
    }

    /**
     * 天财定时拉取POS单据任务.
     */
    @XxlJob("syncTcShopBillDetailHandler")
    public void syncTcShopBillDetailHandler() {
        String jobParam = XxlJobHelper.getJobParam();
        log.info("tcShopBillingDetailQueryAndSaveCmdExe jobParam:{}", jobParam);
        // 如果jobParam.isBlank，直接返回
        if (StringUtils.isBlank(jobParam)) {
            return;
        }
        // jobParam转ScStBillAuxiliaryOaQueryCmd
        TcShopBillingDetailQueryCmd cmd = JSONUtil.toBean(jobParam, TcShopBillingDetailQueryCmd.class);
        // 打印cmd日志
        log.info("tcShopBillingDetailQueryAndSaveCmdExe cmd:{}", JSONUtil.toJsonStr(cmd));
        // 校验cmd.getBeginDate()和cmd.getEndDate()是否为空,如果为空,则默认查询前一天的数据.
        if (StringUtils.isBlank(cmd.getBeginDate()) || StringUtils.isBlank(cmd.getEndDate())) {
            // 打印日志 进入默认查询前一天的数据
            log.info("tcShopBillingDetailQueryAndSaveCmdExe cmd.getBeginDate() or cmd.getEndDate() is blank, default query previous day data.");
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
            cmd.setBeginDate(startOfDay.format(df));
            cmd.setEndDate(endOfDay.format(df));
        }
        tcShopBillingDetailQueryAndSaveCmdExe.execute(cmd);
    }

    /**
     * 每天凌晨二点同步
     */
    @XxlJob("syncDyFulfilmentVerifyRecordHandler")
    public void syncDyFulfilmentVerifyRecordHandler() {
        String jobParam = XxlJobHelper.getJobParam();
        log.info("dyDyFulfilmentVerifyRecordSyncCmdExe jobParam:{}", jobParam);
        // 如果jobParam.isBlank，直接返回
        if (StringUtils.isBlank(jobParam)) {
            return;
        }
        // jobParam转BaseAllCmd
        BaseAllCmd cmd = JSONUtil.toBean(jobParam, BaseAllCmd.class);
        // 打印cmd日志
        log.info("dyDyFulfilmentVerifyRecordSyncCmdExe cmd:{}", JSONUtil.toJsonStr(cmd));
        // 校验cmd.getBeginDate()和cmd.getEndDate()是否为空,如果为空,则默认查询前一天的数据.
        if (ObjectUtils.isEmpty(cmd.getStartTime()) || ObjectUtils.isEmpty(cmd.getEndTime())) {
            // 打印日志 进入默认查询前一天的数据
            log.info("dyDyFulfilmentVerifyRecordSyncCmdExe cmd.getStartTime() or cmd.getEndTime() is blank, default query previous day data.");
            // 获取前一天的时间的0点和24点.
            // 获取当前时间
            LocalDateTime now = LocalDateTime.now();
            // 获取前一天的时间
            LocalDateTime previousDay = now.minusDays(1);
            // 获取前一天的0点
            LocalDateTime startOfDay = previousDay.withHour(0).withMinute(0).withSecond(0);
            // 获取前一天的24点
            LocalDateTime endOfDay = previousDay.withHour(23).withMinute(59).withSecond(59);

            cmd.setStartTime(startOfDay);
            cmd.setEndTime(endOfDay);
        }
        dyFulfilmentVerifyRecordSyncCmdExe.executeAll(cmd);
    }

    /**
     * 每天凌晨一点同步抖音门店信息
     */
    @XxlJob("syncDyShopHandler")
    public void syncDyShopHandler() {
        String jobParam = XxlJobHelper.getJobParam();
        log.info("dySyncCmdExe jobParam:{}", jobParam);
        // 如果jobParam.isBlank，直接返回
        if (StringUtils.isBlank(jobParam)) {
            return;
        }
        // jobParam转ShopAllSyncCmd
        ShopAllSyncCmd cmd = JSONUtil.toBean(jobParam, ShopAllSyncCmd.class);
        // 打印cmd日志
        log.info("dySyncCmdExe cmd:{}", JSONUtil.toJsonStr(cmd));
        dySyncCmdExe.executeAll(cmd);
    }

    /**
     * 每天凌晨三点同步
     */
    @XxlJob("syncDySettleLedgerRecordHandler")
    public void syncDySettleLedgerRecordHandler() {
        String jobParam = XxlJobHelper.getJobParam();
        log.info("dySettleLedgerRecordSyncCmdExe jobParam:{}", jobParam);
        // 如果jobParam.isBlank，直接返回
        if (StringUtils.isBlank(jobParam)) {
            return;
        }
        // jobParam转DySettleLedgerRecordAllSyncCmd
        DySettleLedgerRecordAllSyncCmd cmd = JSONUtil.toBean(jobParam, DySettleLedgerRecordAllSyncCmd.class);
        // 打印cmd日志
        log.info("dySettleLedgerRecordSyncCmdExe cmd:{}", JSONUtil.toJsonStr(cmd));
        // 校验cmd.getBeginDate()和cmd.getEndDate()是否为空,如果为空,则默认查询前一天的数据.
        if (ObjectUtils.isEmpty(cmd.getStartTime()) || ObjectUtils.isEmpty(cmd.getEndTime())) {
            // 打印日志 进入默认查询前一天的数据
            log.info("dySettleLedgerRecordSyncCmdExe cmd.getStartTime() or cmd.getEndTime() is blank" +
                ", default query previous day data.");
            // 获取前一天的时间的0点和24点.
            // 获取当前时间
            LocalDateTime now = LocalDateTime.now();
            // 获取前一天的时间
            LocalDateTime previousDay = now.minusDays(1);
            // 获取前两天的0点 因为前一天的分账状态不一定是终态，往前推迟一天
            LocalDateTime startOfDay = previousDay.minusDays(1).withHour(0).withMinute(0).withSecond(0);
            // 获取前一天的24点
            LocalDateTime endOfDay = previousDay.withHour(23).withMinute(59).withSecond(59);

            cmd.setStartTime(startOfDay);
            cmd.setEndTime(endOfDay);
        }
        dySettleLedgerRecordSyncCmdExe.executeAll(cmd);
    }

    /**
     * 触发金蝶应付单调用
     */
    @XxlJob("callKingdeePayableOrderHandler")
    public void callKingdeePayableOrderHandler() {
        String jobParam = XxlJobHelper.getJobParam();
        log.info("callKingdeePayableOrderHandler jobParam:{}", jobParam);
        // 如果jobParam.isBlank，直接返回
        if (StringUtils.isBlank(jobParam)) {
            return;
        }
        // jobParam转KingdeePayableBillCalledCmd
        KingdeePayableBillCalledCmd cmd = JSONUtil.toBean(jobParam, KingdeePayableBillCalledCmd.class);
        // 打印cmd日志
        log.info("KingdeePayableBillCalledCmd cmd:{}", JSONUtil.toJsonStr(cmd));
        kingdeeSavePayableOrderCmdExe.execute(cmd);
    }

    /**
     * 触发金蝶生成凭证调用
     */
    @XxlJob("callKingdeeCredentialOrderHandler")
    public void callKingdeeCredentialOrderHandler() {
        String jobParam = XxlJobHelper.getJobParam();
        log.info("callKingdeeCredentialOrderHandler jobParam:{}", jobParam);
        // 如果jobParam.isBlank，直接返回
        if (StringUtils.isBlank(jobParam)) {
            return;
        }
        // jobParam转KingdeePayableBillCalledCmd
        KingdeeCredentialBillCalledCmd cmd = JSONUtil.toBean(jobParam, KingdeeCredentialBillCalledCmd.class);
        // 打印cmd日志
        log.info("KingdeeCredentialBillCalledCmd cmd:{}", JSONUtil.toJsonStr(cmd));
        kingdeeSaveCredentialOrderCmdExe.execute(cmd);
    }

    /**
     * 每天凌晨一点同步天才基础档案信息
     */
    @XxlJob("syncTcBaseDataHandler")
    public void syncTcBaseDataHandler() {
        String jobParam = XxlJobHelper.getJobParam();
        log.info("syncTcBaseDataHandler jobParam:{}", jobParam);
        // 如果jobParam.isBlank，直接返回
        if (StringUtils.isBlank(jobParam)) {
            return;
        }
        // jobParam转DySettleLedgerRecordAllSyncCmd
        TcBaseDataQryCmd cmd = JSONUtil.toBean(jobParam, TcBaseDataQryCmd.class);
        // 打印cmd日志
        log.info("syncTcBaseDataHandler cmd:{}", JSONUtil.toJsonStr(cmd));
        // 校验cmd.getItemQueryCmd().getLastTime()是否为空,如果为空,则默认查询前两天的数据.
        if (ObjectUtils.isEmpty(cmd.getItemQueryCmd().getLastTime())) {
            // 打印日志 进入默认查询前一天的数据
            log.info("syncTcBaseDataHandler cmd.getItemQueryCmd().getLastTime() is blank" +
                ", default query previous day data.");
            // 获取前一天的时间的0点和24点.
            // 获取当前时间
            LocalDateTime now = LocalDateTime.now();
            // 获取前一天的时间
            LocalDateTime previousDay = now.minusDays(2);
            // 获取前两天的0点 因为前一天的分账状态不一定是终态，往前推迟一天
            LocalDateTime startOfDay = previousDay.minusDays(1).withHour(0).withMinute(0).withSecond(0);

            cmd.getItemQueryCmd().setLastTime(startOfDay.format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
        }
        tcBaseDataQueryAndSaveCmdExe.execute(cmd);
    }


    /**
     * 触发金蝶生成收银凭证调用
     */
    @XxlJob("callKingdeeCashCredentialOrderHandler")
    public void callKingdeeCashCredentialOrderHandler() {
        String jobParam = XxlJobHelper.getJobParam();
        log.info("callKingdeeCashCredentialOrderHandler jobParam:{}", jobParam);
        // 如果jobParam.isBlank，直接返回
        if (StringUtils.isBlank(jobParam)) {
            return;
        }
        // jobParam转KingdeePayableBillCalledCmd
        KingdeeCredentialBillCalledCmd cmd = JSONUtil.toBean(jobParam, KingdeeCredentialBillCalledCmd.class);
        // 打印cmd日志
        log.info("KingdeeCredentialBillCalledCmd cmd:{}", JSONUtil.toJsonStr(cmd));
        kingdeeSaveCashCredentialOrderCmdExe.execute(cmd);
    }
    /**
     * 每天凌晨一点同步天才供应链单据明细
     */
    @XxlJob("syncTcScmDjmxHandler")
    public void syncTcScmDjmxHandler() {
        String jobParam = XxlJobHelper.getJobParam();
        log.info("syncTcScmDjmxHandler jobParam:{}", jobParam);
        // 如果jobParam.isBlank，直接返回
        if (StringUtils.isBlank(jobParam)) {
            return;
        }
        // jobParam转TcScmDjmxCmd
        TcScmDjmxCmd cmd = JSONUtil.toBean(jobParam, TcScmDjmxCmd.class);
        // 打印cmd日志
        log.info("syncTcScmDjmxHandler cmd:{}", JSONUtil.toJsonStr(cmd));

        tcScmDjmxQueryAndSaveCmdExe.execute(cmd);
    }

}
