package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api;

import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.ScmDjmxEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.database.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 门店(中心)库存流水集市(过滤了配送中心的单据) 服务类
 * </p>
 *
 * @author LiPei
 * @since 2024-04-07
 */
public interface IScmDjmxDao extends IBaseService<ScmDjmxEntity> {

    /**
     * 根据集团编码查询类别信息
     * @return 结果
     */
    List<ScmDjmxEntity> getList(List<String> zhujianList);

    /**
     * 根据集团编码查询类别信息
     * @return 结果
     */
    Map<String, ScmDjmxEntity> getMap(List<String> zhujianList);

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     * @param billIdList 批量删除数据
     */
    void operateBatch(List<ScmDjmxEntity> saveList, List<ScmDjmxEntity> updateList, List<String> billIdList);

    /**
     * 批量修改 - 根据实体类内容修改
     * @param updateList 批量修改数据
     */
    void updateBatchByEntity(List<ScmDjmxEntity> updateList);

    /**
     * 批量删除 - 根据主键
     * @param billIdList 主键集合
     */
    void removeBatch(List<String> billIdList);

}
