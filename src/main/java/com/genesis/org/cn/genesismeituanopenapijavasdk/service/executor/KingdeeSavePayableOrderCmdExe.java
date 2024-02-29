package com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.IJdScmShopBillDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.IVoucherCalsstypeDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.JdScmShopBillEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.VoucherClassTypeEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.base.BaseVO;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request.*;
import com.google.gson.Gson;
import com.kingdee.bos.webapi.entity.RepoRet;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.jsoup.helper.Validate.fail;

/**
 * 金蝶SaaS-调用保存应付单具体逻辑.
 *
 * @author steven
 * &#064;date  2023/10/29
 */
@Service
@Slf4j
public class KingdeeSavePayableOrderCmdExe {


    @Resource
    private IJdScmShopBillDao iJdScmShopBillDao;

    @Resource
    private IVoucherCalsstypeDao iVoucherCalsstypeDao;

    /**
     * execute
     * 执行
     *
     * @return {@link BaseVO}
     */
    @SneakyThrows
    public BaseVO execute() {
        // 打印日志 - 开始.
        log.info("KingdeeSavePayableOrderCmdExe.execute() start");

        // 读取配置，初始化SDK
        K3CloudApi client = new K3CloudApi();

        // 拼接入参对象.
        // KingdeeSavePayableOrderRequest kingdeeSavePayableOrderRequest = getKingdeeSavePayableOrderRequest();
        // kingdeeSavePayableOrderRequest转jsonString.
        // String jsonData = new Gson().toJson(kingdeeSavePayableOrderRequest);
        // String jsonData = "{\"NeedUpDateFields\":[],\"NeedReturnFields\":[],\"IsDeleteEntry\":\"true\",\"SubSystemId\":\"\",\"IsVerifyBaseDataField\":\"false\",\"IsEntryBatchFill\":\"true\",\"ValidateFlag\":\"true\",\"NumberSearch\":\"true\",\"IsAutoAdjustField\":\"false\",\"InterationFlags\":\"\",\"IgnoreInterationFlag\":\"\",\"IsControlPrecision\":\"false\",\"ValidateRepeatJson\":\"false\",\"Model\":{\"FID\":0,\"FBillTypeID\":{\"FNUMBER\":\"YFD02_SYS\"},\"FBillNo\":\"\",\"FISINIT\":\"false\",\"FDATE\":\"2024-01-01\",\"FENDDATE_H\":\"2024-01-01\",\"FDOCUMENTSTATUS\":\"\",\"FSUPPLIERID\":{\"FNumber\":\"100001\"},\"FCURRENCYID\":{\"FNumber\":\"PRE001\"},\"FPayConditon\":{\"FNumber\":\"\"},\"FISPRICEEXCLUDETAX\":\"false\",\"FSourceBillType\":\"\",\"FBUSINESSTYPE\":\"费用应付单\",\"FISTAX\":\"false\",\"FSETTLEORGID\":{\"FNumber\":\"21210002\"},\"FPAYORGID\":{\"FNumber\":\"21210002\"},\"FSetAccountType\":\"业务应付\",\"FISTAXINCOST\":\"false\",\"FAP_Remark\":\"货款备注\",\"FISHookMatch\":\"false\",\"FACCOUNTSYSTEM\":{\"FNumber\":\"\"},\"FPURCHASEDEPTID\":{\"FNumber\":\"\"},\"FPURCHASERGROUPID\":{\"FNumber\":\"\"},\"FPURCHASERID\":{\"FNumber\":\"\"},\"FCancelStatus\":\"A\",\"FISBYIV\":\"false\",\"FISGENHSADJ\":\"false\",\"FMatchMethodID\":0,\"FISINVOICEARLIER\":\"false\",\"FScanPoint\":{\"FNUMBER\":\"\"},\"FBILLMATCHLOGID\":0,\"FWBOPENQTY\":\"false\",\"FPRESETBASE1\":{\"FNUMBER\":\"\"},\"FPRESETBASE2\":{\"FNUMBER\":\"\"},\"FPRESETTEXT1\":\"\",\"FPRESETTEXT2\":\"\",\"FPRESETASSISTANT2\":{\"FNumber\":\"\"},\"FPRESETASSISTANT1\":{\"FNumber\":\"\"},\"FIsGeneratePlanByCostItem\":\"false\",\"FSCPCONFIRMERID\":{\"FUserID\":\"\"},\"FSCPCONFIRMDATE\":\"2024-01-01\",\"FOrderDiscountAmountFor\":0,\"FsubHeadSuppiler\":{\"FEntryId\":0,\"FORDERID\":{\"FNumber\":\"\"},\"FTRANSFERID\":{\"FNumber\":\"\"},\"FChargeId\":{\"FNumber\":\"\"}},\"FsubHeadFinc\":{\"FEntryId\":0,\"FACCNTTIMEJUDGETIME\":\"2024-01-01\",\"FSettleTypeID\":{\"FNumber\":\"\"},\"FMAINBOOKSTDCURRID\":{\"FNumber\":\"PRE001\"},\"FEXCHANGETYPE\":{\"FNumber\":\"HLTX01_SYS\"},\"FExchangeRate\":1,\"FTaxAmountFor\":0,\"FNoTaxAmountFor\":0,\"FISCARRIEDDATE\":\"false\"},\"FEntityDetail\":[{\"FEntryID\":0,\"FCOSTID\":{\"FNumber\":\"CI018\"},\"FMATERIALID\":{\"FNumber\":\"140301\"},\"FMaterialDesc\":\"\",\"FASSETID\":{\"FNUMBER\":\"\"},\"FPRICEUNITID\":{\"FNumber\":\"\"},\"FPrice\":0,\"FPriceQty\":0,\"FTaxPrice\":0,\"FPriceWithTax\":0,\"FEntryTaxRate\":0,\"FTaxCombination\":{\"FNumber\":\"\"},\"FEntryDiscountRate\":0,\"FSourceBillNo\":\"\",\"FSOURCETYPE\":\"\",\"FDISCOUNTAMOUNTFOR\":0,\"FNoTaxAmountFor_D\":50767.50,\"FTAXAMOUNTFOR_D\":0,\"FALLAMOUNTFOR_D\":50767.50,\"FINCLUDECOST\":\"false\",\"FINCLUDECONTRACTCOMPCOST\":\"false\",\"FOUTSTOCKID\":[{\"FNumber\":\"\"}],\"FAUXPROPID\":{},\"FBUYIVQTY\":0,\"FIVALLAMOUNTFOR\":0,\"FISOUTSTOCK\":\"false\",\"FCOSTDEPARTMENTID\":{\"FNUMBER\":\"\"},\"FLot\":{\"FNumber\":\"\"},\"FMONUMBER\":\"\",\"FMOENTRYSEQ\":0,\"FOPNO\":\"\",\"FSEQNUMBER\":\"\",\"FOPERNUMBER\":0,\"FPROCESSID\":{\"FNUMBER\":\"\"},\"FFPRODEPARTMENTID\":{\"FNUMBER\":\"\"},\"FWWINTYPE\":\"\",\"FIsFree\":\"false\",\"FStockUnitId\":{\"FNumber\":\"\"},\"FStockQty\":0,\"FStockBaseQty\":0,\"FPriceBaseDen\":0,\"FStockBaseNum\":0,\"FOrderEntryID\":0,\"FORDERENTRYSEQ\":0,\"FBUYIVINIQTY\":0,\"FBUYIVINIBASICQTY\":0,\"FPushRedQty\":0,\"FIVINIALLAMOUNTFOR\":0,\"FDIFFAMOUNTEXRATE\":0,\"FDIFFALLAMOUNTEXRATE\":0,\"FNORECEIVEAMOUNT\":0,\"FSRCROWID\":0,\"FNOINVOICEAMOUNT\":0,\"FNOINVOICEQTY\":0,\"FROOTSETACCOUNTTYPE\":\"\",\"FROOTSOURCETYPE\":\"\",\"FTAILDIFFFLAG\":\"false\",\"FDIFFAMOUNT\":0,\"FDIFFALLAMOUNT\":0,\"FPRESETENTRYBASE1\":{\"FNUMBER\":\"\"},\"FPRESETENTRYBASE2\":{\"FNUMBER\":\"\"},\"FPRESETENTRYTEXT1\":\"\",\"FPRESETENTRYTEXT2\":\"\",\"FTaxDetailSubEntity\":[{\"FDetailID\":0,\"FTaxRateId\":{\"FNumber\":\"\"},\"FTaxRate\":0,\"FTaxAmount_T\":0,\"FCostPercent\":0,\"FCostAmount\":0,\"FVAT\":\"false\",\"FSellerWithholding\":\"false\",\"FBuyerWithholding\":\"false\"}]}],\"FEntityPlan\":[{\"FEntryID\":0,\"FENDDATE\":\"2024-01-01\",\"FPAYAMOUNTFOR\":0,\"FPAYRATE\":0,\"FPURCHASEORDERID\":0,\"FPAYABLEENTRYID\":0,\"FMATERIALID_P\":{\"FNUMBER\":\"\"},\"FPRICEUNITID_P\":{\"FNumber\":\"\"},\"FPRICE_P\":0,\"FQTY_P\":0,\"FPURCHASEORDERNO\":\"\",\"FMATERIALSEQ\":0,\"FRELATEHADPAYQTY\":0,\"FNOTVERIFICATEAMOUNT\":0,\"FCOSTID_P\":{\"FNUMBER\":\"\"},\"FREMARK\":\"\",\"FPRESETENTRYBASE_P1\":{\"FNUMBER\":\"\"},\"FPRESETENTRYBASE_P2\":{\"FNUMBER\":\"\"},\"FPRESETENTRYTEXT_P1\":\"\",\"FPRESETENTRYTEXT_P2\":\"\",\"FCOSTDEPARTMENTID_P\":{\"FNUMBER\":\"\"}}],\"FRecInvInfo\":[{\"FEntryID\":0}]}}";
        String jsonData = "{\n" +
            "        \"Model\": {\n" +
            "                \"FID\": 0,\n" +
            "                \"FBillTypeID\": {\n" +
            "                        \"FNUMBER\": \"YFD02_SYS\"\n" +
            "                },\n" +
            "                \"FBillNo\": \"\",\n" +
            "                \"FISINIT\": \"false\",\n" +
            "                \"FDATE\": \"2024-01-18\",\n" +
            "                \"FENDDATE_H\": \"2024-01-18\",\n" +
            "                \"FDOCUMENTSTATUS\": \"\",\n" +
            "                \"FSUPPLIERID\": {\n" +
            "                        \"FNumber\": \"100001\"\n" +
            "                },\n" +
            "                \"FCURRENCYID\": {\n" +
            "                        \"FNumber\": \"PRE001\"\n" +
            "                },\n" +
            "                \"FPayConditon\": {\n" +
            "                        \"FNumber\": \"\"\n" +
            "                },\n" +
            "                \"FISPRICEEXCLUDETAX\": \"false\",\n" +
            "                \"FSourceBillType\": \"\",\n" +
            "                \"FBUSINESSTYPE\": \"FY\",\n" +
            "                \"FISTAX\": \"false\",\n" +
            "                \"FSETTLEORGID\": {\n" +
            "                        \"FNumber\": \"21210002\"\n" +
            "                },\n" +
            "                \"FPAYORGID\": {\n" +
            "                        \"FNumber\": \"21210002\"\n" +
            "                },\n" +
            "                \"FSetAccountType\": \"1\",\n" +
            "                \"FISTAXINCOST\": \"false\",\n" +
            "                \"FAP_Remark\": \"货款备注\",\n" +
            "                \"FISHookMatch\": \"false\",\n" +
            "                \"FCancelStatus\": \"A\",\n" +
            "                \"FMatchMethodID\": 0,\n" +
            "                \"FBILLMATCHLOGID\": 0,\n" +
            "                \"FWBOPENQTY\": \"false\",\n" +
            "                \"FIsGeneratePlanByCostItem\": \"false\",\n" +
            "                \"FSCPCONFIRMDATE\": \"2024-01-18\",\n" +
            "                \"FOrderDiscountAmountFor\": 0,\n" +
            "                \"FsubHeadFinc\": {\n" +
            "                        \"FEntryId\": 0,\n" +
            "                        \"FACCNTTIMEJUDGETIME\": \"2024-01-18\",\n" +
            "                        \"FSettleTypeID\": {\n" +
            "                                \"FNumber\": \"\"\n" +
            "                        },\n" +
            "                        \"FMAINBOOKSTDCURRID\": {\n" +
            "                                \"FNumber\": \"PRE001\"\n" +
            "                        },\n" +
            "                        \"FEXCHANGETYPE\": {\n" +
            "                                \"FNumber\": \"HLTX01_SYS\"\n" +
            "                        },\n" +
            "                        \"FExchangeRate\": 1,\n" +
            "                        \"FTaxAmountFor\": 0,\n" +
            "                        \"FNoTaxAmountFor\": 0,\n" +
            "                        \"FISCARRIEDDATE\": \"false\"\n" +
            "                },\n" +
            "                \"FEntityDetail\": [{\n" +
            "                        \"FEntryID\": 0,\n" +
            "                        \"FCOSTID\": {\n" +
            "                                \"FNumber\": \"CI018\"\n" +
            "                        },\n" +
            "                        \"FMATERIALID\": {\n" +
            "                                \"FNumber\": \"140301\"\n" +
            "                        },\n" +
            "                        \"FMaterialDesc\": \"\",\n" +
            "                        \"FASSETID\": {\n" +
            "                                \"FNUMBER\": \"\"\n" +
            "                        },\n" +
            "                        \"FPRICEUNITID\": {\n" +
            "                                \"FNumber\": \"\"\n" +
            "                        },\n" +
            "                        \"FPrice\": 0,\n" +
            "                        \"FPriceQty\": 0,\n" +
            "                        \"FTaxPrice\": 0,\n" +
            "                        \"FPriceWithTax\": 0,\n" +
            "                        \"FEntryTaxRate\": 13,\n" +
            "                        \"FTaxCombination\": {\n" +
            "                                \"FNumber\": \"\"\n" +
            "                        },\n" +
            "                        \"FEntryDiscountRate\": 0,\n" +
            "                        \"FSourceBillNo\": \"\",\n" +
            "                        \"FSOURCETYPE\": \"\",\n" +
            "                        \"FDISCOUNTAMOUNTFOR\": 0,\n" +
            "                        \"FALLAMOUNTFOR_D\": 50767.50\n" +
            "                }]\n" +
            "        }\n" +
            "}";

        try {
            // 业务对象标识
            String formId = "AP_Payable";
            // 调用接口
            String resultJson = client.save(formId, jsonData);

            // 用于记录结果
            Gson gson = new Gson();
            // 对返回结果进行解析和校验
            RepoRet repoRet = gson.fromJson(resultJson, RepoRet.class);
            if (repoRet.getResult().getResponseStatus().isIsSuccess()) {
                log.info("接口返回结果: {}", gson.toJson(repoRet.getResult()));
            } else {
                fail("接口返回结果: " + gson.toJson(repoRet.getResult().getResponseStatus()));
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }

        // 返回参数.
        return BaseVO.builder()
            .id("1")
            .state("success")
            .msg("成功")
            .build();
    }

    /**
     * get kingdee save payable order request
     * 拼接KingdeeSavePayableOrderRequest对象方法
     *
     * @return {@link KingdeeSavePayableOrderRequest}
     */
    private KingdeeSavePayableOrderRequest getKingdeeSavePayableOrderRequest() {

        // 1. 读取数据库
        // List<JdScmShopBillEntity> JdScmShopBillEntityList = iJdScmShopBillDao.list();
        QueryWrapper<JdScmShopBillEntity> queryWrapper = new QueryWrapper<>();
        // BillType In ('门店自采入库','门店统配入库','店间调入')
        queryWrapper.in("BillType", "门店自采入库", "门店统配入库");
        List<JdScmShopBillEntity> jdScmShopBillEntities = iJdScmShopBillDao.list(queryWrapper);
        JdScmShopBillEntity jdScmShopBillEntity = jdScmShopBillEntities.get(0);

        // 2. 拼接参数
        List<VoucherClassTypeEntity> voucherClassTypeEntities = iVoucherCalsstypeDao.list();
        // 根据voucherClassTypeEntities, 获取Map<ItemBigClassCode,VoucherClassTypeEntity>对象
        Map<String, VoucherClassTypeEntity> stringVoucherClassTypeEntityMap = voucherClassTypeEntities.stream().collect(
            Collectors.toMap(VoucherClassTypeEntity::getItemBigClassCode, Function.identity()));

        // 2.1 大类

        // 2.2 业务时间 格式化为YYYY-MM-DD字符串
        Date busDate = jdScmShopBillEntity.getBusDate();
        // 创建SimpleDateFormat对象，指定日期格式为YYYY-MM-DD
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // 使用SimpleDateFormat格式化Date对象
        String formattedBusinessDate = dateFormat.format(busDate);

        // 2.3 供应商code
        String otherSideCode = jdScmShopBillEntity.getOtherSideCode();
        // 2.4 门店编码
        String shopCode = jdScmShopBillEntity.getShopCode();
        // 2.5 备注 付+供应商名称+货款
        // 供应商名称
        String otherSideName = jdScmShopBillEntity.getOtherSideName();
        String remark = "付" + otherSideName + "货款";

        // 2.6 不含税金额 totalStoreMoney 保留两位小数
        String totalStoreMoney = jdScmShopBillEntity.getTotalstoremoney()
            .setScale(2, RoundingMode.HALF_UP).toString();
        // 2.7 税额 totalTaxMoney 保留两位小数
        String totalTaxMoney = jdScmShopBillEntity.getTotalTaxMoney()
            .setScale(2, RoundingMode.HALF_UP).toString();
        // 2.8 价税合计 totalIncludeTaxMoney 保留两位小数
        String totalIncludeTaxMoney = jdScmShopBillEntity.getTotalIncludeTaxMoney()
            .setScale(2, RoundingMode.HALF_UP).toString();

        // 2.9 获取billTypeCode
        String billTypeCode = stringVoucherClassTypeEntityMap.get(jdScmShopBillEntity.getItemBigClassCode())
            .getBilltypecode();
        // 2.10 获取bill type
        String billType = stringVoucherClassTypeEntityMap.get(jdScmShopBillEntity.getItemBigClassCode())
            .getBilltype();
        // 2.11 获取类别编码 voucher_calsstype.classCode
        String classCode = stringVoucherClassTypeEntityMap.get(jdScmShopBillEntity.getItemBigClassCode())
            .getClasscode();


        // 3. 返回参数
        KingdeeSavePayableOrderRequestModel model = KingdeeSavePayableOrderRequestModel.builder()
            // 单据类型编码
            .FBillTypeID(BaseFNumberUppercase.builder().FNUMBER(billTypeCode).build())
            .FDATE(formattedBusinessDate)
            // 到期日
            .FENDDATE_H(formattedBusinessDate)
            // 单据状态
            .FDOCUMENTSTATUS("")
            // 供应商id
            .FSUPPLIERID(BaseFNumber.builder().FNumber(otherSideCode).build())
            // 币别
            .FCURRENCYID(BaseFNumber.builder().FNumber("PRE001").build())
            .FMAINBOOKSTDCURRID(BaseFNumber.builder().FNumber("PRE001").build())
            // FExchangeRate
            .FExchangeRate("1")
            // 业务类型
            .FBUSINESSTYPE(billType)
            // 结算组织编码
            .FSETTLEORGID(BaseFNumber.builder().FNumber(shopCode).build())
            // 付款组织编码
            .FPAYORGID(BaseFNumber.builder().FNumber(shopCode).build())
            // 作废状态
            .FCancelStatus("A")
            // 备注 付【供应商名称】货款
            .FAP_Remark(remark)
            // 立账类型
            .FSetAccountType("业务应付")
            // 物料list 单例
            .FEntityDetail(List.of(FEntityDetail.builder()
                // 费用项目编码
                .FCOSTID(BaseFNumber.builder().FNumber(classCode).build())
                // 不含税金额
                .FNoTaxAmountFor_D(totalStoreMoney)
                // 税额
                .FTAXAMOUNTFOR_D(totalTaxMoney)
                // 价税合计
                .FALLAMOUNTFOR_D(totalIncludeTaxMoney)
                .build()))
            .build();
        return KingdeeSavePayableOrderRequest.builder()
            .Model(model)
            .build();
    }
}
