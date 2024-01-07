package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bty.scm.boot.mybatis.base.BaseDaoImpl;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcShopBillingDetailDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcShopBillingDetailEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.mapper.TcShopBillingDetailMapper;
import com.github.pagehelper.util.StringUtil;
import org.springframework.stereotype.Repository;

/**
 * TcShopBillingDetail表数据层实现
 *
 * @author 人工智能
 * @date 2023-12-10 16:13:44
 */
@Repository
public class TcShopBillingDetailDaoImpl extends BaseDaoImpl<TcShopBillingDetailMapper, TcShopBillingDetailEntity> implements ITcShopBillingDetailDao {

    /**
     * delete tc shop billing detail entity by shop id
     * 根据门店id删除门店账单明细表数据
     *
     * @param shopId   门店id
     * @param centerId center id
     */
    @Override
    public void deleteTcShopBillingDetailEntityByShopId(String centerId, String shopId) {
        if (StringUtil.isEmpty(shopId) || StringUtil.isEmpty(centerId)) {
            return;
        }

        // 修复MybatisPlusException: cannot use this method for "getEntity",必须new一个lambdaQueryWrapper对象.
        LambdaQueryWrapper<TcShopBillingDetailEntity> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(TcShopBillingDetailEntity::getShopId, shopId);

        this.remove(lambdaQueryWrapper);
    }
}
