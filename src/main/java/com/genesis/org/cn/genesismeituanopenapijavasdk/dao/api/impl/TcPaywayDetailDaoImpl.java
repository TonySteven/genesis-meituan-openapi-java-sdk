package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcPaywayDetailDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcPaywayDetailEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.mapper.TcPaywayDetailMapper;
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
public class TcPaywayDetailDaoImpl extends BaseServiceImpl<TcPaywayDetailMapper, TcPaywayDetailEntity> implements ITcPaywayDetailDao {

    /**
     * 根据集团编码查询类别信息
     *
     * @param centerId 集团ID
     * @return 结果
     */
    @Override
    public List<TcPaywayDetailEntity> getListByCenterId(String centerId) {
        LambdaQueryWrapper<TcPaywayDetailEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TcPaywayDetailEntity::getCenterId, centerId)
            .orderByAsc(TcPaywayDetailEntity::getPaywayId);
        return this.listAll(wrapper);
    }

    /**
     * 根据集团编码查询类别信息
     * @param centerId 集团ID
     * @return 结果
     */
    @Override
    public Map<String, TcPaywayDetailEntity> getMapByCenterId(String centerId){
        List<TcPaywayDetailEntity> list = this.getListByCenterId(centerId);
        if(ObjectUtils.isEmpty(list)){
            return new HashMap<>();
        }
        return list.stream().collect(Collectors.toMap(TcPaywayDetailEntity::getPaywayId, item -> item));
    }

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void operateBatch(List<TcPaywayDetailEntity> saveList, List<TcPaywayDetailEntity> updateList){
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
    public void updateBatchByEntity(List<TcPaywayDetailEntity> updateList){
        this.updateBatchNoneEntity(updateList, item -> {
            LambdaUpdateWrapper<TcPaywayDetailEntity> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(TcPaywayDetailEntity::getCenterId, item.getCenterId())
                .eq(TcPaywayDetailEntity::getPaywayId, item.getPaywayId());
            return wrapper;
        });
    }

}
