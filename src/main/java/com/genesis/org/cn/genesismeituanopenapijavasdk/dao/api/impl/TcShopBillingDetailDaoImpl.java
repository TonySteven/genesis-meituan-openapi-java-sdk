package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcShopBillingDetailDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcShopBillingDetailEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.mapper.TcShopBillingDetailMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * TcShopBillingDetail表数据层实现
 *
 * @author 人工智能
 * &#064;date  2023-12-10 16:13:44
 */
@Repository
public class TcShopBillingDetailDaoImpl extends ServiceImpl<TcShopBillingDetailMapper, TcShopBillingDetailEntity> implements ITcShopBillingDetailDao {

    /**
     * delete tc shop billing detail entity by shop id
     * 根据门店id删除门店账单明细表数据
     *
     * @param shopId   门店id
     * @param centerId center id
     */
    @Override
    public void deleteTcShopBillingDetailEntityByShopId(String centerId, String shopId) {
        if (StringUtils.isBlank(shopId) || StringUtils.isBlank(centerId)) {
            return;
        }

        // 写sql删除门店账单明细表数据
        this.baseMapper.deleteTcShopBillingDetailEntityByShopId(centerId, shopId);
    }
}
