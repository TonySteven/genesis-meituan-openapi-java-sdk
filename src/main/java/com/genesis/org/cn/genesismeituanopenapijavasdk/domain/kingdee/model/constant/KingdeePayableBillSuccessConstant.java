package com.genesis.org.cn.genesismeituanopenapijavasdk.domain.kingdee.model.constant;

/**
 * 金蝶应付单调用基础常量
 */
public class KingdeePayableBillSuccessConstant {

    public static final String CREDENTIAL_BILL_SUCCESS_JSON_DATA = """
        {
             "Model": {
                 "FVOUCHERID": 0,
                 "FAccountBookID": {
                     "FNumber": ""
                 },
                 "FDate": "1900-01-01",
                 "FBUSDATE": "1900-01-01",
                 "FYEAR": 0,
                 "FPERIOD": 0,
                 "FVOUCHERGROUPID": {
                     "FNumber": ""
                 },
                 "FVOUCHERGROUPNO": "",
                 "FATTACHMENTS": 0,
                 "FISADJUSTVOUCHER": "false",
                 "FSourceBillKey": {
                     "FNumber": ""
                 },
                 "FIMPORTVERSION": "",
                 "FDocumentStatus": "",
                 "FEntity": [{
                     "FEntryID": 0,
                     "FEXPLANATION": "",
                     "FACCOUNTID": {
                         "FNumber": ""
                     },
                     "FDetailID": {
                         "FDETAILID__FFLEX9": {
                             "FNumber": ""
                         },
                         "FDETAILID__FFLEX4": {
                             "FNumber": ""
                         },
                         "FDETAILID__FFLEX5": {
                             "FNumber": ""
                         },
                         "FDETAILID__FFLEX6": {
                             "FNumber": ""
                         },
                         "FDETAILID__FFLEX7": {
                             "FNumber": ""
                         },
                         "FDETAILID__FFLEX8": {
                             "FNumber": ""
                         },
                         "FDETAILID__FFLEX10": {
                             "FNumber": ""
                         },
                         "FDETAILID__FFLEX11": {
                             "FNumber": ""
                         },
                         "FDETAILID__FFLEX15": {
                             "FNumber": ""
                         },
                         "FDETAILID__FFLEX12": {
                             "FNumber": ""
                         },
                         "FDETAILID__FFLEX13": {
                             "FNumber": ""
                         },
                         "FDETAILID__FFLEX14": {
                             "FNumber": ""
                         },
                         "FDETAILID__FFLEX16": {
                             "FNumber": ""
                         }
                     },
                     "FCURRENCYID": {
                         "FNumber": ""
                     },
                     "FEXCHANGERATETYPE": {
                         "FNumber": ""
                     },
                     "FEXCHANGERATE": 0,
                     "FUnitId": {
                         "FNUMBER": ""
                     },
                     "FPrice": 0,
                     "FQty": 0,
                     "FAMOUNTFOR": 0,
                     "FDEBIT": 0,
                     "FCREDIT": 0,
                     "FSettleTypeID": {
                         "FNumber": ""
                     },
                     "FSETTLENO": "",
                     "FBUSNO": "",
                     "FEXPORTENTRYID": 0
                 }]
             }
         }""";
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

    private KingdeePayableBillSuccessConstant() {
        throw new IllegalStateException("Utility class");
    }


}
