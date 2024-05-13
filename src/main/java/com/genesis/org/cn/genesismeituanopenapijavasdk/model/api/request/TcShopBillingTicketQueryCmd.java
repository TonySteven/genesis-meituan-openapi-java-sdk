package com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request;

import lombok.Data;

import java.util.List;

/**
 * 账单明细查询参数
 */
@Data
public class TcShopBillingTicketQueryCmd {

    /**
     * 门店ID
     */
    private List<String> shopIdList;

    /**
     * 营业日期 yyyy-MM-dd HH:mm:ss
     */
    private String begin;

    /**
     * 营业日期 yyyy-MM-dd HH:mm:ss
     */
    private String end;

    /**
     * 平台 [1,”美团点评-团购”],[2,”支付宝-口碑”],[3,”抖音券”],[4,”快手券”],[5,”支付宝团购”],[6,”京东-团购”],[7,”易百券”]
     */
    private String seller;
}
