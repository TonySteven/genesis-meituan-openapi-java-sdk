package com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy.goodlife.settle_ledger;

import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy.goodlife.BaseAllCmd;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 抖音分账记录历史查询请求模型
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DySettleLedgerRecordAllSyncCmd extends BaseAllCmd {

    /**
     * 券id列表
     */
    private List<String> certificateIds;
}
