package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api;

import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.ScmPxEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.database.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiPei
 * @since 2024-04-07
 */
public interface IScmPxDao extends IBaseService<ScmPxEntity> {

    /**
     * 根据集团编码查询类别信息
     * @return 结果
     */
    List<ScmPxEntity> getList();

    /**
     * 根据集团编码查询类别信息
     * @return 结果
     */
    Map<String, ScmPxEntity> getMap();

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     * @return 结果
     */
    void operateBatch(List<ScmPxEntity> saveList, List<ScmPxEntity> updateList);

    /**
     * 批量修改 - 根据实体类内容修改
     * @param updateList 批量修改数据
     */
    void updateBatchByEntity(List<ScmPxEntity> updateList);

}
