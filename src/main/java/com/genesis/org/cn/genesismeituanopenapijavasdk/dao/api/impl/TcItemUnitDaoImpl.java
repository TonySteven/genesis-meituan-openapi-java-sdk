package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcItemUnitDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcItemUnitEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.mapper.TcItemUnitMapper;
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
 * 品项单位信息 服务实现类
 * </p>
 *
 * @author LiPei
 * @since 2024-03-16
 */
@Service
public class TcItemUnitDaoImpl extends BaseServiceImpl<TcItemUnitMapper, TcItemUnitEntity> implements ITcItemUnitDao {

    /**
     * 根据集团编码和单位id查询单位信息
     * @param centerId 集团id
     * @return 结果
     */
    @Override
    public List<TcItemUnitEntity> getListByCenterId(String centerId) {
        LambdaQueryWrapper<TcItemUnitEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TcItemUnitEntity::getCenterId, centerId)
            .orderByAsc(TcItemUnitEntity::getId);
        return this.listAll(wrapper);
    }

    /**
     * 根据集团编码和单位id查询单位信息
     * @param centerId 集团id
     * @return 结果
     */
    @Override
    public Map<String, TcItemUnitEntity> getMapByCenterId(String centerId){
        List<TcItemUnitEntity> list = this.getListByCenterId(centerId);
        if(ObjectUtils.isEmpty(list)){
            return new HashMap<>();
        }
        return list.stream().collect(Collectors.toMap(TcItemUnitEntity::getId, item -> item));
    }

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void operateBatch(List<TcItemUnitEntity> saveList, List<TcItemUnitEntity> updateList){
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
    public void updateBatchByEntity(List<TcItemUnitEntity> updateList){
        this.updateBatchNoneEntity(updateList, item -> {
            LambdaUpdateWrapper<TcItemUnitEntity> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(TcItemUnitEntity::getCenterId, item.getCenterId())
                .eq(TcItemUnitEntity::getId, item.getId());
            return wrapper;
        });
    }

}
