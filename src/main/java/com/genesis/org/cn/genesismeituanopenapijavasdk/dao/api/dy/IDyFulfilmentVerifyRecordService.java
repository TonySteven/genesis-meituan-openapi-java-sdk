package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.dy;

import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.dy.DyFulfilmentVerifyRecordEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.database.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 抖音验券历史记录 服务类
 * </p>
 *
 * @author LiPei
 * @since 2024-02-03
 */
public interface IDyFulfilmentVerifyRecordService extends IBaseService<DyFulfilmentVerifyRecordEntity> {

    /**
     * 根据企业号商家总店id、门店id和核销记录id查询抖音验券历史记录
     * @param accountId 企业号商家总店id
     * @param poiId 门店id
     * @return 结果
     */
    List<DyFulfilmentVerifyRecordEntity> getListByVerifyIds(String accountId,String poiId,List<String> verifyIds);

    /**
     * 根据企业号商家总店id、门店id和核销记录id查询抖音验券历史记录
     * @param accountId 企业号商家总店id
     * @param poiId 门店id
     * @return 结果
     */
    Map<String,DyFulfilmentVerifyRecordEntity> getMapByVerifyIds(String accountId, String poiId, List<String> verifyIds);

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     * @return 结果
     */
    void operateBatch(List<DyFulfilmentVerifyRecordEntity> saveList, List<DyFulfilmentVerifyRecordEntity> updateList);

    /**
     * 批量修改 - 根据实体类内容修改
     * @param updateList 批量修改数据
     */
    void updateBatchByEntity(List<DyFulfilmentVerifyRecordEntity> updateList);

}
