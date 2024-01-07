package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bty.scm.boot.mybatis.base.BaseDaoImpl;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcShopBillingSettleDetailDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcShopBillingSettleDetailEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.mapper.TcShopBillingSettleDetailMapper;
import com.github.pagehelper.util.StringUtil;
import org.springframework.stereotype.Repository;

/**
 * TcShopBillingSettleDetail表数据层实现
 *
 * @author 人工智能
 * @date 2023-12-10 16:13:45
 */
@Repository
public class TcShopBillingSettleDetailDaoImpl extends BaseDaoImpl<TcShopBillingSettleDetailMapper, TcShopBillingSettleDetailEntity> implements ITcShopBillingSettleDetailDao {

    /**
     * delete tc shop billing settle detail entity by shop id
     * 根据门店id删除门店账单结算明细表数据
     *
     * @param centerId center id
     * @param shopId   门店id
     */
    @Override
    public void deleteTcShopBillingSettleDetailEntityByShopId(String centerId, String shopId) {
        if (StringUtil.isEmpty(shopId) || StringUtil.isEmpty(centerId)) {
            return;
        }
        // 修复MybatisPlusException: cannot use this method for "getEntity",必须new一个lambdaQueryWrapper对象.
        LambdaQueryWrapper<TcShopBillingSettleDetailEntity> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(TcShopBillingSettleDetailEntity::getCenterId, centerId);
        lambdaQueryWrapper.eq(TcShopBillingSettleDetailEntity::getShopId, shopId);

        this.remove(lambdaQueryWrapper);
    }
}
