package com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy.goodlife.shop;

import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy.BaseCmd;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 查询抖音门店信息请求模型
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ShopAllSyncCmd extends BaseCmd {

    /**
     * 企业号商家总店id（验券准备接口中返回）
     */
    private List<String> accountIds;

    /**
     * 门店id列表，不传默认返回商家所有门店核销记录，多个值使用,拼接
     */
    private String poiId;
}
