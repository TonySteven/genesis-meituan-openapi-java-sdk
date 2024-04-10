package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.IScmPxDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.ScmPxEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.mapper.ScmPxMapper;
import com.genesis.org.cn.genesismeituanopenapijavasdk.database.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LiPei
 * @since 2024-04-07
 */
@Service
public class ScmPxDaoImpl extends BaseServiceImpl<ScmPxMapper, ScmPxEntity> implements IScmPxDao {
    /**
     * 根据集团编码查询类别信息
     * @return 结果
     */
    @Override
    public List<ScmPxEntity> getList() {
        LambdaQueryWrapper<ScmPxEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(ScmPxEntity::getItemId);
        return this.listAll(wrapper);
    }

    /**
     * 根据集团编码查询类别信息
     * @return 结果
     */
    @Override
    public Map<String, ScmPxEntity> getMap(){
        List<ScmPxEntity> list = this.getList();
        if(ObjectUtils.isEmpty(list)){
            return new HashMap<>();
        }
        return list.stream().collect(Collectors.toMap(ScmPxEntity::getItemId, item -> item));
    }

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void operateBatch(List<ScmPxEntity> saveList, List<ScmPxEntity> updateList){
        if(ObjectUtils.isNotEmpty(saveList)){
            this.saveBatch(saveList);
        }

        if(ObjectUtils.isNotEmpty(updateList)){
            this.updateBatchByEntity(updateList);
        }
    }

    /**
     * 批量修改 - 根据实体类内容修改
     * @param updateList 批量修改数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchByEntity(List<ScmPxEntity> updateList){
        this.updateBatchNoneEntity(updateList, item -> {
            LambdaUpdateWrapper<ScmPxEntity> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(ScmPxEntity::getItemId, item.getItemId());
            return wrapper;
        });
    }

}
