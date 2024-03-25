package com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class FsubHeadFinc {

    private int FEntryId;

    private String FACCNTTIMEJUDGETIME;

    private BaseFNumber FSettleTypeID;

    private BaseFNumber FMAINBOOKSTDCURRID;

    private BaseFNumber FEXCHANGETYPE;

    private int FExchangeRate;

    private int FTaxAmountFor;

    private int FNoTaxAmountFor;

    private String FISCARRIEDDATE;

}
