package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcRecipeCardDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcRecipeCardEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.mapper.TcRecipeCardMapper;
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
 * 菜品成本卡信息 服务实现类
 * </p>
 *
 * @author LiPei
 * @since 2024-03-20
 */
@Service
public class TcRecipeCardDaoImpl extends BaseServiceImpl<TcRecipeCardMapper, TcRecipeCardEntity> implements ITcRecipeCardDao {
    /**
     * 根据集团编码查询类别信息
     *
     * @param centerId 集团ID
     * @return 结果
     */
    @Override
    public List<TcRecipeCardEntity> getListByCenterId(String centerId) {
        LambdaQueryWrapper<TcRecipeCardEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TcRecipeCardEntity::getCenterId, centerId)
            .orderByAsc(TcRecipeCardEntity::getId);
        return this.listAll(wrapper);
    }

    /**
     * 根据集团编码查询类别信息
     * @param centerId 集团ID
     * @return 结果
     */
    @Override
    public Map<String, TcRecipeCardEntity> getMapByCenterId(String centerId){
        List<TcRecipeCardEntity> list = this.getListByCenterId(centerId);
        if(ObjectUtils.isEmpty(list)){
            return new HashMap<>();
        }
        return list.stream().collect(Collectors.toMap(item -> item.getDishJkId() + item.getDishUnitJkId(), item -> item));
    }

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void operateBatch(String centerId,List<TcRecipeCardEntity> saveList, List<TcRecipeCardEntity> updateList,List<String> removeList){
        if(ObjectUtils.isNotEmpty(saveList)){
            this.saveBatch(saveList);
        }

        if(ObjectUtils.isNotEmpty(updateList)){
            this.updateBatchByEntity(updateList);
        }

        if(ObjectUtils.isNotEmpty(removeList)){
            this.removeBatch(centerId,removeList);
        }
    }

    /**
     * 批量修改 - 根据实体类内容修改
     * @param updateList 批量修改数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchByEntity(List<TcRecipeCardEntity> updateList){
        this.updateBatchNoneEntity(updateList, item -> {
            LambdaUpdateWrapper<TcRecipeCardEntity> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(TcRecipeCardEntity::getCenterId, item.getCenterId())
                .eq(TcRecipeCardEntity::getDishJkId, item.getDishJkId())
                .eq(TcRecipeCardEntity::getDishUnitJkId, item.getDishUnitJkId());
            return wrapper;
        });
    }

    /**
     * 批量删除 - 根据品项id和规格id删除
     * @param removeList 批量删除数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeBatch(String centerId,List<String> removeList){
        LambdaUpdateWrapper<TcRecipeCardEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(TcRecipeCardEntity::getCenterId, centerId)
            .in(TcRecipeCardEntity::getId,removeList);
        this.remove(wrapper);
    }

}
