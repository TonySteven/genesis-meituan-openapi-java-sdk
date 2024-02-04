package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.request.goodlife.fulfilment_verify;

import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.request.BaseQueryRequest;
import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 抖音验券历史查询请求模型
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FulfilmentVerifyRecordQueryRequest extends BaseQueryRequest implements BaseRequest {

    /**
     * 企业号商家总店id（验券准备接口中返回）
     */
    @NotBlank(message = "企业号商家总店id不能为空")
    private String account_id;

    /**
     * 门店id列表，不传默认返回商家所有门店核销记录，多个值使用,拼接
     */
    private List<String> poi_ids;

    @Override
    public String getUrl() {
        return "/goodlife/v1/fulfilment/certificate/verify_record/query/";
    }
}
