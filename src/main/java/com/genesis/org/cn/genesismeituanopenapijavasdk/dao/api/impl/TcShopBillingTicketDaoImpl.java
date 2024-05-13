package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcShopBillingTicketDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcShopBillingTicketEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.mapper.TcShopBillingTicketMapper;
import com.genesis.org.cn.genesismeituanopenapijavasdk.database.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 天才账单明细数据 服务实现类
 * </p>
 *
 * @author LiPei
 * @since 2024-05-13
 */
@Service
public class TcShopBillingTicketDaoImpl extends BaseServiceImpl<TcShopBillingTicketMapper, TcShopBillingTicketEntity> implements ITcShopBillingTicketDao {

    /**
     * 根据集团编码查询单据明细信息
     *
     * @param centerId 集团ID
     * @param shopId 门店ID
     * @param bsIdList 账单ID集合
     * @return 结果
     */
    @Override
    public List<TcShopBillingTicketEntity> getListByCenterId(String centerId,String shopId,List<String> bsIdList,List<String> ticketCodeList) {
        LambdaQueryWrapper<TcShopBillingTicketEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TcShopBillingTicketEntity::getCenterId, centerId)
            .in(ObjectUtils.isNotEmpty(bsIdList),TcShopBillingTicketEntity::getBsId, bsIdList)
            .in(ObjectUtils.isNotEmpty(ticketCodeList),TcShopBillingTicketEntity::getTicketCode, ticketCodeList)
            .eq(StringUtils.isNotEmpty(shopId),TcShopBillingTicketEntity::getShopId, shopId)
            .orderByAsc(TcShopBillingTicketEntity::getId);
        return this.listAll(wrapper);
    }

    /**
     * 根据集团编码查询单据明细信息
     * @param centerId 集团ID
     * @return 结果
     */
    @Override
    public Map<String, TcShopBillingTicketEntity> getMapByCenterId(String centerId,String shopId,List<String> bsIdList,List<String> ticketCodeList){
        List<TcShopBillingTicketEntity> list = this.getListByCenterId(centerId,shopId,bsIdList,ticketCodeList);
        if(ObjectUtils.isEmpty(list)){
            return new HashMap<>();
        }
        // 根据唯一索引转map
        return list.stream()
            .collect(Collectors.toMap(item -> item.getShopId() + item.getBsId() + item.getTicketCode() + LocalDateTimeUtil.format(item.getUseTime(), DatePattern.PURE_DATETIME_PATTERN),
                item -> item));
    }

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void operateBatch(List<TcShopBillingTicketEntity> saveList, List<TcShopBillingTicketEntity> updateList){
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
    public void updateBatchByEntity(List<TcShopBillingTicketEntity> updateList){
        this.updateBatchNoneEntity(updateList, item -> {
            LambdaUpdateWrapper<TcShopBillingTicketEntity> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(TcShopBillingTicketEntity::getCenterId, item.getCenterId())
                .eq(TcShopBillingTicketEntity::getShopId, item.getShopId())
                .eq(TcShopBillingTicketEntity::getBsId, item.getBsId())
                .eq(TcShopBillingTicketEntity::getTicketCode, item.getTicketCode())
                .eq(TcShopBillingTicketEntity::getUseTime, item.getUseTime());
            return wrapper;
        });
    }

}
