package com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 金蝶生成凭证单 Model中的FEntity
 *
 * @author steven
 * &#064;date  2023/10/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KingdeeSaveCredentialOrderFEntity {

    private String FEntryID;
    private String FEXPLANATION;
    private BaseFNumber FACCOUNTID;
    private KingdeeSaveCredentialOrderFEntityFDetailId FDetailID;
    private BaseFNumber FCURRENCYID;
    private BaseFNumber FEXCHANGERATETYPE;
    private int FEXCHANGERATE = 1;
    private BaseFNumber FUnitId;
    private String FPrice;
    private String FQty;
    private String FAMOUNTFOR;
    private String FDEBIT;
    private String FCREDIT;
    private BaseFNumber FSettleTypeID;
    private String FSETTLENO;
    private String FBUSNO;
    private String FEXPORTENTRYID;

}
