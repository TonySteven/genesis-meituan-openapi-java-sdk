package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcShopBillingSettleDetailEntity;

/**
 * TcShopBillingSettleDetail表Mapper接口
 *
 * @author 人工智能
 * &#064;date  2023-12-10 16:13:45
 */
public interface TcShopBillingSettleDetailMapper extends BaseMapper<TcShopBillingSettleDetailEntity> {


    /**
     * delete tc shop billing settle detail entity by shop id
     * 根据门店id删除门店账单结算明细表数据
     *
     * @param centerId center id
     * @param shopId   门店id
     */
    void deleteTcShopBillingSettleDetailEntityByShopId(String centerId, String shopId);

}
