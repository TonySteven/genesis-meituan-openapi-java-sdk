package com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 金蝶生成应付单入参
 *
 * @author steven
 * &#064;date  2023/10/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KingdeeSavePayableOrderRequest {

    /**
     * need update fields
     */
    private List<String> NeedUpDateFields;

    /**
     * need return fields
     */
    private List<String> NeedReturnFields;

    /**
     * is delete entry
     */
    private String IsDeleteEntry;

    /**
     * sub system id
     */
    private String SubSystemId;

    /**
     * is verify base data field
     */
    private String IsVerifyBaseDataField;

    /**
     * is entry batch fill
     */
    private String IsEntryBatchFill;

    /**
     * validate flag
     */
    private String ValidateFlag;

    /**
     * number search
     */
    private String NumberSearch;

    /**
     * is auto adjust field
     */
    private String IsAutoAdjustField;

    /**
     * integration flags
     */
    private String InterationFlags;

    /**
     * ignore integration flag
     */
    private String IgnoreInterationFlag;

    /**
     * is control precision
     */
    private String IsControlPrecision;

    /**
     * validate repeat json
     */
    private String ValidateRepeatJson;

    /**
     * model
     */
    private KingdeeSavePayableOrderRequestModel Model;

}
