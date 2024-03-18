package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api;

import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcItemMethodEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.database.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 品项通用做法表 服务类
 * </p>
 *
 * @author LiPei
 * @since 2024-03-16
 */
public interface ITcItemMethodDao extends IBaseService<TcItemMethodEntity> {

    /**
     * 根据集团编码查询和结算方式id门店限制列表信息
     * @param centerId 集团ID
     * @param itemIds 品项id
     * @return 结果
     */
    List<TcItemMethodEntity> getListByCenterId(String centerId, List<String> itemIds);

    /**
     * 根据集团编码查询和结算方式id门店限制列表信息
     * @param centerId 集团ID
     * @param itemIds 品项id
     * @return 结果
     */
    Map<String, List<TcItemMethodEntity>> getGroupByCenterId(String centerId, List<String> itemIds);

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     * @param removeList 批量删除数据
     */
    void operateBatch(String centerId,List<TcItemMethodEntity> saveList, List<TcItemMethodEntity> updateList, Map<String,List<String>> removeList);

    /**
     * 批量修改 - 根据实体类内容修改
     * @param updateList 批量修改数据
     */
    void updateBatchByEntity(List<TcItemMethodEntity> updateList);

    /**
     * 批量删除 - 根据结算方式和门店id删除
     * @param removeList 批量删除数据
     */
    void removeBatch(String centerId,Map<String,List<String>> removeList);

}
