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
    public static final String CASH_CREDENTIAL_BILL_SUCCESS_JSON_DATA = """
        {
            "Model": [{
                "FAccountBookID": {
                    "FNumber": "24520004"
                },
                "FDate": "2024-01",
                "FBUSDATE": "2024-01",
                "FYEAR": 2024,
                "FPERIOD": 1,
                "FVOUCHERGROUPID": {
                    "FNumber": "PRE001"
                },
                "FVOUCHERGROUPNO": "13",
                "FSourceBillKey": {
                    "FNumber": "78050206-2fa6-40e3-b7c8-bd608146fa38"
                },
                "FEntity": [{
                    "FEXPLANATION": "2024-01堂食收入",
                    "FACCOUNTID": {
                        "FNumber": "1122"
                    },
                    "FDetailID": {
                        "FDETAILID__FFLEX6": {
                            "FNumber": "CUST0005"
                        }
                    },
                    "FCURRENCYID": {
                        "FNumber": "PRE001"
                    },
                    "FEXCHANGERATETYPE": {
                        "FNumber": "HLTX01_SYS"
                    },
                    "FEXCHANGERATE": 1,
                    "FAMOUNTFOR": "92720.72",
                    "FDEBIT": "92720.72"
                }, {
                    "FEXPLANATION": "2024-01堂食收入",
                    "FACCOUNTID": {
                        "FNumber": "6001.01"
                    },
                    "FCURRENCYID": {
                        "FNumber": "PRE001"
                    },
                    "FEXCHANGERATETYPE": {
                        "FNumber": "HLTX01_SYS"
                    },
                    "FEXCHANGERATE": 1,
                    "FAMOUNTFOR": "92720.72",
                    "FCREDIT": "92720.72"
                }, {
                    "FEXPLANATION": "2024-01美团点评收入",
                    "FACCOUNTID": {
                        "FNumber": "1122"
                    },
                    "FDetailID": {
                        "FDETAILID__FFLEX6": {
                            "FNumber": "CUST0001"
                        }
                    },
                    "FCURRENCYID": {
                        "FNumber": "PRE001"
                    },
                    "FEXCHANGERATETYPE": {
                        "FNumber": "HLTX01_SYS"
                    },
                    "FEXCHANGERATE": 1,
                    "FAMOUNTFOR": "158193.60",
                    "FDEBIT": "158193.60"
                }, {
                    "FEXPLANATION": "2024-01美团点评收入",
                    "FACCOUNTID": {
                        "FNumber": "6001.02"
                    },
                    "FCURRENCYID": {
                        "FNumber": "PRE001"
                    },
                    "FEXCHANGERATETYPE": {
                        "FNumber": "HLTX01_SYS"
                    },
                    "FEXCHANGERATE": 1,
                    "FAMOUNTFOR": "158193.60",
                    "FCREDIT": "158193.60"
                }, {
                    "FEXPLANATION": "2024-01抖音收入",
                    "FACCOUNTID": {
                        "FNumber": "1122"
                    },
                    "FDetailID": {
                        "FDETAILID__FFLEX6": {
                            "FNumber": "CUST0004"
                        }
                    },
                    "FCURRENCYID": {
                        "FNumber": "PRE001"
                    },
                    "FEXCHANGERATETYPE": {
                        "FNumber": "HLTX01_SYS"
                    },
                    "FEXCHANGERATE": 1,
                    "FAMOUNTFOR": "10551.50",
                    "FDEBIT": "10551.50"
                }, {
                    "FEXPLANATION": "2024-01抖音收入",
                    "FACCOUNTID": {
                        "FNumber": "6001.03"
                    },
                    "FCURRENCYID": {
                        "FNumber": "PRE001"
                    },
                    "FEXCHANGERATETYPE": {
                        "FNumber": "HLTX01_SYS"
                    },
                    "FEXCHANGERATE": 1,
                    "FAMOUNTFOR": "10551.50",
                    "FCREDIT": "10551.50"
                }, {
                    "FEXPLANATION": "2024-01人民币收入",
                    "FACCOUNTID": {
                        "FNumber": "1002"
                    },
                    "FDetailID": {
                        "FDETAILID__FFLEX15": {
                            "FNumber": "123"
                        },
                        "FDETAILID__FFLEX14": {
                            "FNumber": "002"
                        }
                    },
                    "FCURRENCYID": {
                        "FNumber": "PRE001"
                    },
                    "FEXCHANGERATETYPE": {
                        "FNumber": "HLTX01_SYS"
                    },
                    "FEXCHANGERATE": 1,
                    "FAMOUNTFOR": "402.00",
                    "FDEBIT": "402.00"
                }, {
                    "FEXPLANATION": "2024-01人民币收入",
                    "FACCOUNTID": {
                        "FNumber": "6001.01"
                    },
                    "FCURRENCYID": {
                        "FNumber": "PRE001"
                    },
                    "FEXCHANGERATETYPE": {
                        "FNumber": "HLTX01_SYS"
                    },
                    "FEXCHANGERATE": 1,
                    "FAMOUNTFOR": "402.00",
                    "FCREDIT": "402.00"
                }]
            }]
        }
        """;

    public static final String PAYABLE_BILL_SUCCESS_JSON_DATA = """
        {
            "Model": [{
                "FAccountBookID": {
                    "FNumber": "24732001"
                },
                "FDate": "2024-01-31",
                "FYEAR": 2024,
                "FPERIOD": 1,
                "FVOUCHERGROUPID": {
                    "FNumber": "PRE001"
                },
                "FVOUCHERGROUPNO": " ",
                "FSourceBillKey": {
                    "FNumber": "78050206-2fa6-40e3-b7c8-bd608146fa38"
                },
                "FEntity": [{
                    "FEntryID": 0,
                    "FEXPLANATION": "日照1店（万象汇店）门店盘盈",
                    "FACCOUNTID": {
                        "FNumber": "6401"
                    },
                    "FCURRENCYID": {
                        "FNumber": "PRE001"
                    },
                    "FEXCHANGERATETYPE": {
                        "FNumber": "HLTX01_SYS"
                    },
                    "FEXCHANGERATE": 1,
                    "FPrice": 0,
                    "FQty": 0,
                    "FAMOUNTFOR": "-2.15",
                    "FDEBIT": "-2.15",
                    "FEXPORTENTRYID": 0
                }, {
                    "FEntryID": 0,
                    "FEXPLANATION": "日照1店（万象汇店）门店盘盈",
                    "FACCOUNTID": {
                        "FNumber": "1403.01"
                    },
                    "FCURRENCYID": {
                        "FNumber": "PRE001"
                    },
                    "FEXCHANGERATETYPE": {
                        "FNumber": "HLTX01_SYS"
                    },
                    "FEXCHANGERATE": 1,
                    "FPrice": 0,
                    "FQty": 0,
                    "FAMOUNTFOR": "-2.15",
                    "FCREDIT": "-2.15",
                    "FEXPORTENTRYID": 0
                }]
            }]
        }""";

    private KingdeePayableBillSuccessConstant() {
        throw new IllegalStateException("Utility class");
    }


}
