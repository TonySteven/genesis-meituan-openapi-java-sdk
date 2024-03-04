package com.genesis.org.cn.genesismeituanopenapijavasdk.domain.kingdee.model.constant;

/**
 * 金蝶应付单调用基础常量
 */
public class KingdeePayableBillSuccessConstant {
    public static final String PAYABLE_BILL_SUCCESS_JSON_DATA = """
        {
                "Model": {
                        "FID": 0,
                        "FBillTypeID": {
                                "FNUMBER": "YFD02_SYS"
                        },
                        "FBillNo": "",
                        "FISINIT": "false",
                        "FDATE": "2024-01-18",
                        "FENDDATE_H": "2024-01-18",
                        "FDOCUMENTSTATUS": "",
                        "FSUPPLIERID": {
                                "FNumber": "100001"
                        },
                        "FCURRENCYID": {
                                "FNumber": "PRE001"
                        },
                        "FPayConditon": {
                                "FNumber": ""
                        },
                        "FISPRICEEXCLUDETAX": "false",
                        "FSourceBillType": "",
                        "FBUSINESSTYPE": "FY",
                        "FISTAX": "false",
                        "FSETTLEORGID": {
                                "FNumber": "21210002"
                        },
                        "FPAYORGID": {
                                "FNumber": "21210002"
                        },
                        "FSetAccountType": "1",
                        "FISTAXINCOST": "false",
                        "FAP_Remark": "货款备注",
                        "FISHookMatch": "false",
                        "FCancelStatus": "A",
                        "FMatchMethodID": 0,
                        "FBILLMATCHLOGID": 0,
                        "FWBOPENQTY": "false",
                        "FIsGeneratePlanByCostItem": "false",
                        "FSCPCONFIRMDATE": "2024-01-18",
                        "FOrderDiscountAmountFor": 0,
                        "FsubHeadFinc": {
                                "FEntryId": 0,
                                "FACCNTTIMEJUDGETIME": "2024-01-18",
                                "FSettleTypeID": {
                                        "FNumber": ""
                                },
                                "FMAINBOOKSTDCURRID": {
                                        "FNumber": "PRE001"
                                },
                                "FEXCHANGETYPE": {
                                        "FNumber": "HLTX01_SYS"
                                },
                                "FExchangeRate": 1,
                                "FTaxAmountFor": 0,
                                "FNoTaxAmountFor": 0,
                                "FISCARRIEDDATE": "false"
                        },
                        "FEntityDetail": [{
                                "FEntryID": 0,
                                "FCOSTID": {
                                        "FNumber": "CI018"
                                },
                                "FMATERIALID": {
                                        "FNumber": "140301"
                                },
                                "FMaterialDesc": "",
                                "FASSETID": {
                                        "FNUMBER": ""
                                },
                                "FPRICEUNITID": {
                                        "FNumber": ""
                                },
                                "FPrice": 0,
                                "FPriceQty": 0,
                                "FTaxPrice": 0,
                                "FPriceWithTax": 0,
                                "FEntryTaxRate": 13,
                                "FTaxCombination": {
                                        "FNumber": ""
                                },
                                "FEntryDiscountRate": 0,
                                "FSourceBillNo": "",
                                "FSOURCETYPE": "",
                                "FDISCOUNTAMOUNTFOR": 0,
                                "FALLAMOUNTFOR_D": 50767.50
                        }]
                }
        }""";
}
