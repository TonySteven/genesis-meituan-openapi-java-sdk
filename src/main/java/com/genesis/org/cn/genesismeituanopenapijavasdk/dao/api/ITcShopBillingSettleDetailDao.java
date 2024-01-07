package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcShopBillingSettleDetailEntity;

/**
 * TcShopBillingSettleDetail表数据层接口
 *
 * @author 人工智能
 * @date 2023-12-10 16:13:45
 */
public interface ITcShopBillingSettleDetailDao extends IService<TcShopBillingSettleDetailEntity> {

    /**
     * delete tc shop billing settle detail entity by shop id
     * 根据门店id删除门店账单结算明细表数据
     *
     * @param shopId   门店id
     * @param centerId center id
     */
    void deleteTcShopBillingSettleDetailEntityByShopId(String centerId, String shopId);
}
