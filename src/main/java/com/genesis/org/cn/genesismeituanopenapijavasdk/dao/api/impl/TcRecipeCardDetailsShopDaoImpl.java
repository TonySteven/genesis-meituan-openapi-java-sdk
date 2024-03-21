package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcRecipeCardDetailsShopDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcRecipeCardDetailsShopEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.mapper.TcRecipeCardDetailsShopMapper;
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
 * 成本卡适用门店 服务实现类
 * </p>
 *
 * @author LiPei
 * @since 2024-03-21
 */
@Service
public class TcRecipeCardDetailsShopDaoImpl extends BaseServiceImpl<TcRecipeCardDetailsShopMapper, TcRecipeCardDetailsShopEntity> implements ITcRecipeCardDetailsShopDao {


    /**
     * 根据集团编码查询和成本卡id门店限制列表信息
     *
     * @param centerId   集团编码
     * @param idList 成本卡id
     * @return 结果
     */
    @Override
    public List<TcRecipeCardDetailsShopEntity> getListByCenterId(String centerId, List<String> idList) {
        LambdaQueryWrapper<TcRecipeCardDetailsShopEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TcRecipeCardDetailsShopEntity::getCenterId, centerId)
            .in(ObjectUtils.isNotEmpty(idList),TcRecipeCardDetailsShopEntity::getCardDtId, idList)
            .orderByAsc(TcRecipeCardDetailsShopEntity::getId)
            .orderByAsc(TcRecipeCardDetailsShopEntity::getShopId);
        return this.listAll(wrapper);
    }

    /**
     * 根据集团编码查询和成本卡id门店限制列表信息
     *
     * @param centerId   集团编码
     * @param paywayIdList 成本卡id
     * @return 结果
     */
    @Override
    public Map<String, List<TcRecipeCardDetailsShopEntity>> getGroupByCenterId(String centerId, List<String> paywayIdList) {
        List<TcRecipeCardDetailsShopEntity> list = this.getListByCenterId(centerId, paywayIdList);
        if(ObjectUtils.isEmpty(list)){
            return Maps.newHashMap();
        }
        return list.stream().collect(Collectors.groupingBy(TcRecipeCardDetailsShopEntity::getCardDtId));
    }

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     * @param removeList 批量删除数据
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void operateBatch(String centerId,List<TcRecipeCardDetailsShopEntity> saveList, List<TcRecipeCardDetailsShopEntity> updateList, Map<String,List<String>> removeList){
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
    public void updateBatchByEntity(List<TcRecipeCardDetailsShopEntity> updateList){
        this.updateBatchNoneEntity(updateList, item -> {
            LambdaUpdateWrapper<TcRecipeCardDetailsShopEntity> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(TcRecipeCardDetailsShopEntity::getCenterId, item.getCenterId())
                .eq(TcRecipeCardDetailsShopEntity::getId, item.getId());
            return wrapper;
        });
    }
    /**
     * 批量删除 - 根据品项id和规格id删除
     * @param removeList 批量删除数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeBatch(String centerId, Map<String, List<String>> removeList){
        for (Map.Entry<String,List<String>> entry : removeList.entrySet()){
            LambdaUpdateWrapper<TcRecipeCardDetailsShopEntity> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(TcRecipeCardDetailsShopEntity::getCenterId, centerId)
                .eq(TcRecipeCardDetailsShopEntity::getCardDtId, entry.getKey())
                .in(ObjectUtils.isNotEmpty(entry.getValue()),TcRecipeCardDetailsShopEntity::getShopId, entry.getValue());
            this.remove(wrapper);
        }
    }
}
