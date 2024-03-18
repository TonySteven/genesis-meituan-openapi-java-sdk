package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api;

import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcItemMultiBarcodeEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.database.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 品项多条码表 服务类
 * </p>
 *
 * @author LiPei
 * @since 2024-03-16
 */
public interface ITcItemMultiBarcodeDao extends IBaseService<TcItemMultiBarcodeEntity> {

    /**
     * 根据集团编码查询和结算方式id门店限制列表信息
     * @param centerId 集团ID
     * @param itemIds 品项id
     * @return 结果
     */
    List<TcItemMultiBarcodeEntity> getListByCenterId(String centerId, List<String> itemIds);

    /**
     * 根据集团编码查询和结算方式id门店限制列表信息
     * @param centerId 集团ID
     * @param itemIds 品项id
     * @return 结果
     */
    Map<String, List<TcItemMultiBarcodeEntity>> getGroupByCenterId(String centerId, List<String> itemIds);

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     * @param removeList 批量删除数据
     */
    void operateBatch(String centerId,List<TcItemMultiBarcodeEntity> saveList, List<TcItemMultiBarcodeEntity> updateList, Map<String,List<String>> removeList);

    /**
     * 批量修改 - 根据实体类内容修改
     * @param updateList 批量修改数据
     */
    void updateBatchByEntity(List<TcItemMultiBarcodeEntity> updateList);

    /**
     * 批量删除 - 根据结算方式和门店id删除
     * @param removeList 批量删除数据
     */
    void removeBatch(String centerId,Map<String,List<String>> removeList);

}
