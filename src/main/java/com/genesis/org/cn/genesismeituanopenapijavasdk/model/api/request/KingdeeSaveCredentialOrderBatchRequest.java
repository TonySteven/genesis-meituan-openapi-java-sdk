package com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 金蝶生成凭证单入参(批量)
 *
 * @author steven
 * &#064;date  2023/10/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KingdeeSaveCredentialOrderBatchRequest {

    private List<String> NeedUpDateFields;
    private List<String> NeedReturnFields;
    private String IsDeleteEntry;
    private String SubSystemId;
    private String IsVerifyBaseDataField;
    private String IsEntryBatchFill;
    private String ValidateFlag;
    private String NumberSearch;
    private String IsAutoAdjustField;
    private String InterationFlags;
    private String IgnoreInterationFlag;
    private String IsControlPrecision;
    private String ValidateRepeatJson;

    /**
     * model
     */
    private List<KingdeeSaveCredentialOrderRequestModel> Model;

}
