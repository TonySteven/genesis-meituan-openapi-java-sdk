package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.request.goodlife.settle_ledger;

import com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.request.BaseRequest;
import lombok.Data;

import java.util.List;

@Data
public class SettleLedgerRecordQueryRequest implements BaseRequest {

    public SettleLedgerRecordQueryRequest(){

    }

    public SettleLedgerRecordQueryRequest(List<String> certificate_ids){
        this.certificate_ids = certificate_ids;
    }

    /**
     * 券码的标识（验券时返回）列表，列表长度范围1～50，多个值使用,拼接
     */
    private List<String> certificate_ids;

    @Override
    public String getUrl() {
        return "/goodlife/v1/settle/ledger/query_record_by_cert/";
    }
}
