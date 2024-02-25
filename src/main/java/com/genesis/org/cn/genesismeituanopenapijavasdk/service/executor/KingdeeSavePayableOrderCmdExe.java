package com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.IJdScmShopBillDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.JdScmShopBillEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.base.BaseVO;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request.BaseFNumber;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request.BaseFNumberUppercase;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request.KingdeeSavePayableOrderRequest;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request.KingdeeSavePayableOrderRequestModel;
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
        KingdeeSavePayableOrderRequest kingdeeSavePayableOrderRequest = getKingdeeSavePayableOrderRequest();
        // kingdeeSavePayableOrderRequest转jsonString.
        String jsonData = new Gson().toJson(kingdeeSavePayableOrderRequest);

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
        queryWrapper.in("BillType", "门店自采入库", "门店统配入库", "店间调入");
        JdScmShopBillEntity jdScmShopBillEntity = iJdScmShopBillDao.getOne(queryWrapper);

        // 2. 拼接参数
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


        // 3. 返回参数
        KingdeeSavePayableOrderRequestModel model = KingdeeSavePayableOrderRequestModel.builder()
            // 单据类型编码
            .FBillTypeID(BaseFNumberUppercase.builder().FNUMBER("YFD01_SYS").build())
            .FDATE(formattedBusinessDate)
            // 到期日
            .FENDDATE_H(formattedBusinessDate)
            // 单据状态
            .FDOCUMENTSTATUS("")
            // 供应商id
            .FSUPPLIERID(BaseFNumber.builder().FNumber(otherSideCode).build())
            // 币别
            .FCURRENCYID(BaseFNumber.builder().FNumber("").build())
            // 业务类型
            .FBUSINESSTYPE("TODO")
            // 结算组织编码
            .FSETTLEORGID(BaseFNumber.builder().FNumber(shopCode).build())
            // 付款组织编码
            .FPAYORGID(BaseFNumber.builder().FNumber(shopCode).build())
            // 作废状态
            .FCancelStatus("")
            // 备注 付【供应商名称】货款
            .FAP_Remark(remark)
            // 立账类型
            .FSetAccountType("业务应付")
            // 费用项目编码
            .FCOSTID(BaseFNumber.builder().FNumber("TODO").build())
            // 不含税金额
            .FNoTaxAmountFor_D(totalStoreMoney)
            // 税额
            .FTAXAMOUNTFOR_D(totalTaxMoney)
            // 价税合计
            .FALLAMOUNTFOR_D(totalIncludeTaxMoney)
            .build();
        return KingdeeSavePayableOrderRequest.builder()
            .Model(model)
            .build();
    }
}
