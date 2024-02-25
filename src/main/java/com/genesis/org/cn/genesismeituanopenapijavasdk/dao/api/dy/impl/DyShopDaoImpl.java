package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.dy.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.dy.IDyShopDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.dy.DyShopEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.mapper.dy.DyShopMapper;
import com.genesis.org.cn.genesismeituanopenapijavasdk.database.service.impl.BaseServiceImpl;
import com.genesis.org.cn.genesismeituanopenapijavasdk.enums.DeletedEnums;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 抖音门店信息 服务实现类
 * </p>
 *
 * @author LiPei
 * @since 2024-02-03
 */
@Service
public class DyShopDaoImpl extends BaseServiceImpl<DyShopMapper, DyShopEntity> implements IDyShopDao {

    /**
     * 根据生活服务商家账户 ID查询门店列表
     * @param rootAccountId 生活服务商家账户 ID
     * @param poiIds 抖音门店 ID
     * @return 结果
     */
    @Override
    public List<DyShopEntity> getListByAccountIdAndPoiIds(String rootAccountId,List<String> poiIds){
        LambdaQueryWrapper<DyShopEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DyShopEntity::getRootAccountId, rootAccountId)
            .in(ObjectUtils.isNotEmpty(poiIds), DyShopEntity::getPoiId, poiIds)
            .eq(DyShopEntity::getIsDeleted, DeletedEnums.NONE_DELETE.getCode())
            .orderByAsc(DyShopEntity::getId);
        return this.listAll(wrapper);
    }

    /**
     * 根据生活服务商家账户 ID查询门店列表
     * @param rootAccountId 生活服务商家账户 ID
     * @param poiIds 抖音门店 ID
     * @return 结果
     */

    @Override
    public Map<String,DyShopEntity> getMapByAccountIdAndPoiIds(String rootAccountId, List<String> poiIds){
        List<DyShopEntity> list = this.getListByAccountIdAndPoiIds(rootAccountId,poiIds);
        if(ObjectUtils.isEmpty(list)){
            return new HashMap<>();
        }
        return list.stream().collect(Collectors.toMap(DyShopEntity::getPoiId, Function.identity()));
    }

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void operateBatch(List<DyShopEntity> saveList, List<DyShopEntity> updateList){
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
    public void updateBatchByEntity(List<DyShopEntity> updateList){
        this.updateBatchNoneEntity(updateList, item -> {
            LambdaUpdateWrapper<DyShopEntity> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(DyShopEntity::getRootAccountId, item.getRootAccountId())
                .eq(DyShopEntity::getPoiId, item.getPoiId())
                .eq(DyShopEntity::getIsDeleted, DeletedEnums.NONE_DELETE.getCode());
            return wrapper;
        });
    }
}
