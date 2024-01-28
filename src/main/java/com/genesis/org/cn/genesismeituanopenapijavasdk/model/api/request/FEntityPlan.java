package com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request;

import lombok.Data;

/**
 * 金蝶生成应付单入参
 *
 * @author steven
 * &#064;date  2023/10/29
 */
@Data
public class FEntityPlan {

    private int FEntryID;
    private String FENDDATE;
    private int FPAYAMOUNTFOR;
    private int FPAYRATE;
    private int FPURCHASEORDERID;
    private int FPAYABLEENTRYID;
    private BaseFNumber FMATERIALIDP;
    private BaseFNumberUppercase FPRICEUNITIDP;
    private int FPRICEP;
    private int FQTYP;
    private String FPURCHASEORDERNO;
    private int FMATERIALSEQ;
    private int FRELATEHADPAYQTY;
    private int FNOTVERIFICATEAMOUNT;
    private BaseFNumberUppercase FCOSTIDP;
    private String FREMARK;
    private BaseFNumberUppercase FPRESETENTRYBASEP1;
    private BaseFNumberUppercase FPRESETENTRYBASEP2;
    private String FPRESETENTRYTEXTP1;
    private String FPRESETENTRYTEXTP2;
    private BaseFNumberUppercase FCOSTDEPARTMENTIDP;


}
