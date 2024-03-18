package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api;

import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcPaywayDetailShopEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.database.service.IBaseService;

import java.util.List;
import java.util.Map;


public interface ITcPaywayDetailShopDao extends IBaseService<TcPaywayDetailShopEntity> {

    /**
     * 根据集团编码查询和结算方式id门店限制列表信息
     * @param centerId 集团ID
     * @param paywayIdList 结算方式id
     * @return 结果
     */
    List<TcPaywayDetailShopEntity> getListByCenterId(String centerId,List<String> paywayIdList);

    /**
     * 根据集团编码查询和结算方式id门店限制列表信息
     * @param centerId 集团ID
     * @param paywayIdList 结算方式id
     * @return 结果
     */
    Map<String, List<TcPaywayDetailShopEntity>> getGroupByCenterId(String centerId,List<String> paywayIdList);

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     * @param removeList 批量删除数据
     */
    void operateBatch(String centerId,List<TcPaywayDetailShopEntity> saveList, List<TcPaywayDetailShopEntity> updateList, Map<String,List<String>> removeList);

    /**
     * 批量修改 - 根据实体类内容修改
     * @param updateList 批量修改数据
     */
    void updateBatchByEntity(List<TcPaywayDetailShopEntity> updateList);

    /**
     * 批量删除 - 根据结算方式和门店id删除
     * @param removeList 批量删除数据
     */
    public void removeBatch(String centerId,Map<String,List<String>> removeList);
}
