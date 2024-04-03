package com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.IJdScmShopBillPzDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.IKingdeeCredentialBillAuxiliaryCalledDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.IVoucherGroupingVoucherAccountingEntryDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.JdScmShopBillPzEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.KingdeeCredentialBillAuxiliaryCalledEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.VoucherGroupingVoucherAccountingEntryEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.base.BaseVO;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request.*;
import com.google.gson.Gson;
import com.kingdee.bos.webapi.entity.RepoError;
import com.kingdee.bos.webapi.entity.RepoRet;
import com.kingdee.bos.webapi.entity.SuccessEntity;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 金蝶SaaS-调用保存凭证具体逻辑.
 *
 * @author steven
 * &#064;date  2023/10/29
 */
@Service
@Slf4j
public class KingdeeSaveCredentialOrderCmdExe {

    // 初始化system 静态变量
    private static final String SYSTEM = "system";

    @Resource
    private IJdScmShopBillPzDao iJdScmShopBillPzDao;

    @Resource
    private IKingdeeCredentialBillAuxiliaryCalledDao iKingdeeCredentialBillAuxiliaryCalledDao;

    @Resource
    private IVoucherGroupingVoucherAccountingEntryDao iVoucherGroupingVoucherAccountingEntryDao;

    @Resource
    private KingdeeSaveCashCredentialOrderCmdExe kingdeeSaveCashCredentialOrderCmdExe;


    /**
     * execute
     * 执行
     *
     * @return {@link BaseVO}
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public BaseVO execute(KingdeeCredentialBillCalledCmd cmd) {
        // 打印日志 - 开始.
        log.info("KingdeeSaveCredentialOrderCmdExe.execute() start");

        // 读取配置，初始化SDK
        K3CloudApi client = new K3CloudApi();

        // 拼接入参对象.
        // 1. 获取 jdScmShopBillList
        List<JdScmShopBillPzEntity> jdScmShopBillList = getJdScmShopBillList(cmd);
        // 2.1 . 获取kingdee_bill_auxiliary_called已经回调成功的数据,并去除已经回调成功的数据的id
        List<KingdeeCredentialBillAuxiliaryCalledEntity> kingdeeBillAuxiliaryCalledSuccessEntities
            = getKingdeeBillAuxiliaryCalledSuccessEntities();
        // jdScmShopBillList和kingdeeBillAuxiliaryCalledSuccessEntities id 取差集,就是还没有回调成功的数据.
        jdScmShopBillList = jdScmShopBillList.stream()
            .filter(jdScmShopBillPzEntity -> kingdeeBillAuxiliaryCalledSuccessEntities.stream()
                .noneMatch(kingdeeBillAuxiliaryCalledEntity -> kingdeeBillAuxiliaryCalledEntity.getId()
                    .equals(jdScmShopBillPzEntity.getId())))
            .toList();
        // jdScmShopBillList根据门店进行分组,并遍历调用保存应付单接口
        Map<String, List<JdScmShopBillPzEntity>> collect = jdScmShopBillList.stream()
            .collect(Collectors.groupingBy(JdScmShopBillPzEntity::getShopName));
        // 查询VoucherGroupingVoucherAccountingEntryEntity List
        List<VoucherGroupingVoucherAccountingEntryEntity> voucherGroupingVoucherAccountingEntryEntities
            = iVoucherGroupingVoucherAccountingEntryDao.list();
        // voucherGroupingVoucherAccountingEntryEntities 根据item_type_name进行分组
        // 如果getItemTypeName为null,则跳过.
        Map<String, List<VoucherGroupingVoucherAccountingEntryEntity>> voucherGroupingVoucherAccountingEntryEntityMap
            = voucherGroupingVoucherAccountingEntryEntities.stream()
            .filter(voucherGroupingVoucherAccountingEntryEntity ->
                voucherGroupingVoucherAccountingEntryEntity.getItemTypeName() != null)
            .collect(Collectors.groupingBy(VoucherGroupingVoucherAccountingEntryEntity::getItemTypeName));


        for (Map.Entry<String, List<JdScmShopBillPzEntity>> entry : collect.entrySet()) {
            // 获取当前entry.getKey()的索引顺序值
            int index = collect.keySet().stream().toList().indexOf(entry.getKey()) + 1;
            // 打印日志 总共有多少条数据 现在处理第几条数据
            log.info("调用凭证-总共有{}条数据,现在处理第{}条数据", collect.size(), index);
            // 打印日志 此次处理的门店名称和门店应付单数量
            log.info("调用凭证-门店名称:{},门店应付单数量:{}", entry.getKey(), entry.getValue().size());

            // 2.3 获取KingdeeSaveCredentialOrderBatchRequest 批量保存入参对象
            KingdeeSaveCredentialOrderBatchRequest kingdeeSaveCredentialOrderBatchRequest =
                getKingdeeSaveCredentialOrderBatchRequest(entry.getValue()
                    , voucherGroupingVoucherAccountingEntryEntityMap);
            // 2.4 入参转jsonString.
            String jsonData = new Gson().toJson(kingdeeSaveCredentialOrderBatchRequest);
            // 测试成功json数据.
            // String jsonData = KingdeePayableBillSuccessConstant.PAYABLE_BILL_SUCCESS_JSON_DATA;
            // 2.5 业务对象标识
            String formId = "GL_VOUCHER";
            // 2.6 调用接口
            String resultJson = client.batchSave(formId, jsonData);
            // 2.7 用于记录结果
            Gson gson = new Gson();
            // 2.8 对返回结果进行解析和校验
            RepoRet repoRet = gson.fromJson(resultJson, RepoRet.class);
            // 2.9 保存成功后，更新数据库状态
            saveCallbackResult(entry.getValue(), repoRet);

            // 打印日志 - 结束. 完成处理第几条数据,还剩多少条数据
            log.info("完成处理第{}个店,还剩{}个门店数据待处理!", index, (collect.size() - index));
        }

        // 返回参数.
        return BaseVO.builder()
            .id("1")
            .state("success")
            .msg("成功")
            .build();
    }

    /**
     * save callback result
     * 保存回调结果
     *
     * @param jdScmShopBillList {@link List}<{@link JdScmShopBillPzEntity}>
     * @param repoRet           {@link RepoRet}
     */
    private void saveCallbackResult(List<JdScmShopBillPzEntity> jdScmShopBillList, RepoRet repoRet) {

        // 1. 如果repoRet.getResult().getResponseStatus().isIsSuccess()为true,则说明全是成功, 按照成功处理.
        if (repoRet.getResult().getResponseStatus().isIsSuccess()) {
            // 1. 调用buildSuccessEntities方法,
            // 获取List<KingdeeCredentialBillAuxiliaryCalledEntity> kingdeeCredentialBillAuxiliaryCalledEntities
            ArrayList<SuccessEntity> successEntities = repoRet.getResult().getResponseStatus().getSuccessEntitys();
            List<KingdeeCredentialBillAuxiliaryCalledEntity> kingdeeCredentialBillAuxiliaryCalledEntities
                = buildSuccessEntities(jdScmShopBillList, successEntities);
            // 2. 批量保存
            iKingdeeCredentialBillAuxiliaryCalledDao.saveOrUpdateBatch(kingdeeCredentialBillAuxiliaryCalledEntities);
        } else {
            // 说明有失败的, 按照失败处理. 保存失败结果
            // 1. 初始化List<KingdeeCredentialBillAuxiliaryCalledEntity> kingdeeCredentialBillAuxiliaryCalledEntities
            List<KingdeeCredentialBillAuxiliaryCalledEntity> kingdeeCredentialBillAuxiliaryCalledEntities = new ArrayList<>();
            // 2. 遍历repoRet.getResult().getResponseStatus().getSuccessEntities()
            ArrayList<SuccessEntity> successEntities = repoRet.getResult().getResponseStatus().getSuccessEntitys();
            // 如果successEntities不为空, 则保存成功的
            if (successEntities != null && !successEntities.isEmpty()) {
                // 调用buildSuccessEntities方法, 获取List<KingdeeCredentialBillAuxiliaryCalledEntity> kingdeeCredentialBillAuxiliaryCalledEntities
                List<KingdeeCredentialBillAuxiliaryCalledEntity> kingdeeBillAuxiliaryCalledSuccessEntities
                    = buildSuccessEntities(jdScmShopBillList, successEntities);
                // kingdeeBillAuxiliaryCalledSuccessEntities新增到kingdeeCredentialBillAuxiliaryCalledEntities
                kingdeeCredentialBillAuxiliaryCalledEntities.addAll(kingdeeBillAuxiliaryCalledSuccessEntities);
            }
            // 3. 获取失败的回调结果
            ArrayList<RepoError> errors = repoRet.getResult().getResponseStatus().getErrors();
            // 获取失败的回调结果
            List<KingdeeCredentialBillAuxiliaryCalledEntity> kingdeeBillAuxiliaryCalledFailedEntities
                = buildFailedEntities(jdScmShopBillList, errors);
            // kingdeeBillAuxiliaryCalledFailedEntities新增到kingdeeCredentialBillAuxiliaryCalledEntities
            kingdeeCredentialBillAuxiliaryCalledEntities.addAll(kingdeeBillAuxiliaryCalledFailedEntities);

            // 4. 保存kingdeeCredentialBillAuxiliaryCalledEntities
            iKingdeeCredentialBillAuxiliaryCalledDao.saveOrUpdateBatch(kingdeeCredentialBillAuxiliaryCalledEntities);
        }
    }

    /**
     * build success entities
     *
     * @param jdScmShopBillList jd scm shop bill list
     * @param successEntities   success entities
     * @return {@link List}<{@link KingdeeCredentialBillAuxiliaryCalledEntity}>
     */
    private List<KingdeeCredentialBillAuxiliaryCalledEntity> buildSuccessEntities(List<JdScmShopBillPzEntity> jdScmShopBillList
        , ArrayList<SuccessEntity> successEntities) {
        // 判断successEntities是否为空, 如果为空, 则返回空集合
        if (successEntities == null || successEntities.isEmpty()) {
            return new ArrayList<>();
        }
        // 1. 初始化List<KingdeeCredentialBillAuxiliaryCalledEntity> kingdeeCredentialBillAuxiliaryCalledEntities
        List<KingdeeCredentialBillAuxiliaryCalledEntity> kingdeeCredentialBillAuxiliaryCalledEntities = new ArrayList<>();
        for (SuccessEntity successEntity : successEntities) {
            // 获取successEntity的索引顺序值, 用于获取jdScmShopBillList中的jdScmShopBillPzEntity
            int index = successEntity.getDIndex();
            // 根据索引顺序值获取jdScmShopBillPzEntity
            JdScmShopBillPzEntity jdScmShopBillPzEntity = jdScmShopBillList.get(index);
            // 2. 拼接数据
            // 2.1 获取单据id
            String kingdeeBillId = successEntity.getId();
            // 2.2 获取单据number
            String kingdeeBillNumber = successEntity.getNumber();
            // 2.3 获取回调状态 成功 0失败1成功
            Integer status = 1;

            // 3.4 添加到kingdeeCredentialBillAuxiliaryCalledEntities
            kingdeeCredentialBillAuxiliaryCalledEntities.add(KingdeeCredentialBillAuxiliaryCalledEntity.builder()
                .id(jdScmShopBillPzEntity.getId())
                .companyId("dth")
                .jdScmShopBillNo(jdScmShopBillPzEntity.getId())
                .kingdeeBillId(kingdeeBillId)
                .kingdeeBillNumber(kingdeeBillNumber)
                .status(status)
                // 创建人
                .createBy(SYSTEM)
                // 创建时间
                .createTime(LocalDateTime.now())
                // 更新人
                .updateBy(SYSTEM)
                // 更新时间
                .updateTime(LocalDateTime.now())
                .build());
        }
        return kingdeeCredentialBillAuxiliaryCalledEntities;
    }

    /**
     * build failed entities
     *
     * @param jdScmShopBillList jd scm shop bill list
     * @param errors            errors
     * @return {@link List}<{@link KingdeeCredentialBillAuxiliaryCalledEntity}>
     */
    private List<KingdeeCredentialBillAuxiliaryCalledEntity> buildFailedEntities(List<JdScmShopBillPzEntity> jdScmShopBillList
        , ArrayList<RepoError> errors) {
        // 判断errors是否为空, 如果为空, 则返回空集合
        if (errors == null || errors.isEmpty()) {
            return new ArrayList<>();
        }
        // 1. 初始化List<KingdeeCredentialBillAuxiliaryCalledEntity> kingdeeCredentialBillAuxiliaryCalledEntities
        List<KingdeeCredentialBillAuxiliaryCalledEntity> kingdeeCredentialBillAuxiliaryCalledEntities = new ArrayList<>();
        // 2. 遍历repoRet.getResult().getResponseStatus().getSuccessEntities()
        // 3.2 遍历errors
        for (RepoError error : errors) {
            // 3.3 拼接数据
            // 获取errors的索引顺序值, 用于获取jdScmShopBillList中的jdScmShopBillPzEntity
            int index = error.getDIndex();
            // 根据索引顺序值获取jdScmShopBillPzEntity
            JdScmShopBillPzEntity jdScmShopBillPzEntity = jdScmShopBillList.get(index);
            // 3.3.3 获取回调状态 失败 0失败1成功
            Integer status = 0;
            // 3.3.4 获取错误信息 errorMsg = error.FieldName + error.Message
            String errorMsg = error.getFieldName() + error.getMessage();
            // 3.4 添加到kingdeeCredentialBillAuxiliaryCalledEntities
            kingdeeCredentialBillAuxiliaryCalledEntities.add(KingdeeCredentialBillAuxiliaryCalledEntity.builder()
                .id(jdScmShopBillPzEntity.getId())
                .companyId("dth")
                .jdScmShopBillNo(jdScmShopBillPzEntity.getId())
                .kingdeeBillErrorMsg(errorMsg)
                .status(status)
                // 创建人
                .createBy(SYSTEM)
                // 创建时间
                .createTime(LocalDateTime.now())
                // 更新人
                .updateBy(SYSTEM)
                // 更新时间
                .updateTime(LocalDateTime.now())
                .build());
        }
        return kingdeeCredentialBillAuxiliaryCalledEntities;
    }

    /**
     * get jd scm shop bill list
     *
     * @return {@link List}<{@link JdScmShopBillPzEntity}>
     */
    private List<JdScmShopBillPzEntity> getJdScmShopBillList(KingdeeCredentialBillCalledCmd cmd) {
        // 1. 读取数据库
        QueryWrapper<JdScmShopBillPzEntity> queryWrapper = new QueryWrapper<>();
        List<String> billTypeList = cmd.getBillTypeList();
        // 如果billType不为空, 则根据billType查询.否则查询全部
        if (CollUtil.isNotEmpty(billTypeList)) {
            queryWrapper.in("BillType", billTypeList);
        }
        // 获取cmd.门店名称
        List<String> shopNameList = cmd.getShopNameList();
        // 如果shopName不为空, 则根据shopName查询.否则查询全部
        if (CollUtil.isNotEmpty(shopNameList)) {
            queryWrapper.in("ShopName", shopNameList);
        }
        // 获取cmd的供应商code
        List<String> supplierCodeList = cmd.getSupplierCodeList();
        // 如果supplierCode不为空, 则根据supplierCode查询.否则查询全部
        if (CollUtil.isNotEmpty(supplierCodeList)) {
            queryWrapper.in("OtherSideCode", supplierCodeList);
        }
        return iJdScmShopBillPzDao.list(queryWrapper);
    }

    private List<KingdeeCredentialBillAuxiliaryCalledEntity> getKingdeeBillAuxiliaryCalledSuccessEntities() {
        // 1. 读取数据库
        QueryWrapper<KingdeeCredentialBillAuxiliaryCalledEntity> queryWrapper = new QueryWrapper<>();
        // status = 1
        queryWrapper.eq("status", 1);
        return iKingdeeCredentialBillAuxiliaryCalledDao.list(queryWrapper);
    }

    /**
     * get kingdee save payable order request
     * 拼接KingdeeSavePayableOrderRequest对象方法
     *
     * @return {@link KingdeeSavePayableOrderRequest}
     */
    private KingdeeSaveCredentialOrderBatchRequest getKingdeeSaveCredentialOrderBatchRequest(
        List<JdScmShopBillPzEntity> jdScmShopBillEntities
        , Map<String, List<VoucherGroupingVoucherAccountingEntryEntity>> voucherGroupingVoucherAccountingEntryEntityMap) {

        // 1. 初始化List<KingdeeSaveCredentialOrderRequestModel> Model
        List<KingdeeSaveCredentialOrderRequestModel> modelList = new ArrayList<>();

        // 遍历jdScmShopBillEntities
        for (JdScmShopBillPzEntity jdScmShopBillPzEntity : jdScmShopBillEntities) {
            KingdeeSaveCredentialOrderRequestModel kingdeeSaveCredentialOrderRequestModel =
                buildSingleModel(jdScmShopBillPzEntity, voucherGroupingVoucherAccountingEntryEntityMap);
            // 2. 添加到modelList
            modelList.add(kingdeeSaveCredentialOrderRequestModel);
        }

        // 3. 返回KingdeeSaveCredentialOrderBatchRequest对象
        return KingdeeSaveCredentialOrderBatchRequest.builder()
            .Model(modelList)
            .build();
    }

    /**
     * get total store money
     *
     * @param jdScmShopBillPzEntity                       jd scm shop bill pz entity
     * @param voucherGroupingVoucherAccountingEntryEntity voucher grouping voucher accounting entry entity
     * @return {@link BigDecimal}
     */
    @NotNull
    private static BigDecimal getTotalStoreMoney(JdScmShopBillPzEntity jdScmShopBillPzEntity, VoucherGroupingVoucherAccountingEntryEntity voucherGroupingVoucherAccountingEntryEntity) {
        String amount = voucherGroupingVoucherAccountingEntryEntity.getAmount();
        // 根据规则计算借方金额和贷方金额.
        // [totalIncludeTaxMoney]-[totalTaxMoney] 则是根据jdScmShopBillPzEntity.getTotalIncludeTaxMoney() - jdScmShopBillPzEntity.getTotalTaxMoney()
        // 如果没有[],直接返回totalstoremoney, totalstoremoney则是jdScmShopBillPzEntity.getTotalStoreMoney().
        // 暂时只有这两种规则, 如果有其他规则, 需要在这里添加.
        BigDecimal totalStoreMoney;
        if (amount.contains("[")) {
            BigDecimal totalIncludeTaxMoney = jdScmShopBillPzEntity.getTotalIncludeTaxMoney();
            BigDecimal totalTaxMoney = jdScmShopBillPzEntity.getTotalTaxMoney();
            totalStoreMoney = totalIncludeTaxMoney.subtract(totalTaxMoney);
        } else {
            totalStoreMoney = jdScmShopBillPzEntity.getTotalstoremoney();
        }
        // totalStoreMoney保留两位小数并四舍五入转换成String.
        totalStoreMoney = totalStoreMoney.setScale(2, RoundingMode.HALF_UP);
        return totalStoreMoney;
    }

    /**
     * get string builder
     *
     * @param jdScmShopBillPzEntity                       jd scm shop bill pz entity
     * @param voucherGroupingVoucherAccountingEntryEntity voucher grouping voucher accounting entry entity
     * @return {@link StringBuilder}
     */
    @NotNull
    private static StringBuilder getExplanationString(JdScmShopBillPzEntity jdScmShopBillPzEntity, VoucherGroupingVoucherAccountingEntryEntity voucherGroupingVoucherAccountingEntryEntity) {
        String abstractString = voucherGroupingVoucherAccountingEntryEntity.getAbstractString();
        // 如果摘要规则为空,则抛出异常
        if (abstractString == null) {
            throw new IllegalArgumentException("摘要规则为空!");
        }
        // 如果摘要规则不为空,则根据摘要规则拼接摘要.
        // 获取到的摘要规则为['shopname', 'billtype'],则根据获取到的摘要规则,拼接摘要.
        // 遍历摘要规则, 如果是shopname, 则拼接shopname, 如果是billtype, 则拼接billtype
        StringBuilder explanation = new StringBuilder();
        // 前后去掉[]
        abstractString = abstractString.replace("[", "").replace("]", "").trim();
        String[] split = abstractString.split(",");
        for (String s : split) {
            if ("'busmonth'".equals(s.trim())) {
                explanation.append(jdScmShopBillPzEntity.getBusMonth());
            } else if ("'shopname'".equals(s.trim())) {
                explanation.append(jdScmShopBillPzEntity.getShopName());
            } else if ("'billtype'".equals(s.trim())) {
                explanation.append(jdScmShopBillPzEntity.getBillType());
            } else if ("'OtherSideName'".equals(s.trim())) {
                explanation.append(jdScmShopBillPzEntity.getOtherSideName());
            }
        }
        return explanation;
    }

    /**
     * build single model
     *
     * @param jdScmShopBillPzEntity                          jd scm shop bill pz entity
     * @param voucherGroupingVoucherAccountingEntryEntityMap voucher grouping voucher accounting entry entity map
     * @return {@link KingdeeSaveCredentialOrderRequestModel}
     */
    private KingdeeSaveCredentialOrderRequestModel buildSingleModel(JdScmShopBillPzEntity jdScmShopBillPzEntity
        , Map<String, List<VoucherGroupingVoucherAccountingEntryEntity>> voucherGroupingVoucherAccountingEntryEntityMap) {
        // 2. 拼接参数

        // 2.2 业务时间 格式化为YYYY-MM-DD字符串
        String formattedBusinessDate = jdScmShopBillPzEntity.getBusMonth();
        // 根据formattedBusinessDate获取年份
        String year = formattedBusinessDate.substring(0, 4);
        // year转换成Integer
        int yearInt = Integer.parseInt(year);
        // 根据formattedBusinessDate获取年份获取月份
        String month = formattedBusinessDate.substring(5, 7);
        // month转换成Integer
        int monthInt = Integer.parseInt(month);

        // 2.4 门店编码
        String shopCode = jdScmShopBillPzEntity.getShopCode();

        // 摘要 = shopname+ billtype
        String billPzType = jdScmShopBillPzEntity.getBillType();
        List<VoucherGroupingVoucherAccountingEntryEntity> voucherGroupingVoucherAccountingEntryEntities
            = voucherGroupingVoucherAccountingEntryEntityMap.get(billPzType);
        if (voucherGroupingVoucherAccountingEntryEntities == null || voucherGroupingVoucherAccountingEntryEntities.isEmpty()) {
            throw new IllegalArgumentException("没有找到对应的凭证规则!");
        }
        // 初始化List<KingdeeSaveCredentialOrderFEntity> FEntities.
        List<KingdeeSaveCredentialOrderFEntity> fEntities = new ArrayList<>();
        // 遍历voucherGroupingVoucherAccountingEntryEntities
        for (VoucherGroupingVoucherAccountingEntryEntity voucherGroupingVoucherAccountingEntryEntity
            : voucherGroupingVoucherAccountingEntryEntities) {
            // 获取凭证规则的摘要规则 ['shopname', 'billtype']
            StringBuilder explanation = getExplanationString(jdScmShopBillPzEntity, voucherGroupingVoucherAccountingEntryEntity);

            // 2.5 获取科目编码, voucher_grouping_voucher_accounting_entry.subject_code
            String subjectCode = voucherGroupingVoucherAccountingEntryEntity.getSubjectCode();

            // 获取借方金额和贷方金额
            // 获取voucher_grouping_voucher_accounting_entry.amount规则. 例如[totalIncludeTaxMoney]-[totalTaxMoney]
            BigDecimal totalStoreMoney = getTotalStoreMoney(jdScmShopBillPzEntity, voucherGroupingVoucherAccountingEntryEntity);

            // 获取voucherGroupingVoucherAccountingEntryEntity.accounts
            String accounts = voucherGroupingVoucherAccountingEntryEntity.getAccounts();

            // 获取voucherGroupingVoucherAccountingEntryEntity.accountsIndex
            String accountsIndex = voucherGroupingVoucherAccountingEntryEntity.getAccountsIndex();
            // 如果accountsIndex为null,则accountsIndex为空.
            if (accountsIndex == null) {
                accountsIndex = "";
            }

            // 如果accounts不为空,并且为OtherSideCode,则取jdScmShopBillPzEntity.OtherSideCode值.
            if (StringUtils.isNotBlank(accounts) && "OtherSideCode".equals(accounts)) {
                accounts = jdScmShopBillPzEntity.getOtherSideCode();
            }

            // 获取voucherGroupingVoucherAccountingEntryEntity.getDebit 如果1 借方金额, 如果2 贷方金额
            Integer debit = voucherGroupingVoucherAccountingEntryEntity.getDebit();
            // 如果debit为1, 则借方金额为totalStoreMoney, 贷方金额为0
            // 如果debit为2, 则借方金额为0, 贷方金额为totalStoreMoney
            if (debit == 1) {
                fEntities.add(KingdeeSaveCredentialOrderFEntity.builder()
                    // 摘要
                    .FEXPLANATION(explanation.toString())
                    // 科目编码
                    .FACCOUNTID(BaseFNumber.builder().FNumber(subjectCode).build())
                    // 对方机构编码
                    // .FDetailID()
                    .FEXCHANGERATETYPE(BaseFNumber.builder().FNumber("HLTX01_SYS").build())
                    // 币别
                    .FCURRENCYID(BaseFNumber.builder().FNumber("PRE001").build())
                    .FEXCHANGERATE(1)
                    // 原币金额
                    .FAMOUNTFOR(String.valueOf(totalStoreMoney))
                    // 借方金额
                    .FDEBIT(String.valueOf(totalStoreMoney))
                    .FDetailID(kingdeeSaveCashCredentialOrderCmdExe
                        .buildKingdeeSaveCredentialOrderFEntityFDetailIdByAccounts(accounts, accountsIndex))
                    // 贷方金额
                    // .FCREDIT(String.valueOf(totalStoreMoney))
                    .build());
            } else if (debit == 2) {
                fEntities.add(KingdeeSaveCredentialOrderFEntity.builder()
                    // 摘要
                    .FEXPLANATION(explanation.toString())
                    // 科目编码
                    .FACCOUNTID(BaseFNumber.builder().FNumber(subjectCode).build())
                    // 对方机构编码
                    // .FDetailID()
                    .FEXCHANGERATETYPE(BaseFNumber.builder().FNumber("HLTX01_SYS").build())
                    // 币别
                    .FCURRENCYID(BaseFNumber.builder().FNumber("PRE001").build())
                    .FEXCHANGERATE(1)
                    // 借方金额
                    // .FDEBIT(String.valueOf(totalStoreMoney))
                    // 原币金额
                    .FAMOUNTFOR(String.valueOf(totalStoreMoney))
                    // 贷方金额
                    .FCREDIT(String.valueOf(totalStoreMoney))
                    .FDetailID(kingdeeSaveCashCredentialOrderCmdExe
                        .buildKingdeeSaveCredentialOrderFEntityFDetailIdByAccounts(accounts, accountsIndex))
                    .build());
            }
        }

        // 3. 返回参数
        return KingdeeSaveCredentialOrderRequestModel.builder()
            // 单据类型编码
            .FAccountBookID(BaseFNumber.builder().FNumber(shopCode).build())
            .FYEAR(yearInt)
            // 业务时间
            .FDate(formattedBusinessDate)
            // 凭证字 写死PRE001
            .FVOUCHERGROUPID(BaseFNumber.builder().FNumber("PRE001").build())
            .FSourceBillKey(BaseFNumber.builder().FNumber("78050206-2fa6-40e3-b7c8-bd608146fa38").build())
            .FPERIOD(monthInt)
            .FVOUCHERGROUPNO(" ")
            // 实例 FEntity
            .FEntity(fEntities)
            .build();
    }

}
