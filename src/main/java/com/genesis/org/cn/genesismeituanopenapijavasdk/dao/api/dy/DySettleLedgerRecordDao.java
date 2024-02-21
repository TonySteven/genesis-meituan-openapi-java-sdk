package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.dy;

import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.dy.DySettleLedgerRecordEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.database.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * dy_settle_ledger_record表服务接口
 */
public interface DySettleLedgerRecordDao extends IBaseService<DySettleLedgerRecordEntity> {

    /**
     * 根据企业号商家总店id、门店id和分账记录id查询抖音分账历史记录
     * @param accountId 企业号商家总店id
     * @param poiId 门店id
     * @param ledgerIds 分账记录id
     * @return 结果
     */
    List<DySettleLedgerRecordEntity> getListByLedgerIds(String accountId, String poiId, List<String> ledgerIds);

    /**
     * 根据企业号商家总店id、门店id和分账记录id查询抖音分账历史记录
     * @param accountId 企业号商家总店id
     * @param poiId 门店id
     * @param ledgerIds 分账记录id
     * @return 结果
     */
    Map<String,DySettleLedgerRecordEntity> getMapByLedgerIds(String accountId, String poiId, List<String> ledgerIds);

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     */
    void operateBatch(List<DySettleLedgerRecordEntity> saveList, List<DySettleLedgerRecordEntity> updateList);

    /**
     * 批量修改 - 根据实体类内容修改
     * @param updateList 批量修改数据
     */
    void updateBatchByEntity(List<DySettleLedgerRecordEntity> updateList);
}
