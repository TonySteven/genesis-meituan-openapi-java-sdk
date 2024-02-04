package com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy.goodlife.fulfilment_verify;

import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.request.goodlife.fulfilment_verify.FulfilmentVerifyRecordQueryRequest;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy.BaseCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy.BaseQueryCmd;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 抖音验券历史查询请求模型
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FulfilmentVerifyRecordSyncCmd extends BaseCmd {

    /**
     * 抖音 APP ID
     */
    @NotBlank(message = "抖音appId不能为空")
    private String appId;

    /**
     * 企业号商家总店id（验券准备接口中返回）
     */
    @NotBlank(message = "企业号商家总店id不能为空")
    private String accountId;

    /**
     * 门店id列表，不传默认返回商家所有门店核销记录，多个值使用,拼接
     */
    private List<String> poiIds;

    /**
     * 起始时间戳，单位秒，不传表示今天
     */
    private Long startTime;

    /**
     * 截止时间戳，单位秒
     */
    private Long endTime;

    public FulfilmentVerifyRecordQueryRequest toRequest() {
        FulfilmentVerifyRecordQueryRequest request = new FulfilmentVerifyRecordQueryRequest();
        request.setStart_time(this.getStartTime());
        request.setEnd_time(this.getEndTime());
        return request;
    }
}
