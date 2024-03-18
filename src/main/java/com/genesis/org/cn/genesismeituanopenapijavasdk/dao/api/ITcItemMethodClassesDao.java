package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api;

import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcItemMethodClassesEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.database.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * tc_item_method_classes表服务接口
 * @date : 2024-03-12 16:20:35
 */
public interface ITcItemMethodClassesDao extends IBaseService<TcItemMethodClassesEntity>{

    /**
     * 根据集团编码查询类别信息
     * @param centerId 集团ID
     * @return 结果
     */
    List<TcItemMethodClassesEntity> getListByCenterId(String centerId);

    /**
     * 根据集团编码查询类别信息
     * @param centerId 集团ID
     * @return 结果
     */
    Map<String, TcItemMethodClassesEntity> getMapByCenterId(String centerId);

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     * @return 结果
     */
    void operateBatch(List<TcItemMethodClassesEntity> saveList, List<TcItemMethodClassesEntity> updateList);

    /**
     * 批量修改 - 根据实体类内容修改
     * @param updateList 批量修改数据
     */
    void updateBatchByEntity(List<TcItemMethodClassesEntity> updateList);
}
