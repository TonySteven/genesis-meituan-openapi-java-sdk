package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcShopBillingDetailEntity;

/**
 * TcShopBillingDetail表Mapper接口
 *
 * @author 人工智能
 * @date 2023-12-10 16:13:44
 */
public interface TcShopBillingDetailMapper extends BaseMapper<TcShopBillingDetailEntity> {

    /**
     * delete tc shop billing detail entity by shop id
     * 根据门店id删除门店账单明细表数据
     *
     * @param centerId center id
     * @param shopId   门店id
     */
    void deleteTcShopBillingDetailEntityByShopId(String centerId, String shopId);
}
