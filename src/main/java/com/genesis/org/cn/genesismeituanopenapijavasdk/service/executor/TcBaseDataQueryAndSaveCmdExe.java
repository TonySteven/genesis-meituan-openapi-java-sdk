package com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor;

import com.genesis.org.cn.genesismeituanopenapijavasdk.config.TcConfig;
import com.genesis.org.cn.genesismeituanopenapijavasdk.enums.TcSyncTypeEnums;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request.TcBaseDataQryCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.result.ApiResult;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.LoginToServerAction;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 天才基础信息同步命令执行器
 */
@Slf4j
@Service
public class TcBaseDataQueryAndSaveCmdExe {

    /**
     * 天才配置
     */
    @Resource
    private TcConfig tcConfig;

    @Resource
    private TcItemCategoryQueryAndSaveCmdExe tcItemCategoryQueryAndSaveCmdExe;

    @Resource
    private TcItemMethodClassesQueryAndSaveCmdExe tcItemMethodClassesQueryAndSaveCmdExe;

    @Resource
    private TcItemMethodsQueryAndSaveCmdExe tcItemMethodsQueryAndSaveCmdExe;

    @Resource
    private TcItemQueryAndSaveCmdExe tcItemQueryAndSaveCmdExe;

    @Resource
    private TcPayTypeQueryAndSaveCmdExe tcPayTypeQueryAndSaveCmdExe;

    @Resource
    private TcPaywayDetailQueryAndSaveCmdExe tcPaywayDetailQueryAndSaveCmdExe;

    @Resource
    private TcRecipeCardQueryAndSaveCmdExe tcRecipeCardQueryAndSaveCmdExe;

    @Resource
    private TcScmPxQueryAndSaveCmdExe tcScmPxQueryAndSaveCmdExe;

    @Resource
    private TcScmGysQueryAndSaveCmdExe tcScmGysQueryAndSaveCmdExe;

    @SneakyThrows
    public ApiResult<Object> execute(TcBaseDataQryCmd cmd)
    {
        // 打印日志 - 开始.
        log.info("TcBaseDataQueryAndSaveCmdExe.execute() - start");
        // 1. 根据天财AppId和accessId进行鉴权.
        String accessToken = LoginToServerAction.getAccessToken(tcConfig);
        // 打印日志 - 鉴权成功.
        log.info("TcBaseDataQueryAndSaveCmdExe.execute() 鉴权成功, accessToken:{}", accessToken);

        if(cmd.getSyncType() == null || cmd.getSyncType().equals(TcSyncTypeEnums.ITEM)){
            tcItemQueryAndSaveCmdExe.syncData(cmd.getItemQueryCmd(),accessToken);
        }
        if(cmd.getSyncType() == null || cmd.getSyncType().equals(TcSyncTypeEnums.ITEM_CLASS)){
            tcItemCategoryQueryAndSaveCmdExe.syncData(accessToken);
        }
        if(cmd.getSyncType() == null || cmd.getSyncType().equals(TcSyncTypeEnums.METHODS_CLASS)){
            tcItemMethodClassesQueryAndSaveCmdExe.syncData(accessToken);
        }
        if(cmd.getSyncType() == null || cmd.getSyncType().equals(TcSyncTypeEnums.METHODS)){
            tcItemMethodsQueryAndSaveCmdExe.syncData(accessToken);
        }
        if(cmd.getSyncType() == null || cmd.getSyncType().equals(TcSyncTypeEnums.PAYWAY)){
            tcPaywayDetailQueryAndSaveCmdExe.syncData(accessToken);
        }
        if(cmd.getSyncType() == null || cmd.getSyncType().equals(TcSyncTypeEnums.PAYWAY_CLASS)){
            tcPayTypeQueryAndSaveCmdExe.syncData(accessToken);
        }
        if(cmd.getSyncType() == null || cmd.getSyncType().equals(TcSyncTypeEnums.RECIPE_CARD)){
            tcRecipeCardQueryAndSaveCmdExe.syncData(cmd.getTcRecipeCardQueryCmd());
        }
        if(cmd.getSyncType() == null || cmd.getSyncType().equals(TcSyncTypeEnums.SCM_PX)){
            tcScmPxQueryAndSaveCmdExe.syncData();
        }
        if(cmd.getSyncType() == null || cmd.getSyncType().equals(TcSyncTypeEnums.SCM_GYS)){
            tcScmGysQueryAndSaveCmdExe.syncData();
        }
        // 打印日志 - 结束.
        log.info("TcBaseDataQueryAndSaveCmdExe.execute() - end");
        return ApiResult.success();
    }
}
