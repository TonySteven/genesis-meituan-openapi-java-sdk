package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.IScmDjmxDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.ScmDjmxEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.mapper.ScmDjmxMapper;
import com.genesis.org.cn.genesismeituanopenapijavasdk.database.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 门店(中心)库存流水集市(过滤了配送中心的单据) 服务实现类
 * </p>
 *
 * @author LiPei
 * @since 2024-04-07
 */
@Service
public class ScmDjmxDaoImpl extends BaseServiceImpl<ScmDjmxMapper, ScmDjmxEntity> implements IScmDjmxDao {
    /**
     * 根据集团编码查询类别信息
     * @return 结果
     */
    @Override
    public List<ScmDjmxEntity> getList(List<String> zhujianList) {
        List<ScmDjmxEntity> list = new ArrayList<>();
        // 根据主键分批次查询
        List<List<String>> groupList = ListUtil.partition(zhujianList,500);
        for (List<String> idList:groupList){
            LambdaQueryWrapper<ScmDjmxEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(ScmDjmxEntity::getZhujian, idList);
            wrapper.orderByAsc(ScmDjmxEntity::getZhujian);
            list.addAll(this.list(wrapper));
        }
        return list;
    }

    /**
     * 根据集团编码查询类别信息
     * @return 结果
     */
    @Override
    public Map<String, ScmDjmxEntity> getMap(List<String> zhujianList){
        List<ScmDjmxEntity> list = this.getList(zhujianList);
        if(ObjectUtils.isEmpty(list)){
            return new HashMap<>();
        }
        return list.stream().collect(Collectors.toMap(ScmDjmxEntity::getZhujian, item -> item));
    }

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void operateBatch(List<ScmDjmxEntity> saveList, List<ScmDjmxEntity> updateList, List<String> billIdList){
        if(ObjectUtils.isNotEmpty(billIdList)){
            this.removeBatch(billIdList);
        }
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
    public void updateBatchByEntity(List<ScmDjmxEntity> updateList){
        this.updateBatchNoneEntity(updateList, item -> {
            LambdaUpdateWrapper<ScmDjmxEntity> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(ScmDjmxEntity::getZhujian, item.getZhujian());
            return wrapper;
        });
    }

    /**
     * 批量删除 - 根据主键
     * @param billIdList 主单据ID列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeBatch(List<String> billIdList){
        List<List<String>> ids = ListUtil.partition(billIdList,1000);
        ids.forEach(id -> {
            LambdaQueryWrapper<ScmDjmxEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(ScmDjmxEntity::getStoreBillID, id);
            this.remove(wrapper);
        });
    }

}
