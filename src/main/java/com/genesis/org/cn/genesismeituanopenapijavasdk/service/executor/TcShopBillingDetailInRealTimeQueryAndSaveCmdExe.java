package com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor;

import cn.hutool.core.util.ObjectUtil;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcShopBillingDetailDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcShopBillingDetailItemDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcShopBillingSettleDetailDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcShopDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcShopBillingDetailEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcShopBillingDetailItemEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcShopBillingSettleDetailEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcShopEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.base.BaseVO;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.LoginToServerAction;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.QueryShopInfoAction;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.enums.ResponseStatusEnum;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.*;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.base.BasePageInfo;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * tc shop billing detail query and save cmd exe
 * 天财SaaS-调用获取账单明细实时并落库api具体逻辑.
 *
 * @author steven
 * &#064;date  2023/12/10
 */
@Service
@Slf4j
public class TcShopBillingDetailInRealTimeQueryAndSaveCmdExe {

    /**
     * 服务器请求协议
     */
    @Value("${tiancai.protocol}")
    private String protocol;

    /**
     * 服务器地址
     */
    @Value("${tiancai.url}")
    private String applicationServer;
    /**
     * 服务器端口
     */
    @Value("${tiancai.port}")
    private Integer applicationPort;

    /**
     * 天财AppId
     */
    @Value("${tiancai.api.appId}")
    private String appId;

    /**
     * 天财accessId
     */
    @Value("${tiancai.api.accessId}")
    private String accessId;

    /**
     * 餐饮集团ID
     */
    @Value("${tiancai.api.centerId}")
    private String centerId;

    @Resource
    private ITcShopDao iTcShopDao;

    @Resource
    private ITcShopBillingDetailDao iTcShopBillingDetailDao;

    @Resource
    private ITcShopBillingDetailItemDao iTcShopBillingDetailItemDao;

    @Resource
    private ITcShopBillingSettleDetailDao iTcShopBillingSettleDetailDao;

    /**
     * execute
     * 执行
     *
     * @return {@link BaseVO}
     */
    @SneakyThrows
    public BaseVO execute() {
        // 打印日志 - 开始.
        log.info("TcShopBillingDetailInRealTimeQueryAndSaveCmdExe.execute() - start");
        // 1. 根据天财AppId和accessId进行鉴权.
        LoginResult loginResult = LoginToServerAction.login(protocol, applicationServer, applicationPort
            , appId, accessId);
        String msg = loginResult.getMsg();
        // 如果msg不为success,则抛出异常.
        if (!ResponseStatusEnum.SUCCESS.getValue().equals(msg)) {
            throw new Exception("鉴权失败!");
        }
        // 如果msg为success,则获取accessToken.
        String accessToken = loginResult.getAccessToken();
        // 打印日志 - 鉴权成功.
        log.info("TcShopBillingDetailInRealTimeQueryAndSaveCmdExe.execute() 鉴权成功, accessToken:{}", accessToken);

        // 2. 调用天财接口获取所有账单明细实时信息.
        // 2.0 获取所有店铺ids
        List<TcShopEntity> tcShopEntityList = iTcShopDao.list();
        List<String> shopIds = tcShopEntityList.stream().map(TcShopEntity::getShopId).toList();
        // 遍历shopIds,获取每个shopId的账单明细实时信息.
        for (String shopId : shopIds) {
            // 打印日志 - 执行百分比
            log.info("TcShopBillingDetailInRealTimeQueryAndSaveCmdExe.execute() 执行百分比:{}"
                , shopIds.indexOf(shopId) / shopIds.size());
            // 初始化分页参数.
            Integer pageNo = 1;
            Integer pageSize = 500;
            // 2.1 获取所有门店实时账单信息.
            QueryBillDetailsInRealTimeResponse queryBillDetailsInRealTimeResponse = QueryShopInfoAction
                .queryBillingDetailsInRealTime(protocol, applicationServer, applicationPort, accessId
                    , accessToken, pageNo, pageSize, shopId);

            // 2.2 获取账单明细列表.
            List<ShopBillItem> shopBillList = queryBillDetailsInRealTimeResponse.getData().getShopBillList();

            ShopBillItem shopBillItem = shopBillList.get(0);
            BasePageInfo pageInfo = shopBillItem.getPageInfo();
            // 获取总页数, 如果总页数大于1, 则遍历获取所有门店信息.
            int totalPage = pageInfo.getPageTotal();
            if (totalPage > 1) {
                for (int i = 2; i <= totalPage; i++) {
                    // 获取所有门店实时账单信息.
                    QueryBillDetailsInRealTimeResponse queryBillDetailsInRealTimeResponse2 = QueryShopInfoAction
                        .queryBillingDetailsInRealTime(protocol, applicationServer, applicationPort
                            , accessToken, centerId, i, pageSize, shopId);
                    // 如果success为false,则抛出异常.
                    if (!ResponseStatusEnum.SUCCESS.getInfo().equals(queryBillDetailsInRealTimeResponse2.getMsg())) {
                        throw new Exception("获取所有门店实时账单信息失败!");
                    }
                    // 如果success为true,则获取门店实时账单.
                    ShopBillItem shopBillItem2 = queryBillDetailsInRealTimeResponse2.getData().getShopBillList().get(0);
                    // 将shopBillList2添加到shopBillItem中.
                    shopBillList.add(shopBillItem2);
                }
            }
            List<BillListItem> billList = shopBillItem.getBillList();
            // 如果billList为空,则跳过.
            if (CollectionUtils.isEmpty(billList)) {
                continue;
            }
            MergeEntity mergeEntity = createTcShopBillingDetailEntity(centerId, shopId, billList);
            List<TcShopBillingDetailEntity> tcShopBillingDetailEntityList = mergeEntity
                .getTcShopBillingDetailEntityList();
            List<TcShopBillingDetailItemEntity> tcShopBillingDetailItemEntityList = mergeEntity
                .getTcShopBillingDetailItemEntityList();
            List<TcShopBillingSettleDetailEntity> tcShopBillingSettleDetailEntityList = mergeEntity
                .getTcShopBillingSettleDetailEntityList();


            // 3. 落库.
            saveBillDetail(tcShopBillingDetailEntityList, tcShopBillingDetailItemEntityList, tcShopBillingSettleDetailEntityList);
        }

        // 返回参数.
        return BaseVO.builder()
            .id("1")
            .state("success")
            .msg("成功")
            .build();
    }

    /**
     * extracted
     *
     * @param tcShopBillingDetailEntityList       tc shop a billing detail entity list
     * @param tcShopBillingDetailItemEntityList   tc shop billing detail item entity list
     * @param tcShopBillingSettleDetailEntityList tc shop billing settle detail entity list
     * @throws Exception exception
     */
    public void saveBillDetail(List<TcShopBillingDetailEntity> tcShopBillingDetailEntityList
        , List<TcShopBillingDetailItemEntity> tcShopBillingDetailItemEntityList
        , List<TcShopBillingSettleDetailEntity> tcShopBillingSettleDetailEntityList) throws Exception {
        // 3.1 落库tcShopBillingDetailEntityList.
        // 如果tcShopBillingDetailEntityList不为空,则落库.
        if (!CollectionUtils.isEmpty(tcShopBillingDetailEntityList)) {
            boolean tcShopBillingDetailEntityTag = iTcShopBillingDetailDao.saveBatch(tcShopBillingDetailEntityList);
            if (!tcShopBillingDetailEntityTag) {
                throw new Exception("落库tcShopBillingDetailEntityList失败!");
            }
        }

        // 如果tcShopBillingDetailItemEntityList不为空,则落库.
        if (!CollectionUtils.isEmpty(tcShopBillingDetailItemEntityList)) {
            // 3.2 落库tcShopBillingDetailItemEntityList.
            boolean tcShopBillingDetailItemEntityListTag = iTcShopBillingDetailItemDao
                .saveBatch(tcShopBillingDetailItemEntityList);
            if (!tcShopBillingDetailItemEntityListTag) {
                throw new Exception("落库tcShopBillingDetailItemEntityList失败!");
            }
        }

        // 如果tcShopBillingSettleDetailEntityList不为空,则落库.
        if (!CollectionUtils.isEmpty(tcShopBillingSettleDetailEntityList)) {
            // 3.3 落库tcShopBillingSettleDetailEntityList.
            boolean tcShopBillingSettleDetailEntityListTag = iTcShopBillingSettleDetailDao
                .saveBatch(tcShopBillingSettleDetailEntityList);
            if (!tcShopBillingSettleDetailEntityListTag) {
                throw new Exception("落库tcShopBillingSettleDetailEntityList失败!");
            }
        }

        // 打印日志 - 结束.
        log.info("TcShopBillingDetailInRealTimeQueryAndSaveCmdExe.execute() - end");
        log.info("TcShopBillingDetailInRealTimeQueryAndSaveCmdExe.execute() 完成落库, 对应的落库数量表1:{}" +
                ",对应的落库数量表2:{},对应的落库数量表3:{} ", tcShopBillingDetailEntityList.size()
            , tcShopBillingDetailItemEntityList.size(), tcShopBillingSettleDetailEntityList.size());
    }


    /**
     * create tc shop billing detail entity
     *
     * @param centerId center id
     * @param shopId   shop id
     * @param billList bill list
     * @return {@link MergeEntity}
     */
    public MergeEntity createTcShopBillingDetailEntity(String centerId, String shopId
        , List<BillListItem> billList) {

        // 1. 创建返回值
        List<TcShopBillingDetailEntity> tcShopBillingDetailEntityList = new ArrayList<>();
        List<TcShopBillingDetailItemEntity> tcShopBillingDetailItemEntityList = new ArrayList<>();
        List<TcShopBillingSettleDetailEntity> tcShopBillingSettleDetailEntityList = new ArrayList<>();

        // 2. 遍历poiParamList,转换为TcShopBillingDetailEntity对象.
        for (BillListItem billListItem : billList) {
            // 初始化tcShopBillingDetailEntityId
            String bsId = billListItem.getBsId();
            String id = ObjectUtil.cloneByStream(bsId);

            // 2.1 创建tcShopBillingDetailEntity对象.
            TcShopBillingDetailEntity tcShopBillingDetailEntity = TcShopBillingDetailEntity.builder()
                // id
                .id(bsId)
                // 集团id
                .centerId(centerId)
                // 天财门店id
                .shopId(shopId)
                // bsId
                .bsId(bsId)
                // bsCode
                .bsCode(billListItem.getBsCode())
                // areaCode
                .areaCode(billListItem.getAreaCode())
                // areaName
                .areaName(billListItem.getAreaName())
                // pointCode
                .pointCode(billListItem.getPointCode())
                // pointName
                .pointName(billListItem.getPointName())
                // 人数
                .peopleQty(billListItem.getPeopleQty())
                // 开台时间
                .openTime(billListItem.getOpenTime())
                // 结账时间
                .settleTime(billListItem.getSettleTime())
                // 营业日
                .settleBizDate(billListItem.getSettleBizDate())
                // state
                .state(billListItem.getState())
                // 服务员代码
                .waiterCode(billListItem.getWaiterCode())
                // 服务员名称
                .waiterName(billListItem.getWaiterName())
                // 营销员代码
                .salesmanCode(billListItem.getSalesmanCode())
                // 营销员名称
                .salesmanName(billListItem.getSalesmanName())
                // 品项折前金额
                .itemOrigMoney(billListItem.getItemOrigMoney())
                // 品项合计金额
                .itemIncomeTotal(billListItem.getIncomeTotal())
                // 服务费应收金额
                .origServerFee(billListItem.getOrigServerFee())
                // 服务费纯收金额
                .serviceFeeIncomeMoney(billListItem.getServiceFeeIncomeMoney())
                // 服务费非纯收金额
                .serviceFeeNotIncomeMoney(billListItem.getServiceFeeNotIncomeMoney())
                // 服务费实收金额
                .serviceFeeLastTotal(billListItem.getServiceFeeLastTotal())
                // 最低消费补齐金额
                .origZdxfbq(billListItem.getOrigZdxfbq())
                // 应收金额
                .origTotal(billListItem.getOrigTotal())
                // 优惠合计金额
                .discTotal(billListItem.getDiscTotal())
                // 折扣优惠金额
                .discMoney(billListItem.getDiscMoney())
                // 定额优惠金额
                .quotaMoney(billListItem.getQuotaMoney())
                // 赠送优惠金额
                .presentMoney(billListItem.getPresentMoney())
                // 会员价优惠金额
                .memberMoney(billListItem.getMemberMoney())
                // 促销优惠金额
                .promoteMoney(billListItem.getPromoteMoney())
                // 抹零金额
                .wipeMoney(billListItem.getWipeMoney())
                // 实收金额
                .lastTotal(billListItem.getLastTotal())
                // 纯收金额
                .incomeTotal(billListItem.getIncomeTotal())
                // 非纯收金额
                .notIncomeMoney(billListItem.getNotIncomeMoney())
                // 销售类型ID
                .saleTypeId(billListItem.getSaleTypeId())
                // 销售类型名称
                .saleTypeName(billListItem.getSaleTypeName())
                // 订单来源ID
                .orderOriginId(billListItem.getOrderOriginId())
                // 订单来源名称
                .orderOriginName(billListItem.getOrderOriginName())
                // 就餐类型
                .dinnerTypeName(billListItem.getDinnerTypeName())
                // 是否挂单
                .isDesignates(billListItem.getIsDesignates())
                // 结算状态
                .settleState(billListItem.getSettleState())
                // 发票号
                .invoiceNo(billListItem.getInvoiceNo())
                // 发票代码
                .invoiceCode(billListItem.getInvoiceCode())
                // 订单类型
                .orderType(billListItem.getOrderType())
                // 会员卡号
                .memberCardNo(billListItem.getMemberCardNo())
                // 会员id
                .memberId(billListItem.getMemberId())
                // 会员名
                .memberName(billListItem.getMemberName())
                // 会员手机号
                .memberMobile(billListItem.getMemberMobile())
                // 会员卡类型名称
                .cardKindName(billListItem.getCardKindName())
                // 是否续单
                .isContinuedBill(billListItem.getIsContinuedBill())
                // 是否续单名称
                .isContinuedBillName(billListItem.getIsContinuedBillName())
                // 税金
                .taxMoney(billListItem.getTaxMoney())
                // 牌号
                .orderCode(billListItem.getOrderCode())
                // 所属集团代码
                .centerCode(billListItem.getCenterCode())
                // 所属集团名称
                .centerName(billListItem.getCenterName())
                // 所属品牌代码
                .brandCode(billListItem.getBrandCode())
                // 所属品牌名称
                .brandName(billListItem.getBrandName())
                // 创建店代码
                .shopCode(billListItem.getShopCode())
                // 创建店名称
                .shopName(billListItem.getShopName())
                // 删除标记
                .delflg(billListItem.getDelflg())
                // 红冲原单id
                .origWdBsId(billListItem.getOrigWdBsId())
                // 外卖送餐时间
                .deliveryTime(billListItem.getDeliveryTime())
                // 创建人
                .createBy("system")
                // 创建时间
                .createTime(LocalDateTime.now())
                // 更新人
                .updateBy("system")
                // 更新时间
                .updateTime(LocalDateTime.now())
                .build();

            // 2.2 新增到返回值中.
            tcShopBillingDetailEntityList.add(tcShopBillingDetailEntity);


            // 2.3 创建tcShopBillingDetailItemEntity对象.
            List<BillListItemItem> item = billListItem.getItem();
            // 如果item不为空,则遍历item,转换为TcShopBillingDetailItemEntity对象.
            if (!CollectionUtils.isEmpty(item)) {
                for (BillListItemItem billListItemItem : item) {
                    String scId = billListItemItem.getScId();
                    // 2.3.1 创建tcShopBillingDetailItemEntity对象.
                    TcShopBillingDetailItemEntity tcShopBillingDetailItemEntity = TcShopBillingDetailItemEntity.builder()
                        // id
                        .id(scId)
                        // 集团id
                        .centerId(centerId)
                        // 天财门店id
                        .shopId(shopId)
                        // 门店实时账单明细表id
                        .tcShopBillingDetailId(id)
                        // 服务ID
                        .scId(scId)
                        // 品项ID
                        .itemId(billListItemItem.getItemId())
                        // 品项名称
                        .itemName(billListItemItem.getItemName())
                        // 品项单位
                        .unitName(billListItemItem.getUnitName())
                        // 品项规格Id
                        .sizeId(billListItemItem.getSizeId())
                        // 品项规格
                        .sizeName(billListItemItem.getSizeName())
                        // 最终单价
                        .lastPrice(billListItemItem.getLastPrice())
                        // 是否变过价
                        .isChangePrice(billListItemItem.getIsChangePrice())
                        // 最终制作费用（小计）
                        .lastMakeFee(billListItemItem.getLastMakeFee())
                        // 数量
                        .lastQty(billListItemItem.getLastQty())
                        // 折前小计（应收）
                        .origSubtotal(billListItemItem.getOrigSubtotal())
                        // 折扣金额
                        .discMoney(billListItemItem.getDiscMoney())
                        // 赠送优惠金额
                        .presentMoney(billListItemItem.getPresentMoney())
                        // 会员价优惠金额
                        .memberMoney(billListItemItem.getMemberMoney())
                        // 促销优惠金额
                        .promoteMoney(billListItemItem.getPromoteMoney())
                        // 定额优惠
                        .discFix(billListItemItem.getDiscFix())
                        // 抹零金额
                        .wipeMoney(billListItemItem.getWipeMoney())
                        // 实收金额
                        .lastTotal(billListItemItem.getLastTotal())
                        // 纯收金额
                        .incomeTotal(billListItemItem.getIncomeMoney())
                        // 非纯收金额
                        .notIncomeMoney(billListItemItem.getNotIncomeMoney())
                        // 品项类型
                        .itemType(billListItemItem.getItemType())
                        // 套餐标识
                        .pkgFlg(billListItemItem.getPkgFlg())
                        // 套餐标志名称
                        .pkgFlgName(billListItemItem.getPkgFlgName())
                        // 套餐明细所属的服务内容 该字段若不为空，则对应相应品项消费明细的sc_id（即主套餐）
                        .pkgScId(billListItemItem.getPkgScId())
                        // 0：无；1：赠；2：折；3、变；4、促；5、会
                        .discFlg(billListItemItem.getDiscFlg())
                        // 优惠类型名称
                        .discName(billListItemItem.getDiscName())
                        // 赠单原因
                        .rzName(billListItemItem.getRzName())
                        // 删除标记
                        .delflg(billListItemItem.getDelflg())
                        // 品项小类ID
                        .smallClassId(billListItemItem.getSmallClassId())
                        // 品项小类编号
                        .smallClassCode(billListItemItem.getSmallClassCode())
                        // 品项小类名称
                        .smallClassName(billListItemItem.getSmallClassName())
                        // 品项大类ID
                        .bigClassId(billListItemItem.getBigClassId())
                        // 品项大类编号
                        .bigClassCode(billListItemItem.getBigClassCode())
                        // 品项大类名称
                        .bigClassName(billListItemItem.getBigClassName())
                        // 品项小类店内税率
                        .taxRateDinein(billListItemItem.getTaxRateDinein())
                        // 品项小类外卖税率
                        .taxRateTakeout(billListItemItem.getTaxRateTakeout())
                        // 品项小类外带税率
                        .taxRateTakesale(billListItemItem.getTaxRateTakesale())
                        // 做法ID
                        .methodId(billListItemItem.getMethodId())
                        // 做法描述
                        .methodText(billListItemItem.getMethodText())
                        // 毛利部门ID
                        .profitDeptId(billListItemItem.getDeptId())
                        // 毛利部门编号
                        .profitDeptCode(billListItemItem.getDeptCode())
                        // 毛利部门名称
                        .profitDeptName(billListItemItem.getDeptName())
                        // 创建人
                        .createBy("system")
                        // 创建时间
                        .createTime(LocalDateTime.now())
                        // 更新人
                        .updateBy("system")
                        // 更新时间
                        .updateTime(LocalDateTime.now())
                        .build();
                    // 2.3.2 新增到返回值中.
                    tcShopBillingDetailItemEntityList.add(tcShopBillingDetailItemEntity);
                }
            }

            // 2.5 获取settleDetail
            List<SettleDetail> settleDetail = billListItem.getSettleDetail();
            // 如果settleDetail不为空,则遍历settleDetail,转换为TcShopBillingSettleDetailEntity对象.
            if (!CollectionUtils.isEmpty(settleDetail)) {
                for (SettleDetail settleDetailItem : settleDetail) {
                    // 2.5.1 创建tcShopBillingSettleDetailEntity对象.
                    String tsId = settleDetailItem.getTsId();
                    TcShopBillingSettleDetailEntity tcShopBillingSettleDetailEntity = TcShopBillingSettleDetailEntity.builder()
                        // id
                        .id(tsId)
                        // 集团id
                        .centerId(centerId)
                        // 天财门店id
                        .shopId(shopId)
                        // 门店实时账单明细表id
                        .tcShopBillingDetailId(id)
                        // 结算流水ID
                        .tsId(tsId)
                        // 所属营业流水
                        .bsId(settleDetailItem.getBsId())
                        // 结算状态 1:正常结算;-1:表示返位结算;-2:表示预结（即结算中）
                        .settleState(settleDetailItem.getSettleState())
                        // 结算方式ID
                        .paywayId(settleDetailItem.getPaywayId())
                        // 结算方式代码
                        .paywayCode(settleDetailItem.getPaywayCode())
                        // 结算方式名称
                        .paywayName(settleDetailItem.getPaywayName())
                        // 支付金额（实收）
                        .payMoney(settleDetailItem.getPayMoney())
                        // 收入金额(纯收)
                        .incomeMoney(settleDetailItem.getIncomeMoney())
                        // 非纯收金额
                        .notIncomeMoney(settleDetailItem.getNotIncomeMoney())
                        // 会员手机号
                        .crmMobile(settleDetailItem.getCrmMobile())
                        // 会员卡号
                        .cardNo(settleDetailItem.getCardNo())
                        // 删除标记
                        .delflg(settleDetailItem.getDelflg())
                        // 创建人
                        .createBy("system")
                        // 创建时间
                        .createTime(LocalDateTime.now())
                        // 更新人
                        .updateBy("system")
                        // 更新时间
                        .updateTime(LocalDateTime.now())
                        .build();
                    // 2.5.2 新增到返回值中.
                    tcShopBillingSettleDetailEntityList.add(tcShopBillingSettleDetailEntity);
                }
            }
        }

        // 返回结果.
        return MergeEntity.builder()
            .tcShopBillingDetailEntityList(tcShopBillingDetailEntityList)
            .tcShopBillingDetailItemEntityList(tcShopBillingDetailItemEntityList)
            .tcShopBillingSettleDetailEntityList(tcShopBillingSettleDetailEntityList)
            .build();
    }

    /**
     * merge entity 内部类
     *
     * @author steven
     * &#064;date  2023/12/10
     */
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MergeEntity {

        /**
         * tc shop a billing detail entity list
         */
        List<TcShopBillingDetailEntity> tcShopBillingDetailEntityList;

        /**
         * tc shop billing detail item entity list
         */
        List<TcShopBillingDetailItemEntity> tcShopBillingDetailItemEntityList;


        /**
         * tc shop billing settle detail entity list
         */
        List<TcShopBillingSettleDetailEntity> tcShopBillingSettleDetailEntityList;


    }

}
