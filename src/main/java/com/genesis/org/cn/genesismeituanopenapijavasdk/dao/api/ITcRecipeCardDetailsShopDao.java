package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api;

import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcRecipeCardDetailsShopEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.database.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 成本卡适用门店 服务类
 * </p>
 *
 * @author LiPei
 * @since 2024-03-21
 */
public interface ITcRecipeCardDetailsShopDao extends IBaseService<TcRecipeCardDetailsShopEntity> {

    /**
     * 根据集团编码查询和成本卡id门店限制列表信息
     * @param centerId 集团ID
     * @param paywayIdList 成本卡id
     * @return 结果
     */
    List<TcRecipeCardDetailsShopEntity> getListByCenterId(String centerId, List<String> paywayIdList);

    /**
     * 根据集团编码查询和成本卡id门店限制列表信息
     * @param centerId 集团ID
     * @param paywayIdList 成本卡id
     * @return 结果
     */
    Map<String, List<TcRecipeCardDetailsShopEntity>> getGroupByCenterId(String centerId,List<String> paywayIdList);

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     * @param removeList 批量删除数据
     */
    void operateBatch(String centerId,List<TcRecipeCardDetailsShopEntity> saveList, List<TcRecipeCardDetailsShopEntity> updateList, Map<String,List<String>> removeList);

    /**
     * 批量修改 - 根据实体类内容修改
     * @param updateList 批量修改数据
     */
    void updateBatchByEntity(List<TcRecipeCardDetailsShopEntity> updateList);

    /**
     * 批量删除 - 根据品项id和规格id删除
     * @param removeList 批量删除数据
     */
    void removeBatch(String centerId, Map<String, List<String>> removeList);

}
