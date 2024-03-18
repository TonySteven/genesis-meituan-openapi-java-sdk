package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcItemMultiBarcodeDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcItemMultiBarcodeEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.mapper.TcItemMultiBarcodeMapper;
import com.genesis.org.cn.genesismeituanopenapijavasdk.database.service.impl.BaseServiceImpl;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 品项多条码表 服务实现类
 * </p>
 *
 * @author LiPei
 * @since 2024-03-16
 */
@Service
public class TcItemMultiBarcodeDaoImpl extends BaseServiceImpl<TcItemMultiBarcodeMapper, TcItemMultiBarcodeEntity> implements ITcItemMultiBarcodeDao {
    /**
     * 根据集团编码查询和结算方式id门店限制列表信息
     *
     * @param centerId   集团编码
     * @param itemIds 结算方式id
     * @return 结果
     */
    @Override
    public List<TcItemMultiBarcodeEntity> getListByCenterId(String centerId, List<String> itemIds) {
        LambdaQueryWrapper<TcItemMultiBarcodeEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TcItemMultiBarcodeEntity::getCenterId, centerId)
            .in(ObjectUtils.isNotEmpty(itemIds),TcItemMultiBarcodeEntity::getItemId, itemIds)
            .orderByAsc(TcItemMultiBarcodeEntity::getId);
        return this.listAll(wrapper);
    }

    /**
     * 根据集团编码查询和结算方式id门店限制列表信息
     *
     * @param centerId   集团编码
     * @param itemIds 结算方式id
     * @return 结果
     */
    @Override
    public Map<String, List<TcItemMultiBarcodeEntity>> getGroupByCenterId(String centerId, List<String> itemIds) {
        List<TcItemMultiBarcodeEntity> list = this.getListByCenterId(centerId, itemIds);
        if(ObjectUtils.isEmpty(list)){
            return Maps.newHashMap();
        }
        return list.stream().collect(Collectors.groupingBy(TcItemMultiBarcodeEntity::getItemId));
    }

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     * @param removeList 批量删除数据
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void operateBatch(String centerId,List<TcItemMultiBarcodeEntity> saveList, List<TcItemMultiBarcodeEntity> updateList, Map<String,List<String>> removeList){
        if(ObjectUtils.isNotEmpty(removeList)){
            this.removeBatch(centerId,removeList);
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
    public void updateBatchByEntity(List<TcItemMultiBarcodeEntity> updateList){
        this.updateBatchNoneEntity(updateList, item -> {
            LambdaUpdateWrapper<TcItemMultiBarcodeEntity> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(TcItemMultiBarcodeEntity::getCenterId, item.getCenterId())
                .eq(TcItemMultiBarcodeEntity::getItemId, item.getItemId())
                .eq(TcItemMultiBarcodeEntity::getBarcode, item.getBarcode());
            return wrapper;
        });
    }

    /**
     * 批量删除 - 根据结算方式和门店id删除
     * @param removeList 批量删除数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeBatch(String centerId,Map<String,List<String>> removeList){
        for (Map.Entry<String,List<String>> entry : removeList.entrySet()){
            LambdaUpdateWrapper<TcItemMultiBarcodeEntity> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(TcItemMultiBarcodeEntity::getCenterId, centerId)
                .eq(TcItemMultiBarcodeEntity::getItemId, entry.getKey())
                .in(ObjectUtils.isNotEmpty(entry.getValue()),TcItemMultiBarcodeEntity::getBarcode, entry.getValue());
            this.remove(wrapper);
        }
    }

}
