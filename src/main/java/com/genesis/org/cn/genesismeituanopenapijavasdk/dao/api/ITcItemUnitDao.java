package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api;

import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcItemUnitEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.database.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 品项单位信息 服务类
 * </p>
 *
 * @author LiPei
 * @since 2024-03-16
 */
public interface ITcItemUnitDao extends IBaseService<TcItemUnitEntity> {

    /**
     * 根据集团编码和单位id查询单位信息
     * @param centerId 集团id
     * @return 结果
     */
    List<TcItemUnitEntity> getListByCenterId(String centerId);

    /**
     * 根据集团编码和单位id查询单位信息
     * @param centerId 集团id
     * @return 结果
     */
    Map<String, TcItemUnitEntity> getMapByCenterId(String centerId);

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     */
    void operateBatch(List<TcItemUnitEntity> saveList, List<TcItemUnitEntity> updateList);

    /**
     * 批量修改 - 根据实体类内容修改
     * @param updateList 批量修改数据
     */
    void updateBatchByEntity(List<TcItemUnitEntity> updateList);

}
