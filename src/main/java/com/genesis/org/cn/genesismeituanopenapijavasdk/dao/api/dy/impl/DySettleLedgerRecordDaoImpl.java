package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.dy.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.dy.DySettleLedgerRecordDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.dy.DySettleLedgerRecordEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.mapper.dy.DySettleLedgerRecordMapper;
import com.genesis.org.cn.genesismeituanopenapijavasdk.database.service.impl.BaseServiceImpl;
import com.genesis.org.cn.genesismeituanopenapijavasdk.enums.DeletedEnums;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 抖音验券历史记录 服务实现类
 * </p>
 *
 * @author LiPei
 * @since 2024-02-03
 */
@Service
public class DySettleLedgerRecordDaoImpl extends BaseServiceImpl<DySettleLedgerRecordMapper, DySettleLedgerRecordEntity> implements DySettleLedgerRecordDao {


    /**
     * 根据企业号商家总店id、门店id和分账记录id查询抖音分账历史记录
     *
     * @param accountId 企业号商家总店id
     * @param poiId     门店id
     * @param ledgerIds 分账记录id
     * @return 结果
     */
    @Override
    public List<DySettleLedgerRecordEntity> getListByLedgerIds(String accountId, String poiId, List<String> ledgerIds) {
        if(ObjectUtils.isEmpty(ledgerIds)){
            return new ArrayList<>();
        }

        // 根据核销记录ID分组 / 500为一组进行查询
        List<List<String>> idsList = ListUtil.partition(ledgerIds,500);

        List<DySettleLedgerRecordEntity> list = new ArrayList<>();

        for(List<String> ids : idsList){

            LambdaQueryWrapper<DySettleLedgerRecordEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(DySettleLedgerRecordEntity::getAccountId, accountId)
                .eq(DySettleLedgerRecordEntity::getPoiId, poiId)
                .in(DySettleLedgerRecordEntity::getLedgerId, ids)
                .orderByAsc(DySettleLedgerRecordEntity::getId);
            list.addAll(this.list(wrapper));
        }

        return list;
    }

    /**
     * 根据企业号商家总店id、门店id和分账记录id查询抖音分账历史记录
     *
     * @param accountId 企业号商家总店id
     * @param poiId     门店id
     * @param ledgerIds 分账记录id
     * @return 结果
     */
    @Override
    public Map<String, DySettleLedgerRecordEntity> getMapByLedgerIds(String accountId, String poiId, List<String> ledgerIds) {
        List<DySettleLedgerRecordEntity> list = this.getListByLedgerIds(accountId,poiId,ledgerIds);
        if(ObjectUtils.isEmpty(list)){
            return new HashMap<>();
        }
        return list.stream().collect(Collectors.toMap(DySettleLedgerRecordEntity::getLedgerId, Function.identity()));
    }

    /**
     * 批量操作数据
     *
     * @param saveList   批量新增数据
     * @param updateList 批量修改数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void operateBatch(List<DySettleLedgerRecordEntity> saveList, List<DySettleLedgerRecordEntity> updateList) {
        if(ObjectUtils.isNotEmpty(saveList)){
            this.saveBatch(saveList);
        }

        if(ObjectUtils.isNotEmpty(updateList)){
            this.updateBatchByEntity(updateList);
        }
    }

    /**
     * 批量修改 - 根据实体类内容修改
     *
     * @param updateList 批量修改数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchByEntity(List<DySettleLedgerRecordEntity> updateList) {
        this.updateBatchNoneEntity(updateList, item -> {
            LambdaUpdateWrapper<DySettleLedgerRecordEntity> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(DySettleLedgerRecordEntity::getAccountId, item.getAccountId())
                .eq(DySettleLedgerRecordEntity::getPoiId, item.getPoiId())
                .eq(DySettleLedgerRecordEntity::getLedgerId, item.getLedgerId())
                .eq(DySettleLedgerRecordEntity::getIsDeleted, DeletedEnums.NONE_DELETE.getCode());
            return wrapper;
        });
    }
}
