package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.request;

import lombok.Data;

/**
 * 账单明细查询参数
 */
@Data
public class TcShopBillingTicketQueryRequest {

    /**
     * 餐饮集团ID
     */
    private String centerId;

    /**
     * 门店ID
     */
    private String shopId;

    /**
     * 营业日期 yyyy-MM-dd HH:mm:ss
     */
    private String begin;

    /**
     * 营业日期 yyyy-MM-dd HH:mm:ss
     */
    private String end;

    /**
     * 页码
     */
    private Integer pageNo = 1;

    /**
     * 分页大小（最多500 大于500系统默认500）
     */
    private Integer pageSize = 500;

    /**
     * 平台 [1,”美团点评-团购”],[2,”支付宝-口碑”],[3,”抖音券”],[4,”快手券”],[5,”支付宝团购”],[6,”京东-团购”],[7,”易百券”]
     */
    private String seller;
}
