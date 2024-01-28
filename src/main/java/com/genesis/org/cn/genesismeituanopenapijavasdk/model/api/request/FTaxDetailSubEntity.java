package com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request;

import lombok.Data;

/**
 * 金蝶生成应付单入参
 *
 * @author steven
 * &#064;date  2023/10/29
 */
@Data
public class FTaxDetailSubEntity {

    private int FDetailID;
    private BaseFNumber FTaxRateId;
    private int FTaxRate;
    private int FTaxAmountT;
    private int FCostPercent;
    private int FCostAmount;
    private String FVAT;
    private String FSellerWithholding;
    private String FBuyerWithholding;
}
