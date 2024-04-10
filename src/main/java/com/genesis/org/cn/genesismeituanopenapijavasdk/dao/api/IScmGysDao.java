package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api;

import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.ScmGysEntity;
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
public interface IScmGysDao extends IBaseService<ScmGysEntity> {

    /**
     * 根据集团编码查询类别信息
     * @return 结果
     */
    List<ScmGysEntity> getList();

    /**
     * 根据集团编码查询类别信息
     * @return 结果
     */
    Map<String, ScmGysEntity> getMap();

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     * @return 结果
     */
    void operateBatch(List<ScmGysEntity> saveList, List<ScmGysEntity> updateList);

    /**
     * 批量修改 - 根据实体类内容修改
     * @param updateList 批量修改数据
     */
    void updateBatchByEntity(List<ScmGysEntity> updateList);

}
