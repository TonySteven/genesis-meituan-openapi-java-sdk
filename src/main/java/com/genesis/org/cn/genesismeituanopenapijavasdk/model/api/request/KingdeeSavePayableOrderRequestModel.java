package com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 金蝶生成应付单 Model 入参
 *
 * @author steven
 * &#064;date  2023/10/29
 */
@Data
public class KingdeeSavePayableOrderRequestModel {

    /**
     * fbill type id
     */
    private BaseFNumberUppercase FBillTypeID;

    /**
     * fbill no
     */
    private String FBillNo;

    /**
     * fisinit
     */
    private String FISINIT;

    /**
     * fdate
     */
    private String FDATE;

    /**
     * fenddateh
     */
    private String FENDDATEH;

    /**
     * fdocumentstatus
     */
    private String FDOCUMENTSTATUS;

    /**
     * fsupplierid
     */
    private BaseFNumber FSUPPLIERID;

    /**
     * fcurrencyid
     */
    private BaseFNumber FCURRENCYID;

    /**
     * fpay conditon
     */
    private BaseFNumber FPayConditon;

    /**
     * fispriceexcludetax
     */
    private String FISPRICEEXCLUDETAX;

    /**
     * fsource bill type
     */
    private String FSourceBillType;

    /**
     * fbusinesstype
     */
    private String FBUSINESSTYPE;

    /**
     * fistax
     */
    private String FISTAX;

    /**
     * fsettleorgid
     */
    private BaseFNumber FSETTLEORGID;

    /**
     * fpayorgid
     */
    private BaseFNumber FPAYORGID;

    /**
     * fset account type
     */
    private String FSetAccountType;

    /**
     * fistaxincost
     */
    private String FISTAXINCOST;

    /**
     * fapremark
     */
    private String FAPRemark;

    /**
     * fishook match
     */
    private String FISHookMatch;

    /**
     * faccountsystem
     */
    private BaseFNumber FACCOUNTSYSTEM;

    /**
     * fpurchasedeptid
     */
    private BaseFNumber FPURCHASEDEPTID;

    /**
     * fpurchasergroupid
     */
    private BaseFNumber FPURCHASERGROUPID;

    /**
     * fpurchaserid
     */
    private BaseFNumber FPURCHASERID;

    /**
     * fcancel status
     */
    private String FCancelStatus;

    /**
     * fisbyiv
     */
    private String FISBYIV;

    /**
     * fisgenhsadj
     */
    private String FISGENHSADJ;

    /**
     * fmatch method id
     */
    private int FMatchMethodID;

    /**
     * fisinvoicearlier
     */
    private String FISINVOICEARLIER;

    /**
     * fscan point
     */
    private BaseFNumberUppercase FScanPoint;

    /**
     * fbillmatchlogid
     */
    private int FBILLMATCHLOGID;

    /**
     * fwbopenqty
     */
    private String FWBOPENQTY;

    /**
     * fpresetbase1
     */
    private BaseFNumberUppercase FPRESETBASE1;

    /**
     * fpresetbase2
     */
    private BaseFNumberUppercase FPRESETBASE2;

    /**
     * fpresettext1
     */
    private String FPRESETTEXT1;

    /**
     * fpresettext2
     */
    private String FPRESETTEXT2;

    /**
     * fpresetassistant2
     */
    private BaseFNumber FPRESETASSISTANT2;

    /**
     * fpresetassistant1
     */
    private BaseFNumber FPRESETASSISTANT1;

    /**
     * fis generate plan by cost item
     */
    private String FIsGeneratePlanByCostItem;

    /**
     * fscpconfirmerid
     */
    private BaseFUserID FSCPCONFIRMERID;

    /**
     * fscpconfirmdate
     */
    private Date FSCPCONFIRMDATE;

    /**
     * forder discount amount for
     */
    private int FOrderDiscountAmountFor;

    /**
     * fsub head suppiler
     */
    private FsubHeadSuppiler FsubHeadSuppiler;

    /**
     * fsub head finc
     */
    private FsubHeadFinc FsubHeadFinc;

    /**
     * fentity detail
     */
    private List<FEntityDetail> FEntityDetail;

    /**
     * fentity plan
     */
    private List<FEntityPlan> FEntityPlan;

    /**
     * frec inv info
     */
    private List<FRecInvInfo> FRecInvInfo;

}
