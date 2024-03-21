package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcRecipeCardDetailsDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcRecipeCardDetailsEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.mapper.TcRecipeCardDetailsMapper;
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
 * 成本卡菜品明细 服务实现类
 * </p>
 *
 * @author LiPei
 * @since 2024-03-20
 */
@Service
public class TcRecipeCardDetailsDaoImpl extends BaseServiceImpl<TcRecipeCardDetailsMapper, TcRecipeCardDetailsEntity> implements ITcRecipeCardDetailsDao {


    /**
     * 根据集团编码和品项id查询成本卡信息
     * @param centerId 集团ID
     * @return 结果
     */
    @Override
    public List<TcRecipeCardDetailsEntity> getListByCenterId(String centerId, List<String> itemIds){
        LambdaQueryWrapper<TcRecipeCardDetailsEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TcRecipeCardDetailsEntity::getCenterId,centerId);
        queryWrapper.in(ObjectUtils.isNotEmpty(itemIds),TcRecipeCardDetailsEntity::getDishJkId,itemIds);
        return this.list(queryWrapper);
    }

    /**
     * 根据集团编码和品项id查询成本卡信息
     *
     * @param centerId 集团ID
     * @param itemIds
     * @return 结果
     */
    @Override
    public Map<String, TcRecipeCardDetailsEntity> getMapByCenterId(String centerId, List<String> itemIds) {
        List<TcRecipeCardDetailsEntity> list = this.getListByCenterId(centerId,itemIds);
        if(ObjectUtils.isEmpty(list)){
            return new HashMap<>();
        }
        return list.stream().collect(Collectors.toMap(TcRecipeCardDetailsEntity::getId, item -> item));
    }

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void operateBatch(String centerId, List<TcRecipeCardDetailsEntity> saveList, List<TcRecipeCardDetailsEntity> updateList, List<String> removeList){
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
    public void updateBatchByEntity(List<TcRecipeCardDetailsEntity> updateList){
        this.updateBatchNoneEntity(updateList, item -> {
            LambdaUpdateWrapper<TcRecipeCardDetailsEntity> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(TcRecipeCardDetailsEntity::getCenterId, item.getCenterId())
                .eq(TcRecipeCardDetailsEntity::getId, item.getId());
            return wrapper;
        });
    }

    /**
     * 批量删除
     * @param idList 批量删除数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeBatch(String centerId, List<String> idList){
        LambdaQueryWrapper<TcRecipeCardDetailsEntity> updateWrapper = new LambdaQueryWrapper<>();
        updateWrapper.eq(TcRecipeCardDetailsEntity::getCenterId,centerId);
        updateWrapper.in(ObjectUtils.isNotEmpty(idList),TcRecipeCardDetailsEntity::getId,idList);
        this.remove(updateWrapper);

    }

}
