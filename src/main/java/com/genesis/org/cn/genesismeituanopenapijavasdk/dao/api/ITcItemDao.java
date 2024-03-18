package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api;

import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcItemEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.database.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 品项档案信息 服务类
 * </p>
 *
 * @author LiPei
 * @since 2024-03-16
 */
public interface ITcItemDao extends IBaseService<TcItemEntity> {

    /**
     * 根据集团编码和品项id查询类别信息
     * @param centerId 集团id
     * @param itemIds 品项id
     * @return 结果
     */
    List<TcItemEntity> getListByCenterId(String centerId,List<String> itemIds);

    /**
     * 根据集团编码和品项id查询类别信息
     * @param centerId 集团id
     * @param itemIds 品项id
     * @return 结果
     */
    Map<String, TcItemEntity> getMapByCenterId(String centerId,List<String> itemIds);

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     * @return 结果
     */
    void operateBatch(List<TcItemEntity> saveList, List<TcItemEntity> updateList);

    /**
     * 批量修改 - 根据实体类内容修改
     * @param updateList 批量修改数据
     */
    void updateBatchByEntity(List<TcItemEntity> updateList);

}
