package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcItemMethodClassesDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcItemMethodClassesEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.mapper.TcItemMethodClassesMapper;
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
 * 品项做法类别信息 服务实现类
 * </p>
 *
 * @author LiPei
 * @since 2024-03-10
 */
@Service
public class TcItemMethodClassesDaoImpl extends BaseServiceImpl<TcItemMethodClassesMapper, TcItemMethodClassesEntity> implements ITcItemMethodClassesDao {

    /**
     * 根据集团编码查询类别信息
     *
     * @param centerId 集团ID
     * @return 结果
     */
    @Override
    public List<TcItemMethodClassesEntity> getListByCenterId(String centerId) {
        LambdaQueryWrapper<TcItemMethodClassesEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TcItemMethodClassesEntity::getCenterId, centerId)
            .orderByAsc(TcItemMethodClassesEntity::getId);
        return this.listAll(wrapper);
    }

    /**
     * 根据集团编码查询类别信息
     * @param centerId 集团ID
     * @return 结果
     */
    @Override
    public Map<String, TcItemMethodClassesEntity> getMapByCenterId(String centerId){
        List<TcItemMethodClassesEntity> list = this.getListByCenterId(centerId);
        if(ObjectUtils.isEmpty(list)){
            return new HashMap<>();
        }
        return list.stream().collect(Collectors.toMap(TcItemMethodClassesEntity::getId, item -> item));
    }

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void operateBatch(List<TcItemMethodClassesEntity> saveList, List<TcItemMethodClassesEntity> updateList){
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
    public void updateBatchByEntity(List<TcItemMethodClassesEntity> updateList){
        this.updateBatchNoneEntity(updateList, item -> {
            LambdaUpdateWrapper<TcItemMethodClassesEntity> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(TcItemMethodClassesEntity::getCenterId, item.getCenterId())
                .eq(TcItemMethodClassesEntity::getId, item.getId());
            return wrapper;
        });
    }
}
