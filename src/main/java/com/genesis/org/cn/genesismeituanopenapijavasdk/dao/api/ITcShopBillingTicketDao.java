package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcShopBillingTicketEntity;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 天才账单明细数据 服务类
 * </p>
 *
 * @author LiPei
 * @since 2024-05-13
 */
public interface ITcShopBillingTicketDao extends IService<TcShopBillingTicketEntity> {

    /**
     * 根据集团编码查询单据明细信息
     *
     * @param centerId 集团ID
     * @param shopId 门店ID
     * @param bsIdList 账单ID集合
     * @return 结果
     */
    List<TcShopBillingTicketEntity> getListByCenterId(String centerId,String shopId,List<String> bsIdList,List<String> ticketCodeList);

    /**
     * 根据集团编码查询单据明细信息
     *
     * @param centerId 集团ID
     * @param shopId 门店ID
     * @param bsIdList 账单ID集合
     * @param ticketCodeList 券码集合
     * @return 结果
     */
    Map<String, TcShopBillingTicketEntity> getMapByCenterId(String centerId,String shopId,List<String> bsIdList,List<String> ticketCodeList);

    /**
     * 批量操作数据
     * @param saveList 批量新增数据
     * @param updateList 批量修改数据
     * @return 结果
     */
    void operateBatch(List<TcShopBillingTicketEntity> saveList, List<TcShopBillingTicketEntity> updateList);

    /**
     * 批量修改 - 根据实体类内容修改
     * @param updateList 批量修改数据
     */
    void updateBatchByEntity(List<TcShopBillingTicketEntity> updateList);

}
