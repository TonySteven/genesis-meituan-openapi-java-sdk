package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcShopBillingDetailItemDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcShopBillingDetailItemEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.mapper.TcShopBillingDetailItemMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * TcShopBillingDetailItem表数据层实现
 *
 * @author 人工智能
 * &#064;date  2023-12-10 16:13:44
 */
@Repository
public class TcShopBillingDetailItemDaoImpl extends ServiceImpl<TcShopBillingDetailItemMapper, TcShopBillingDetailItemEntity> implements ITcShopBillingDetailItemDao {

    /**
     * delete tc shop billing detail item entity by shop id
     * 根据门店id删除门店账单明细表数据
     *
     * @param centerId center id
     * @param shopId   门店id
     */
    @Override
    public void deleteTcShopBillingDetailItemEntityByShopId(String centerId, String shopId) {
        if (StringUtils.isBlank(shopId) || StringUtils.isBlank(centerId)) {
            return;
        }

        baseMapper.delete(new QueryWrapper<TcShopBillingDetailItemEntity>().lambda()
            .eq(TcShopBillingDetailItemEntity::getShopId, shopId)
            .eq(TcShopBillingDetailItemEntity::getCenterId, centerId));
    }
}
