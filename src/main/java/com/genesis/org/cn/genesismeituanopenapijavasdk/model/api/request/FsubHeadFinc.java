package com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request;

import lombok.Data;

import java.util.Date;

/**
 * 金蝶生成应付单入参
 *
 * @author steven
 * &#064;date  2023/10/29
 */
@Data
public class FsubHeadFinc {

    private int FEntryId;

    private Date FACCNTTIMEJUDGETIME;

    private BaseFNumber FSettleTypeID;

    private BaseFNumber FMAINBOOKSTDCURRID;

    private BaseFNumber FEXCHANGETYPE;

    private int FExchangeRate;

    private int FTaxAmountFor;

    private int FNoTaxAmountFor;

    private String FISCARRIEDDATE;

}
