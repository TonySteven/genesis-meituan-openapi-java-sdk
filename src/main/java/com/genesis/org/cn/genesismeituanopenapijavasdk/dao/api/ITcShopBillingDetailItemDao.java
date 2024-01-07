package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api;

import com.bty.scm.boot.mybatis.base.IBaseDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcShopBillingDetailItemEntity;

/**
 * TcShopBillingDetailItem表数据层接口
 *
 * @author 人工智能
 * @date 2023-12-10 16:13:44
 */
public interface ITcShopBillingDetailItemDao extends IBaseDao<TcShopBillingDetailItemEntity> {

    /**
     * delete tc shop billing detail item entity by shop id
     * 根据门店id删除门店账单明细表数据
     *
     * @param shopId   门店id
     * @param centerId center id
     */
    void deleteTcShopBillingDetailItemEntityByShopId(String centerId, String shopId);


}
