package com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
public class FEntityDetail {

    private int FEntryID;
    private BaseFNumber FCOSTID;
    private BaseFNumber FMATERIALID;
    private String FMaterialDesc;
    private BaseFNumber FASSETID;
    private BaseFNumber FPRICEUNITID;
    private int FPrice;
    private int FPriceQty;
    private int FTaxPrice;
    private int FPriceWithTax;
    private int FEntryTaxRate;
    private BaseFNumber FTaxCombination;
    private int FEntryDiscountRate;
    private String FSourceBillNo;
    private String FSOURCETYPE;
    private int FDISCOUNTAMOUNTFOR;
    private int FNoTaxAmountForD;
    private int FTAXAMOUNTFORD;
    private int FALLAMOUNTFORD;
    private String FINCLUDECOST;
    private String FINCLUDECONTRACTCOMPCOST;
    private List<BaseFNumber> FOUTSTOCKID;
    private BaseFNumber FAUXPROPID;
    private int FBUYIVQTY;
    private int FIVALLAMOUNTFOR;
    private String FISOUTSTOCK;
    private BaseFNumber FCOSTDEPARTMENTID;
    private BaseFNumber FLot;
    private String FMONUMBER;
    private int FMOENTRYSEQ;
    private String FOPNO;
    private String FSEQNUMBER;
    private int FOPERNUMBER;
    private BaseFNumber FPROCESSID;
    private BaseFNumber FFPRODEPARTMENTID;
    private String FWWINTYPE;
    private String FIsFree;
    private BaseFNumber FStockUnitId;
    private int FStockQty;
    private int FStockBaseQty;
    private int FPriceBaseDen;
    private int FStockBaseNum;
    private int FOrderEntryID;
    private int FORDERENTRYSEQ;
    private int FBUYIVINIQTY;
    private int FBUYIVINIBASICQTY;
    private int FPushRedQty;
    private int FIVINIALLAMOUNTFOR;
    private int FDIFFAMOUNTEXRATE;
    private int FDIFFALLAMOUNTEXRATE;
    private int FNORECEIVEAMOUNT;
    private int FSRCROWID;
    private int FNOINVOICEAMOUNT;
    private int FNOINVOICEQTY;
    private String FROOTSETACCOUNTTYPE;
    private String FROOTSOURCETYPE;
    private String FTAILDIFFFLAG;
    private int FDIFFAMOUNT;
    private int FDIFFALLAMOUNT;
    /**
     * 不含税金额
     */
    private String FNoTaxAmountFor_D;

    /**
     * 税额
     */
    private String FTAXAMOUNTFOR_D;

    /**
     * 价税合计
     */
    private String FALLAMOUNTFOR_D;
    private BaseFNumberUppercase FPRESETENTRYBASE1;
    private BaseFNumberUppercase FPRESETENTRYBASE2;
    private String FPRESETENTRYTEXT1;
    private String FPRESETENTRYTEXT2;
    private List<FTaxDetailSubEntity> FTaxDetailSubEntity;


}
