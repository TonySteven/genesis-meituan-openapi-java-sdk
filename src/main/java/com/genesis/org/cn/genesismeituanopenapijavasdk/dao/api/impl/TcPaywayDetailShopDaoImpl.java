package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcPaywayDetailShopDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcPaywayDetailShopEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.mapper.TcPaywayDetailShopMapper;
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
 * 品项做法类别信息 服务实现类
 * </p>
 *
 * @author LiPei
 * @since 2024-03-10
 */
@Service
public class TcPaywayDetailShopDaoImpl extends BaseServiceImpl<TcPaywayDetailShopMapper, TcPaywayDetailShopEntity> implements ITcPaywayDetailShopDao {


    /**
     * 根据集团编码查询和结算方式id门店限制列表信息
     *
     * @param centerId   集团编码
     * @param paywayIdList 结算方式id
     * @return 结果
     */
    @Override
    public List<TcPaywayDetailShopEntity> getListByCenterId(String centerId, List<String> paywayIdList) {
        LambdaQueryWrapper<TcPaywayDetailShopEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TcPaywayDetailShopEntity::getCenterId, centerId)
            .in(ObjectUtils.isNotEmpty(paywayIdList),TcPaywayDetailShopEntity::getPaywayId, paywayIdList)
            .orderByAsc(TcPaywayDetailShopEntity::getPaywayId)
            .orderByAsc(TcPaywayDetailShopEntity::getShopId);
        return this.listAll(wrapper);
    }

    /**
     * 根据集团编码查询和结算方式id门店限制列表信息
     *
     * @param centerId   集团编码
     * @param paywayIdList 结算方式id
     * @return 结果
     */
    @Override
    public Map<String, List<TcPaywayDetailShopEntity>> getGroupByCenterId(String centerId, List<String> paywayIdList) {
        List<TcPaywayDetailShopEntity> list = this.getListByCenterId(centerId, paywayIdList);
        if(ObjectUtils.isEmpty(list)){
            return Maps.newHashMap();
        }
        return list.stream().collect(Collectors.groupingBy(TcPaywayDetailShopEntity::getPaywayId));
    }

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     * @param removeList 批量删除数据
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void operateBatch(String centerId,List<TcPaywayDetailShopEntity> saveList, List<TcPaywayDetailShopEntity> updateList, Map<String,List<String>> removeList){
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
    public void updateBatchByEntity(List<TcPaywayDetailShopEntity> updateList){
        this.updateBatchNoneEntity(updateList, item -> {
            LambdaUpdateWrapper<TcPaywayDetailShopEntity> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(TcPaywayDetailShopEntity::getCenterId, item.getCenterId())
                .eq(TcPaywayDetailShopEntity::getPaywayId, item.getPaywayId())
                .eq(TcPaywayDetailShopEntity::getShopId, item.getShopId());
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
            LambdaUpdateWrapper<TcPaywayDetailShopEntity> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(TcPaywayDetailShopEntity::getCenterId, centerId)
                .eq(TcPaywayDetailShopEntity::getPaywayId, entry.getKey())
                .in(ObjectUtils.isNotEmpty(entry.getValue()),TcPaywayDetailShopEntity::getShopId, entry.getValue());
            this.remove(wrapper);
        }
    }
}
