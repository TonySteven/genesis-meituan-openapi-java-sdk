package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcShopBillingDetailEntity;

/**
 * TcShopBillingDetail表数据层接口
 *
 * @author 人工智能
 * @date 2023-12-10 16:13:44
 */
public interface ITcShopBillingDetailDao extends IService<TcShopBillingDetailEntity> {

    /**
     * delete tc shop billing detail entity by shop id
     * 根据门店id删除门店账单明细表数据
     *
     * @param shopId   门店id
     * @param centerId center id
     */
    void deleteTcShopBillingDetailEntityByShopId(String centerId, String shopId);

}
