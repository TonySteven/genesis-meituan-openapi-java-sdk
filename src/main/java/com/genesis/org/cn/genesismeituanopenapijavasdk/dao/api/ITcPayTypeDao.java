package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api;

import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcPayTypeEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.database.service.IBaseService;

import java.util.List;
import java.util.Map;

public interface ITcPayTypeDao extends IBaseService<TcPayTypeEntity> {

    /**
     * 根据集团编码查询类别信息
     * @param centerId 集团ID
     * @return 结果
     */
    List<TcPayTypeEntity> getListByCenterId(String centerId);

    /**
     * 根据集团编码查询类别信息
     * @param centerId 集团ID
     * @return 结果
     */
    Map<String, TcPayTypeEntity> getMapByCenterId(String centerId);

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     * @return 结果
     */
    void operateBatch(List<TcPayTypeEntity> saveList, List<TcPayTypeEntity> updateList);

    /**
     * 批量修改 - 根据实体类内容修改
     * @param updateList 批量修改数据
     */
    void updateBatchByEntity(List<TcPayTypeEntity> updateList);
}
