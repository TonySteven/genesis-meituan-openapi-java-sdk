package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcItemDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcItemEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.mapper.TcItemMapper;
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
 * 品项档案信息 服务实现类
 * </p>
 *
 * @author LiPei
 * @since 2024-03-16
 */
@Service
public class TcItemDaoImpl extends BaseServiceImpl<TcItemMapper, TcItemEntity> implements ITcItemDao {

    /**
     * 根据集团编码和品项id查询类别信息
     * @param centerId 集团id
     * @param itemIds 品项id
     * @return 结果
     */
    @Override
    public List<TcItemEntity> getListByCenterId(String centerId, List<String> itemIds) {
        LambdaQueryWrapper<TcItemEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TcItemEntity::getCenterId, centerId)
            .in(ObjectUtils.isNotEmpty(itemIds),TcItemEntity::getItemId, itemIds)
            .orderByAsc(TcItemEntity::getItemId);
        return this.listAll(wrapper);
    }

    /**
     * 根据集团编码和品项id查询类别信息
     * @param centerId 集团id
     * @param itemIds 品项id
     * @return 结果
     */
    @Override
    public Map<String, TcItemEntity> getMapByCenterId(String centerId, List<String> itemIds){
        List<TcItemEntity> list = this.getListByCenterId(centerId,itemIds);
        if(ObjectUtils.isEmpty(list)){
            return new HashMap<>();
        }
        return list.stream().collect(Collectors.toMap(TcItemEntity::getItemId, item -> item));
    }

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void operateBatch(List<TcItemEntity> saveList, List<TcItemEntity> updateList){
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
    public void updateBatchByEntity(List<TcItemEntity> updateList){
        this.updateBatchNoneEntity(updateList, item -> {
            LambdaUpdateWrapper<TcItemEntity> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(TcItemEntity::getCenterId, item.getCenterId())
                .eq(TcItemEntity::getItemId, item.getItemId());
            return wrapper;
        });
    }

}
