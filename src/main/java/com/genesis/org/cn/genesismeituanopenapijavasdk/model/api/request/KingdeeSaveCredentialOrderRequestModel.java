package com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 金蝶生成凭证单 Model 入参
 *
 * @author steven
 * &#064;date  2023/10/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KingdeeSaveCredentialOrderRequestModel {

    /**
     * fvoucherid
     */
    private int FVOUCHERID;


    /**
     * faccount book id
     */
    private BaseFNumber FAccountBookID;

    /**
     * fdate
     */
    private String FDate;

    /**
     * fbusdate
     */
    private String FBUSDATE;

    /**
     * fyear
     */
    private int FYEAR;

    /**
     * fperiod
     */
    private int FPERIOD;

    /**
     * fvouchergroupid
     */
    private BaseFNumber FVOUCHERGROUPID;

    /**
     * fvouchergroupno
     */
    private String FVOUCHERGROUPNO;

    /**
     * fattachments
     */
    private int FATTACHMENTS;

    /**
     * fisadjustvoucher
     */
    private String FISADJUSTVOUCHER;

    /**
     * fsource bill key
     */
    private BaseFNumber FSourceBillKey;

    /**
     * fimportversion
     */
    private String FIMPORTVERSION;

    /**
     * fdocument status
     */
    private String FDocumentStatus;

    /**
     * fentity
     */
    private List<KingdeeSaveCredentialOrderFEntity> FEntity;

}
