package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api;


import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcRecipeCardEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.database.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜品成本卡信息 服务类
 * </p>
 *
 * @author LiPei
 * @since 2024-03-20
 */
public interface ITcRecipeCardDao extends IBaseService<TcRecipeCardEntity> {

    /**
     * 根据集团编码查询类别信息
     * @param centerId 集团ID
     * @return 结果
     */
    List<TcRecipeCardEntity> getListByCenterId(String centerId);

    /**
     * 根据集团编码查询类别信息
     * @param centerId 集团ID
     * @return 结果
     */
    Map<String, TcRecipeCardEntity> getMapByCenterId(String centerId);

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     * @return 结果
     */
    void operateBatch(String centerId,List<TcRecipeCardEntity> saveList, List<TcRecipeCardEntity> updateList,List<String> removeList);

    /**
     * 批量修改 - 根据实体类内容修改
     * @param updateList 批量修改数据
     */
    void updateBatchByEntity(List<TcRecipeCardEntity> updateList);

    /**
     * 批量删除 - 根据品项id和规格id删除
     * @param removeList 批量删除数据
     */
    void removeBatch(String centerId,List<String> removeList);

}
