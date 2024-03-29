package com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.IJdPosShopBillDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.IKingdeePosBillAuxiliaryCalledDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.IVoucherGroupingVoucherAccountingEntryDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.JdPosShopBillEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.KingdeePosBillAuxiliaryCalledEntity;
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
 * 金蝶SaaS-调用保存收银凭证具体逻辑.
 *
 * @author steven
 * &#064;date  2023/10/29
 */
@Service
@Slf4j
public class KingdeeSaveCashCredentialOrderCmdExe {

    // 初始化system 静态变量
    private static final String SYSTEM = "system";

    @Resource
    private IJdPosShopBillDao iJdPosShopBillDao;

    @Resource
    private IKingdeePosBillAuxiliaryCalledDao iKingdeePosBillAuxiliaryCalledDao;

    @Resource
    private IVoucherGroupingVoucherAccountingEntryDao iVoucherGroupingVoucherAccountingEntryDao;

    /**
     * get total store money
     *
     * @param jdPosShopBillEntity                         jd scm shop bill pz entity
     * @param voucherGroupingVoucherAccountingEntryEntity voucher grouping voucher accounting entry entity
     * @return {@link BigDecimal}
     */
    @NotNull
    private static BigDecimal getTotalStoreMoney(JdPosShopBillEntity jdPosShopBillEntity, VoucherGroupingVoucherAccountingEntryEntity voucherGroupingVoucherAccountingEntryEntity) {
        String amount = voucherGroupingVoucherAccountingEntryEntity.getAmount();
        // 根据规则计算借方金额和贷方金额.
        // [totalIncludeTaxMoney]-[totalTaxMoney] 则是根据jdPosShopBillEntity.getTotalIncludeTaxMoney() - jdPosShopBillEntity.getTotalTaxMoney()
        // 如果没有[],直接返回getTotalIncomeMoney.
        // 暂时只有这两种规则, 如果有其他规则, 需要在这里添加.
        BigDecimal totalStoreMoney;
        if (amount.contains("[")) {
            BigDecimal totalIncludeTaxMoney = jdPosShopBillEntity.getTotalPayMoney();
            BigDecimal totalTaxMoney = jdPosShopBillEntity.getTotalIncomeMoney();
            totalStoreMoney = totalIncludeTaxMoney.subtract(totalTaxMoney);
        } else {
            totalStoreMoney = jdPosShopBillEntity.getTotalIncomeMoney();
        }
        // totalStoreMoney保留两位小数并四舍五入转换成String.
        totalStoreMoney = totalStoreMoney.setScale(2, RoundingMode.HALF_UP);
        return totalStoreMoney;
    }

    /**
     * get string builder
     *
     * @param jdPosShopBillEntity                         jd scm shop bill pz entity
     * @param voucherGroupingVoucherAccountingEntryEntity voucher grouping voucher accounting entry entity
     * @return {@link StringBuilder}
     */
    @NotNull
    private static StringBuilder getStringExplanation(JdPosShopBillEntity jdPosShopBillEntity
        , VoucherGroupingVoucherAccountingEntryEntity voucherGroupingVoucherAccountingEntryEntity) {
        String[] split = getSplit(voucherGroupingVoucherAccountingEntryEntity);
        // 获取]后面的字符串
        String substring = split[1];
        // 拼接字符串
        StringBuilder explanation = new StringBuilder();
        explanation.append(jdPosShopBillEntity.getBusMonth());
        // 如果substring不为空,则拼接
        if (StringUtils.isNotBlank(substring)) {
            explanation.append(substring);
        }

        return explanation;
    }

    /**
     * get split
     *
     * @param voucherGroupingVoucherAccountingEntryEntity voucher grouping voucher accounting entry entity
     * @return {@link String[]}
     */
    @NotNull
    private static String[] getSplit(VoucherGroupingVoucherAccountingEntryEntity voucherGroupingVoucherAccountingEntryEntity) {
        String abstractString = voucherGroupingVoucherAccountingEntryEntity.getAbstractString();
        // 如果摘要规则为空,则抛出异常
        if (abstractString == null) {
            throw new IllegalArgumentException("摘要规则为空!");
        }
        // 如果摘要规则不为空,则根据摘要规则拼接摘要.
        // 获取到的摘要规则为[bus month]堂食收入,则拼接为jdPosShopBillEntity.getBusMonth() + "堂食收入"
        // 根据]分割字符串,获取到bus month
        String[] split = abstractString.split("]");
        // 如果split长度小于2,则抛出异常
        if (split.length < 2) {
            throw new IllegalArgumentException("摘要规则不正确!");
        }
        return split;
    }

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
        log.info("KingdeeSaveCashCredentialOrderCmdExe.execute() start");

        // 读取配置，初始化SDK
        K3CloudApi client = new K3CloudApi();

        // 拼接入参对象.
        // 1. 获取 jdScmShopBillList
        List<JdPosShopBillEntity> jdScmShopBillList = getJdScmShopBillList(cmd);

        // jdScmShopBillList根据 BusMonth 和 shopCode 进行分组.
        // 同一家店铺和同一个月份的数据, 当做一条数据处理.
        Map<String, List<JdPosShopBillEntity>> jdScmShopBillListMap = jdScmShopBillList.stream()
            .collect(Collectors.groupingBy(jdPosShopBillEntity -> jdPosShopBillEntity.getBusMonth() + jdPosShopBillEntity.getShopCode()));

        // 遍历jdScmShopBillListMap
        for (Map.Entry<String, List<JdPosShopBillEntity>> entry : jdScmShopBillListMap.entrySet()) {
            // 获取当前entry.getKey()的索引顺序值
            String key = entry.getKey();
            int index = jdScmShopBillListMap.keySet().stream().toList().indexOf(key) + 1;
            // 打印日志 总共有多少条数据 现在处理第几条数据
            log.info("调用收银凭证-总共有{}条数据,现在处理第{}条数据", jdScmShopBillListMap.size(), index);
            // 打印日志 此次处理的门店名称和门店应付单数量
            log.info("调用收银凭证-门店名称:{},门店应付单数量:{}", key, entry.getValue().size());
            List<JdPosShopBillEntity> entryJdPosShopBillValue = entry.getValue();

            // 2.1 . 获取kingdee_bill_auxiliary_called已经回调成功的数据,并去除已经回调成功的数据的id
            List<KingdeePosBillAuxiliaryCalledEntity> kingdeeBillAuxiliaryCalledSuccessEntities
                = getKingdeeBillAuxiliaryCalledSuccessEntities();
            // jdScmShopBillList和kingdeeBillAuxiliaryCalledSuccessEntities id 取差集,就是还没有回调成功的数据.
            entryJdPosShopBillValue = entryJdPosShopBillValue.stream()
                .filter(jdPosShopBillEntity -> kingdeeBillAuxiliaryCalledSuccessEntities.stream()
                    .noneMatch(kingdeeBillAuxiliaryCalledEntity -> kingdeeBillAuxiliaryCalledEntity.getId()
                        .equals(key)))
                .toList();

            // entryJdPosShopBillValue如果不为空,则继续执行
            if (CollUtil.isEmpty(entryJdPosShopBillValue)) {
                continue;
            }
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


            // 2.3 获取KingdeeSaveCredentialOrderBatchRequest 批量保存入参对象
            KingdeeSaveCredentialOrderBatchRequest kingdeeSaveCredentialOrderBatchRequest =
                getKingdeeSaveCredentialOrderBatchRequest(entryJdPosShopBillValue
                    , voucherGroupingVoucherAccountingEntryEntityMap);
            // 2.4 入参转jsonString.
            String jsonData = new Gson().toJson(kingdeeSaveCredentialOrderBatchRequest);
            // 测试成功json数据.
            // String jsonData = KingdeePayableBillSuccessConstant.CASH_CREDENTIAL_BILL_SUCCESS_JSON_DATA;
            // 2.5 业务对象标识
            String formId = "GL_VOUCHER";
            // 2.6 调用接口
            String resultJson = client.batchSave(formId, jsonData);
            // 2.7 用于记录结果
            Gson gson = new Gson();
            // 2.8 对返回结果进行解析和校验
            RepoRet repoRet = gson.fromJson(resultJson, RepoRet.class);
            // 2.9 保存成功后，更新数据库状态
            saveCallbackResult(key, entryJdPosShopBillValue, repoRet);

            // 打印日志 - 结束. 完成处理第几条数据,还剩多少条数据
            log.info("完成处理第{}个店,还剩{}个门店数据待处理!", index, (jdScmShopBillListMap.size() - index));
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
     * @param jdScmShopBillList {@link List}<{@link JdPosShopBillEntity}>
     * @param repoRet           {@link RepoRet}
     */
    private void saveCallbackResult(String key, List<JdPosShopBillEntity> jdScmShopBillList, RepoRet repoRet) {

        // 1. 如果repoRet.getResult().getResponseStatus().isIsSuccess()为true,则说明全是成功, 按照成功处理.
        if (repoRet.getResult().getResponseStatus().isIsSuccess()) {
            // 1. 调用buildSuccessEntities方法,
            // 获取List<KingdeePosBillAuxiliaryCalledEntity> kingdeeCredentialBillAuxiliaryCalledEntities
            ArrayList<SuccessEntity> successEntities = repoRet.getResult().getResponseStatus().getSuccessEntitys();
            List<KingdeePosBillAuxiliaryCalledEntity> kingdeeCredentialBillAuxiliaryCalledEntities
                = buildSuccessEntities(key, jdScmShopBillList, successEntities);
            // 2. 批量保存
            iKingdeePosBillAuxiliaryCalledDao.saveOrUpdateBatch(kingdeeCredentialBillAuxiliaryCalledEntities);
        } else {
            // 说明有失败的, 按照失败处理. 保存失败结果
            // 1. 初始化List<KingdeePosBillAuxiliaryCalledEntity> kingdeeCredentialBillAuxiliaryCalledEntities
            List<KingdeePosBillAuxiliaryCalledEntity> kingdeeCredentialBillAuxiliaryCalledEntities = new ArrayList<>();
            // 2. 遍历repoRet.getResult().getResponseStatus().getSuccessEntities()
            ArrayList<SuccessEntity> successEntities = repoRet.getResult().getResponseStatus().getSuccessEntitys();
            // 如果successEntities不为空, 则保存成功的
            if (successEntities != null && !successEntities.isEmpty()) {
                // 调用buildSuccessEntities方法, 获取List<KingdeePosBillAuxiliaryCalledEntity> kingdeeCredentialBillAuxiliaryCalledEntities
                List<KingdeePosBillAuxiliaryCalledEntity> kingdeeBillAuxiliaryCalledSuccessEntities
                    = buildSuccessEntities(key, jdScmShopBillList, successEntities);
                // kingdeeBillAuxiliaryCalledSuccessEntities新增到kingdeeCredentialBillAuxiliaryCalledEntities
                kingdeeCredentialBillAuxiliaryCalledEntities.addAll(kingdeeBillAuxiliaryCalledSuccessEntities);
            }
            // 3. 获取失败的回调结果
            ArrayList<RepoError> errors = repoRet.getResult().getResponseStatus().getErrors();
            // 获取失败的回调结果
            List<KingdeePosBillAuxiliaryCalledEntity> kingdeeBillAuxiliaryCalledFailedEntities
                = buildFailedEntities(key, jdScmShopBillList, errors);
            // kingdeeBillAuxiliaryCalledFailedEntities新增到kingdeeCredentialBillAuxiliaryCalledEntities
            kingdeeCredentialBillAuxiliaryCalledEntities.addAll(kingdeeBillAuxiliaryCalledFailedEntities);

            // 4. 保存kingdeeCredentialBillAuxiliaryCalledEntities
            iKingdeePosBillAuxiliaryCalledDao.saveOrUpdateBatch(kingdeeCredentialBillAuxiliaryCalledEntities);
        }
    }

    /**
     * build success entities
     *
     * @param jdScmShopBillList jd scm shop bill list
     * @param successEntities   success entities
     * @return {@link List}<{@link KingdeePosBillAuxiliaryCalledEntity}>
     */
    private List<KingdeePosBillAuxiliaryCalledEntity> buildSuccessEntities(String key, List<JdPosShopBillEntity> jdScmShopBillList
        , ArrayList<SuccessEntity> successEntities) {
        // 判断successEntities是否为空, 如果为空, 则返回空集合
        if (successEntities == null || successEntities.isEmpty()) {
            return new ArrayList<>();
        }
        // 1. 初始化List<KingdeePosBillAuxiliaryCalledEntity> kingdeeCredentialBillAuxiliaryCalledEntities
        List<KingdeePosBillAuxiliaryCalledEntity> kingdeeCredentialBillAuxiliaryCalledEntities = new ArrayList<>();
        for (SuccessEntity successEntity : successEntities) {
            // 获取successEntity的索引顺序值, 用于获取jdScmShopBillList中的jdPosShopBillEntity
            int index = successEntity.getDIndex();
            // 根据索引顺序值获取jdPosShopBillEntity
            JdPosShopBillEntity jdPosShopBillEntity = jdScmShopBillList.get(index);
            // 2. 拼接数据
            // 2.1 获取单据id
            String kingdeeBillId = successEntity.getId();
            // 2.2 获取单据number
            String kingdeeBillNumber = successEntity.getNumber();
            // 2.3 获取回调状态 成功 0失败1成功
            Integer status = 1;

            // 3.4 添加到kingdeeCredentialBillAuxiliaryCalledEntities
            kingdeeCredentialBillAuxiliaryCalledEntities.add(KingdeePosBillAuxiliaryCalledEntity.builder()
                .id(key)
                .companyId("dth")
                .jdPosShopBillNo(jdPosShopBillEntity.getId())
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
     * @return {@link List}<{@link KingdeePosBillAuxiliaryCalledEntity}>
     */
    private List<KingdeePosBillAuxiliaryCalledEntity> buildFailedEntities(String key, List<JdPosShopBillEntity> jdScmShopBillList
        , ArrayList<RepoError> errors) {
        // 判断errors是否为空, 如果为空, 则返回空集合
        if (errors == null || errors.isEmpty()) {
            return new ArrayList<>();
        }
        // 1. 初始化List<KingdeePosBillAuxiliaryCalledEntity> kingdeeCredentialBillAuxiliaryCalledEntities
        List<KingdeePosBillAuxiliaryCalledEntity> kingdeeCredentialBillAuxiliaryCalledEntities = new ArrayList<>();
        // 2. 遍历repoRet.getResult().getResponseStatus().getSuccessEntities()
        // 3.2 遍历errors
        for (RepoError error : errors) {
            // 3.3 拼接数据
            // 获取errors的索引顺序值, 用于获取jdScmShopBillList中的jdPosShopBillEntity
            int index = error.getDIndex();
            // 根据索引顺序值获取jdPosShopBillEntity
            JdPosShopBillEntity jdPosShopBillEntity = jdScmShopBillList.get(index);
            // 3.3.3 获取回调状态 失败 0失败1成功
            Integer status = 0;
            // 3.3.4 获取错误信息 errorMsg = error.FieldName + error.Message
            String errorMsg = error.getFieldName() + error.getMessage();
            // 3.4 添加到kingdeeCredentialBillAuxiliaryCalledEntities
            kingdeeCredentialBillAuxiliaryCalledEntities.add(KingdeePosBillAuxiliaryCalledEntity.builder()
                .id(key)
                .companyId("dth")
                .jdPosShopBillNo(jdPosShopBillEntity.getId())
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
     * @return {@link List}<{@link JdPosShopBillEntity}>
     */
    private List<JdPosShopBillEntity> getJdScmShopBillList(KingdeeCredentialBillCalledCmd cmd) {
        // 1. 读取数据库
        QueryWrapper<JdPosShopBillEntity> queryWrapper = new QueryWrapper<>();
        // 获取cmd.门店名称
        List<String> shopNameList = cmd.getShopNameList();
        // 如果shopName不为空, 则根据shopName查询.否则查询全部
        if (CollUtil.isNotEmpty(shopNameList)) {
            queryWrapper.in("shop_name", shopNameList);
        }

        return iJdPosShopBillDao.list(queryWrapper);
    }

    private List<KingdeePosBillAuxiliaryCalledEntity> getKingdeeBillAuxiliaryCalledSuccessEntities() {
        // 1. 读取数据库
        QueryWrapper<KingdeePosBillAuxiliaryCalledEntity> queryWrapper = new QueryWrapper<>();
        // status = 1
        queryWrapper.eq("status", 1);
        return iKingdeePosBillAuxiliaryCalledDao.list(queryWrapper);
    }

    /**
     * get kingdee save payable order request
     * 拼接KingdeeSavePayableOrderRequest对象方法
     *
     * @return {@link KingdeeSavePayableOrderRequest}
     */
    private KingdeeSaveCredentialOrderBatchRequest getKingdeeSaveCredentialOrderBatchRequest(
        List<JdPosShopBillEntity> jdScmShopBillEntities
        , Map<String, List<VoucherGroupingVoucherAccountingEntryEntity>> voucherGroupingVoucherAccountingEntryEntityMap) {

        // 1. 初始化List<KingdeeSaveCredentialOrderRequestModel> Model
        List<KingdeeSaveCredentialOrderRequestModel> modelList = new ArrayList<>();

        KingdeeSaveCredentialOrderRequestModel kingdeeSaveCredentialOrderRequestModel =
            buildSingleModelByEntities(jdScmShopBillEntities, voucherGroupingVoucherAccountingEntryEntityMap);
        // 2. 添加到modelList
        modelList.add(kingdeeSaveCredentialOrderRequestModel);

        // 3. 返回KingdeeSaveCredentialOrderBatchRequest对象
        return KingdeeSaveCredentialOrderBatchRequest.builder()
            .Model(modelList)
            .build();
    }

    /**
     * build single model
     *
     * @param voucherGroupingVoucherAccountingEntryEntityMap voucher grouping voucher accounting entry entity map
     * @param jdScmShopBillEntities                          jd scm shop bill entities
     * @return {@link KingdeeSaveCredentialOrderRequestModel}
     */
    private KingdeeSaveCredentialOrderRequestModel buildSingleModelByEntities(List<JdPosShopBillEntity> jdScmShopBillEntities
        , Map<String, List<VoucherGroupingVoucherAccountingEntryEntity>> voucherGroupingVoucherAccountingEntryEntityMap) {

        JdPosShopBillEntity jdPosShopBillEntityFrist = jdScmShopBillEntities.get(0);

        // 2.2 业务时间 格式化为YYYY-MM-DD字符串
        String formattedBusinessDate = jdPosShopBillEntityFrist.getBusMonth();
        // 根据formattedBusinessDate获取年份
        String year = formattedBusinessDate.substring(0, 4);
        // year转换成Integer
        int yearInt = Integer.parseInt(year);
        // 根据formattedBusinessDate获取年份获取月份
        String month = formattedBusinessDate.substring(5, 7);
        // month转换成Integer
        int monthInt = Integer.parseInt(month);

        // 2.4 门店编码
        String shopCode = jdPosShopBillEntityFrist.getShopCode();

        // 摘要 = shopname+ billtype
        String billType = "应收凭证";
        List<VoucherGroupingVoucherAccountingEntryEntity> voucherGroupingVoucherAccountingEntryEntities
            = voucherGroupingVoucherAccountingEntryEntityMap.get(billType);
        if (voucherGroupingVoucherAccountingEntryEntities == null || voucherGroupingVoucherAccountingEntryEntities.isEmpty()) {
            throw new IllegalArgumentException("没有找到对应的凭证规则!");
        }
        // voucherGroupingVoucherAccountingEntryEntities根据Accounts进行分组.
        Map<String, List<VoucherGroupingVoucherAccountingEntryEntity>> voucherGroupingVoucherAccountingEntryEntityByAccountsMap
            = voucherGroupingVoucherAccountingEntryEntities.stream()
            .collect(Collectors.groupingBy(VoucherGroupingVoucherAccountingEntryEntity::getAccounts));


        // 初始化List<KingdeeSaveCredentialOrderFEntity> FEntities.
        List<KingdeeSaveCredentialOrderFEntity> fEntities = new ArrayList<>();
        // 遍历 jdScmShopBillEntities
        for (JdPosShopBillEntity jdPosShopBillEntity : jdScmShopBillEntities) {
            // 获取jdPosShopBillEntity.remark
            String remark = jdPosShopBillEntity.getRemark();
            // 只取voucherGroupingVoucherAccountingEntryEntityByAccountsMap中的remark
            List<VoucherGroupingVoucherAccountingEntryEntity> accountingEntryEntities
                = voucherGroupingVoucherAccountingEntryEntityByAccountsMap.get(remark);

            // 如果accountingEntryEntities为空,则跳过
            if (accountingEntryEntities == null || accountingEntryEntities.isEmpty()) {
                continue;
            }
            // 遍历voucherGroupingVoucherAccountingEntryEntities
            for (VoucherGroupingVoucherAccountingEntryEntity voucherGroupingVoucherAccountingEntryEntity
                : accountingEntryEntities) {
                // 获取凭证规则的摘要规则 [bus month]堂食收入
                StringBuilder explanation = getStringExplanation(jdPosShopBillEntity, voucherGroupingVoucherAccountingEntryEntity);

                // 2.5 获取科目编码, voucher_grouping_voucher_accounting_entry.subject_code
                String subjectCode = voucherGroupingVoucherAccountingEntryEntity.getSubjectCode();

                // 获取借方金额和贷方金额
                // 获取voucher_grouping_voucher_accounting_entry.amount规则. 例如[totalIncludeTaxMoney]-[totalTaxMoney]
                BigDecimal totalStoreMoney = getTotalStoreMoney(jdPosShopBillEntity, voucherGroupingVoucherAccountingEntryEntity);

                // 获取voucherGroupingVoucherAccountingEntryEntity.getDebit 如果1 借方金额, 如果2 贷方金额
                Integer debit = voucherGroupingVoucherAccountingEntryEntity.getDebit();

                // 获取voucherGroupingVoucherAccountingEntryEntity.accounts
                String accounts = voucherGroupingVoucherAccountingEntryEntity.getAccounts();

                // 获取voucherGroupingVoucherAccountingEntryEntity.accountsIndex
                String accountsIndex = voucherGroupingVoucherAccountingEntryEntity.getAccountsIndex();

                // 如果accounts不包含逗号,则FDetailID单个拼装,否则则多个拼装.
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
                        .FDetailID(buildKingdeeSaveCredentialOrderFEntityFDetailIdByAccounts(accounts, accountsIndex))
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
                        .build());
                }

            }
        }

        // 3. 返回参数
        return KingdeeSaveCredentialOrderRequestModel.builder()
            // 单据类型编码
            .FAccountBookID(BaseFNumber.builder().FNumber(shopCode).build())
            .FYEAR(yearInt)
            // 业务时间
            .FDate(formattedBusinessDate)
            .FBUSDATE(formattedBusinessDate)
            // 凭证字 写死PRE001
            .FVOUCHERGROUPID(BaseFNumber.builder().FNumber("PRE001").build())
            .FSourceBillKey(BaseFNumber.builder().FNumber("78050206-2fa6-40e3-b7c8-bd608146fa38").build())
            .FPERIOD(monthInt)
            .FVOUCHERGROUPNO("13")
            // 实例 FEntity
            .FEntity(fEntities)
            .build();
    }


    /**
     * build kingdee save credential order fentity fdetail id by accounts
     *
     * @param accounts accounts
     * @return {@link KingdeeSaveCredentialOrderFEntityFDetailId}
     */
    private KingdeeSaveCredentialOrderFEntityFDetailId buildKingdeeSaveCredentialOrderFEntityFDetailIdByAccounts(
        String accounts, String accountsIndex) {

        KingdeeSaveCredentialOrderFEntityFDetailId kingdeeSaveCredentialOrderFEntityFDetailId
            = new KingdeeSaveCredentialOrderFEntityFDetailId();

        // 1. 如果accounts包含逗号,则FDetailID多个拼装
        if (!accounts.contains(",") && !accountsIndex.contains(",")) {
            return getKingdeeSaveCredentialOrderFEntityFDetailIdByIndex(
                kingdeeSaveCredentialOrderFEntityFDetailId, accounts, accountsIndex);
        }
        // 2. 根据逗号分割accounts
        String[] split = accounts.split(",");
        // 3. 遍历split, 调用getKingdeeSaveCredentialOrderFEntityFDetailIdByIndex方法
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            String[] splitAccountsIndex = accountsIndex.split(",");
            String accountsIndexSingle = splitAccountsIndex[i];
            // 调用getKingdeeSaveCredentialOrderFEntityFDetailIdByIndex方法
            getKingdeeSaveCredentialOrderFEntityFDetailIdByIndex(
                kingdeeSaveCredentialOrderFEntityFDetailId, s, accountsIndexSingle);
        }
        return kingdeeSaveCredentialOrderFEntityFDetailId;
    }

    /**
     * get kingdee save credential order fentity fdetail id by index
     *
     * @param kingdeeSaveCredentialOrderFEntityFDetailId kingdee save credential order fentity fdetail id
     * @param accounts                                   accounts
     * @param accountsIndex                              accounts index
     * @return {@link KingdeeSaveCredentialOrderFEntityFDetailId}
     */
    private KingdeeSaveCredentialOrderFEntityFDetailId getKingdeeSaveCredentialOrderFEntityFDetailIdByIndex(
        KingdeeSaveCredentialOrderFEntityFDetailId kingdeeSaveCredentialOrderFEntityFDetailId
        , String accounts, String accountsIndex) {

        // 没有逗号, 则FDetailID单个拼装
        if (!accountsIndex.contains(",")) {
            return getKingdeeSaveCredentialOrderFEntityFDetailId(kingdeeSaveCredentialOrderFEntityFDetailId
                , accounts, accountsIndex);
        } else {
            // 否则FDetailID多个拼装
            // 根据逗号分割accountsIndex
            String[] split = accountsIndex.split(",");
            // 遍历split, 调用getKingdeeSaveCredentialOrderFEntityFDetailId方法
            for (String s : split) {
                // 调用getKingdeeSaveCredentialOrderFEntityFDetailId方法
                getKingdeeSaveCredentialOrderFEntityFDetailId(
                    kingdeeSaveCredentialOrderFEntityFDetailId, accounts, s);
            }

        }
        return kingdeeSaveCredentialOrderFEntityFDetailId;
    }

    /**
     * get kingdee save credential order fentity fdetail id
     *
     * @param kingdeeSaveCredentialOrderFEntityFDetailId kingdee save credential order fentity fdetail id
     * @param accounts                                   accounts
     * @param accountsIndex                              accounts index
     * @return {@link KingdeeSaveCredentialOrderFEntityFDetailId}
     */
    private KingdeeSaveCredentialOrderFEntityFDetailId getKingdeeSaveCredentialOrderFEntityFDetailId(
        KingdeeSaveCredentialOrderFEntityFDetailId kingdeeSaveCredentialOrderFEntityFDetailId
        , String accounts, String accountsIndex) {
        // accountsIndex 转 int
        int accountsIndexInt = Integer.parseInt(accountsIndex);
        // 如果accountsIndexInt为4, 则拼装FDetailID__FFLEX1开始拼接,拼接到16. 用switch case语句.
        switch (accountsIndexInt) {
            case 4:
                kingdeeSaveCredentialOrderFEntityFDetailId.setFDETAILID__FFLEX4(BaseFNumber.builder().FNumber(accounts).build());
                break;
            case 5:
                kingdeeSaveCredentialOrderFEntityFDetailId.setFDETAILID__FFLEX5(BaseFNumber.builder().FNumber(accounts).build());
                break;
            case 7:
                kingdeeSaveCredentialOrderFEntityFDetailId.setFDETAILID__FFLEX7(BaseFNumber.builder().FNumber(accounts).build());
                break;
            case 8:
                kingdeeSaveCredentialOrderFEntityFDetailId.setFDETAILID__FFLEX8(BaseFNumber.builder().FNumber(accounts).build());
                break;
            case 9:
                kingdeeSaveCredentialOrderFEntityFDetailId.setFDETAILID__FFLEX9(BaseFNumber.builder().FNumber(accounts).build());
                break;
            case 10:
                kingdeeSaveCredentialOrderFEntityFDetailId.setFDETAILID__FFLEX10(BaseFNumber.builder().FNumber(accounts).build());
                break;
            case 11:
                kingdeeSaveCredentialOrderFEntityFDetailId.setFDETAILID__FFLEX11(BaseFNumber.builder().FNumber(accounts).build());
                break;
            case 12:
                kingdeeSaveCredentialOrderFEntityFDetailId.setFDETAILID__FFLEX12(BaseFNumber.builder().FNumber(accounts).build());
                break;
            case 13:
                kingdeeSaveCredentialOrderFEntityFDetailId.setFDETAILID__FFLEX13(BaseFNumber.builder().FNumber(accounts).build());
                break;
            case 14:
                kingdeeSaveCredentialOrderFEntityFDetailId.setFDETAILID__FFLEX14(BaseFNumber.builder().FNumber(accounts).build());
                break;
            case 15:
                kingdeeSaveCredentialOrderFEntityFDetailId.setFDETAILID__FFLEX15(BaseFNumber.builder().FNumber(accounts).build());
                break;
            case 16:
                kingdeeSaveCredentialOrderFEntityFDetailId.setFDETAILID__FFLEX16(BaseFNumber.builder().FNumber(accounts).build());
                break;
            case 6:
            default:
                kingdeeSaveCredentialOrderFEntityFDetailId.setFDETAILID__FFLEX6(BaseFNumber.builder().FNumber(accounts).build());
                break;
        }
        return kingdeeSaveCredentialOrderFEntityFDetailId;
    }

}
