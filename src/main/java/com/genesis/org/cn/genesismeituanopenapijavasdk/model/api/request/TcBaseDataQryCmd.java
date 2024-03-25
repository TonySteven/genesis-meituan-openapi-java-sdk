package com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request;

import com.genesis.org.cn.genesismeituanopenapijavasdk.enums.TcSyncTypeEnums;
import lombok.Data;

/**
 * 天才基础信息同步请求
 */
@Data
public class TcBaseDataQryCmd {

    /**
     * 同步类型
     */
    private TcSyncTypeEnums syncType;

    /**
     * 天才品项同步
     */
    private TcItemQueryCmd itemQueryCmd;

    /**
     * 天才成本卡同步入参
     */
    private TcRecipeCardQueryCmd tcRecipeCardQueryCmd;

    public TcItemQueryCmd getItemQueryCmd() {
        if (itemQueryCmd == null) {
            itemQueryCmd = new TcItemQueryCmd();
        }
        return itemQueryCmd;
    }

    public TcRecipeCardQueryCmd getTcRecipeCardQueryCmd() {
        if (tcRecipeCardQueryCmd == null) {
            tcRecipeCardQueryCmd = new TcRecipeCardQueryCmd();
        }
        return tcRecipeCardQueryCmd;
    }
}
