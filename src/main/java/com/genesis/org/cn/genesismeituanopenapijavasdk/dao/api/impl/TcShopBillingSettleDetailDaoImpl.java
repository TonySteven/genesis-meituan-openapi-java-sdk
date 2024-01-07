package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcShopBillingSettleDetailDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcShopBillingSettleDetailEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.mapper.TcShopBillingSettleDetailMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * TcShopBillingSettleDetail表数据层实现
 *
 * @author 人工智能
 * &#064;date  2023-12-10 16:13:45
 */
@Repository
public class TcShopBillingSettleDetailDaoImpl extends ServiceImpl<TcShopBillingSettleDetailMapper, TcShopBillingSettleDetailEntity> implements ITcShopBillingSettleDetailDao {

    /**
     * delete tc shop billing settle detail entity by shop id
     * 根据门店id删除门店账单结算明细表数据
     *
     * @param centerId center id
     * @param shopId   门店id
     */
    @Override
    public void deleteTcShopBillingSettleDetailEntityByShopId(String centerId, String shopId) {
        if (StringUtils.isBlank(shopId) || StringUtils.isBlank(centerId)) {
            return;
        }
        // 删除门店账单结算明细表数据
        this.baseMapper.deleteTcShopBillingSettleDetailEntityByShopId(centerId, shopId);
    }
}
