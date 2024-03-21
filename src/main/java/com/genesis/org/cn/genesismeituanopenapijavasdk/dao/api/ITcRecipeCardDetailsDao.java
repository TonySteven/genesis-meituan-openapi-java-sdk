package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api;


import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcRecipeCardDetailsEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.database.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 成本卡菜品明细 服务类
 * </p>
 *
 * @author LiPei
 * @since 2024-03-20
 */
public interface ITcRecipeCardDetailsDao extends IBaseService<TcRecipeCardDetailsEntity> {

    /**
     * 根据集团编码和品项id查询成本卡信息
     * @param centerId 集团ID
     * @return 结果
     */
    List<TcRecipeCardDetailsEntity> getListByCenterId(String centerId, List<String> itemIds);

    /**
     * 根据集团编码和品项id查询成本卡信息
     * @param centerId 集团ID
     * @return 结果
     */
    Map<String, TcRecipeCardDetailsEntity> getMapByCenterId(String centerId, List<String> itemIds);

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     * @return 结果
     */
    void operateBatch(String centerId, List<TcRecipeCardDetailsEntity> saveList, List<TcRecipeCardDetailsEntity> updateList, List<String> removeList);

    /**
     * 批量修改 - 根据实体类内容修改
     * @param updateList 批量修改数据
     */
    void updateBatchByEntity(List<TcRecipeCardDetailsEntity> updateList);

    /**
     * 批量删除
     * @param idList 批量删除数据
     */
    void removeBatch(String centerId, List<String> idList);

}
