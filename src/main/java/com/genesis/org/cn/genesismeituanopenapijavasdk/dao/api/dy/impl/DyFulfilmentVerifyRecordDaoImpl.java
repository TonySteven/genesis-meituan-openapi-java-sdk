package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.dy.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.dy.IDyFulfilmentVerifyRecordDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.dy.DyFulfilmentVerifyRecordEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.mapper.dy.DyFulfilmentVerifyRecordMapper;
import com.genesis.org.cn.genesismeituanopenapijavasdk.database.service.impl.BaseServiceImpl;
import com.genesis.org.cn.genesismeituanopenapijavasdk.enums.DeletedEnums;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy.goodlife.settle_ledger.DySettleLedgerRecordSyncCmd;
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
public class DyFulfilmentVerifyRecordDaoImpl extends BaseServiceImpl<DyFulfilmentVerifyRecordMapper, DyFulfilmentVerifyRecordEntity> implements IDyFulfilmentVerifyRecordDao {

    /**
     * 根据企业号商家总店id、门店id和核销记录id查询抖音验券历史记录
     * @param accountId 企业号商家总店id
     * @param poiId 门店id
     * @return 结果
     */
    @Override
    public List<DyFulfilmentVerifyRecordEntity> getListByVerifyIds(String accountId, String poiId, List<String> verifyIds){
        if(ObjectUtils.isEmpty(verifyIds)){
            return new ArrayList<>();
        }

        // 根据核销记录ID分组 / 500为一组进行查询
        List<List<String>> verifyIdsList = ListUtil.partition(verifyIds,500);

        List<DyFulfilmentVerifyRecordEntity> list = new ArrayList<>();

        for(List<String> ids : verifyIdsList){

            LambdaQueryWrapper<DyFulfilmentVerifyRecordEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(DyFulfilmentVerifyRecordEntity::getAccountId, accountId)
                .eq(DyFulfilmentVerifyRecordEntity::getPoiId, poiId)
                .in(DyFulfilmentVerifyRecordEntity::getVerifyId, ids)
                .orderByAsc(DyFulfilmentVerifyRecordEntity::getId);
            list.addAll(this.list(wrapper));
        }

        return list;
    }

    /**
     * 根据企业号商家总店id、门店id和核销记录id查询抖音验券历史记录
     *
     * @param accountId 企业号商家总店id
     * @param poiId     门店id
     * @param verifyIds 核销记录id
     * @return 结果
     */
    @Override
    public Map<String, DyFulfilmentVerifyRecordEntity> getMapByVerifyIds(String accountId, String poiId, List<String> verifyIds) {
        List<DyFulfilmentVerifyRecordEntity> list = this.getListByVerifyIds(accountId,poiId,verifyIds);
        if(ObjectUtils.isEmpty(list)){
            return new HashMap<>();
        }
        return list.stream().collect(Collectors.toMap(DyFulfilmentVerifyRecordEntity::getVerifyId, Function.identity()));
    }

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void operateBatch(List<DyFulfilmentVerifyRecordEntity> saveList, List<DyFulfilmentVerifyRecordEntity> updateList){
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
    public void updateBatchByEntity(List<DyFulfilmentVerifyRecordEntity> updateList){
        this.updateBatchNoneEntity(updateList, item -> {
            LambdaUpdateWrapper<DyFulfilmentVerifyRecordEntity> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(DyFulfilmentVerifyRecordEntity::getAccountId, item.getAccountId())
                .eq(DyFulfilmentVerifyRecordEntity::getPoiId, item.getPoiId())
                .eq(DyFulfilmentVerifyRecordEntity::getVerifyId, item.getVerifyId())
                .eq(DyFulfilmentVerifyRecordEntity::getIsDeleted, DeletedEnums.NONE_DELETE.getCode());
            return wrapper;
        });
    }


    /**
     * 根据企业号商家总店id、门店id和券id查询抖音验券历史记录
     * @param accountId 企业号商家总店id
     * @param poiId 门店id
     * @param certificateIds 券id
     * @return 结果
     */
    @Override
    public List<DyFulfilmentVerifyRecordEntity> getListByCertificateIds(String accountId,String poiId,List<String> certificateIds){
        LambdaQueryWrapper<DyFulfilmentVerifyRecordEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DyFulfilmentVerifyRecordEntity::getAccountId, accountId)
            .eq(DyFulfilmentVerifyRecordEntity::getPoiId, poiId)
            .in(ObjectUtils.isNotEmpty(certificateIds),DyFulfilmentVerifyRecordEntity::getVerifyId, certificateIds)
            .orderByAsc(DyFulfilmentVerifyRecordEntity::getId);
        return this.listAll(wrapper);
    }

    /**
     * 根据企业号商家总店id、门店id和券id查询抖音验券历史券ID
     * @param accountId 企业号商家总店id
     * @param poiId 门店id
     * @param cmd 请求入参
     * @return 结果
     */
    @Override
    public List<String> getCertificateIds(String accountId,String poiId, DySettleLedgerRecordSyncCmd cmd){
        LambdaQueryWrapper<DyFulfilmentVerifyRecordEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(DyFulfilmentVerifyRecordEntity::getCertificateId)
            .eq(DyFulfilmentVerifyRecordEntity::getAccountId, accountId)
            .eq(DyFulfilmentVerifyRecordEntity::getPoiId, poiId)
            .in(ObjectUtils.isNotEmpty(cmd.getCertificateIds()),DyFulfilmentVerifyRecordEntity::getVerifyId, cmd.getCertificateIds())
            .ge(ObjectUtils.isNotEmpty(cmd.getStartTime()),DyFulfilmentVerifyRecordEntity::getVerifyTime, cmd.getStartTime())
            .le(ObjectUtils.isNotEmpty(cmd.getEndTime()),DyFulfilmentVerifyRecordEntity::getVerifyTime, cmd.getEndTime())
            .orderByAsc(DyFulfilmentVerifyRecordEntity::getId);
        List<DyFulfilmentVerifyRecordEntity> list = this.listAll(wrapper);

        if(ObjectUtils.isEmpty(list)){
            return new ArrayList<>();
        }

        return list.stream().map(DyFulfilmentVerifyRecordEntity::getCertificateId).distinct().collect(Collectors.toList());
    }
}
