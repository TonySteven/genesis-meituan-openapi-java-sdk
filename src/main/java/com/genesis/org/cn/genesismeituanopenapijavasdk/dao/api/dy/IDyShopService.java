package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.dy;

import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.dy.DyShopEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.database.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 抖音门店信息 服务类
 * </p>
 *
 * @author LiPei
 * @since 2024-02-03
 */
public interface IDyShopService extends IBaseService<DyShopEntity> {

    /**
     * 根据生活服务商家账户 ID查询门店列表
     * @param rootAccountId 生活服务商家账户 ID
     * @param poiIds 抖音门店 ID
     * @return 结果
     */
    List<DyShopEntity> getListByAccountIdAndPoiIds(String rootAccountId,List<String> poiIds);

    /**
     * 根据生活服务商家账户 ID查询门店列表
     * @param rootAccountId 生活服务商家账户 ID
     * @param poiIds 抖音门店 ID
     * @return 结果
     */
    Map<String,DyShopEntity> getMapByAccountIdAndPoiIds(String rootAccountId, List<String> poiIds);

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     * @return 结果
     */
    void operateBatch(List<DyShopEntity> saveList, List<DyShopEntity> updateList);

    /**
     * 批量修改 - 根据实体类内容修改
     * @param updateList 批量修改数据
     */
    void updateBatchByEntity(List<DyShopEntity> updateList);
}
