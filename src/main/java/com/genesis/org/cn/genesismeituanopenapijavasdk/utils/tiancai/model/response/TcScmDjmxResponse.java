package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 门店(中心)库存流水集市(过滤了配送中心的单据)
 * </p>
 *
 * @author LiPei
 * @since 2024-04-07
 */
@Data
public class TcScmDjmxResponse {

    /**
     * 对方机构名称
     */
    private String otherSideName;

    /**
     * 机构名称
     */
    private String shopName;

    /**
     * 品项大类名称
     */
    private String itemBigClassName;

    private String itemAssistNO;

    /**
     * 品项规格
     */
    private String itemSpec;

    /**
     * 销售/调拨/配送，税率
     */
    private String sellTaxes;

    /**
     * 门店加盟标志 0：否 ，1：是
     */
    private String shopJoinFlag;

    /**
     * 品项类别编码
     */
    private String itemSmallClassCode;

    /**
     * EDI发货金额
     */
    private String arriveMoney;

    /**
     * 仓库编码
     */
    private String storeCode;

    /**
     * 产出率
     */
    private String outputRate;

    private String storeBillRemark;

    /**
     * 最小单位出库数量
     */
    private String outMainAmount;

    /**
     * 品项助记符
     */
    private String itemSign;

    /**
     * 门店编码
     */
    private String shopCode;

    /**
     * 税额（成本)
     */
    private String taxMoney;

    /**
     * 对方仓库id
     */
    private String otherStoreID;

    /**
     * 单据明细id
     */
    private String storeBillDTID;

    /**
     * 修改时间
     */
    private String modifyDate;

    /**
     * 仓库名称
     */
    private String storeName;

    private String storeBillIDRemark;

    /**
     * 最小单位
     */
    private String mainUnit;

    /**
     * 赠品标记
     */
    private String giftFlag;

    /**
     * 出库金额（成本）
     */
    private BigDecimal outStoreMoney;

    /**
     * 财务分类编码
     */
    private String fanceTypeCode;

    /**
     * 品项id
     */
    private String itemID;

    /**
     * 品项类别名称
     */
    private String itemSmallClassName;

    /**
     * 对方仓库名称
     */
    private String otherStoreName;

    /**
     * 单号
     */
    private String billNO;

    /**
     * 整单复审状态
     */
    private String storeBillReState;

    /**
     * 业务人
     */
    private String busUser;

    /**
     * 税率
     */
    private String taxes;

    /**
     * 批次号
     */
    private String batchCode;

    /**
     * 入库金额（成本）
     */
    private String inStoreMoney;

    /**
     * 是否是出库类型
     */
    private String isOut;

    /**
     * 门店id
     */
    private String shopID;

    /**
     * 生产日期
     */
    private String makeTime;

    /**
     * 单据业务类型（见附：单据业务类型）
     */
    private String billBusType;

    /**
     * 加工费
     */
    private String processMoney;

    /**
     * 品项编码
     */
    private String itemCode;

    /**
     * 下推出库金额
     */
    private String pushOutStoreMoney;

    /**
     * 最小单位入库数量
     */
    private String inMainAmount;

    /**
     * 下推出库数量
     */
    private String pushOutStoreAmount;

    /**
     * 主单据id
     */
    private String storeBillID;

    /**
     * 销售/调拨/配送，含税金额
     */
    private BigDecimal sellIncludeTaxMoney;

    /**
     * 业务单位出库数量
     */
    private String outBusAmount;

    /**
     * 对方机构类型
     */
    private String otherSideType;

    /**
     * 下推入库金额
     */
    private String pushInStoreMoney;

    private String transMoney;

    /**
     * 出成率
     */
    private String yield;

    /**
     * EDI发货数量
     */
    private String arriveAmount;

    /**
     * 品项名称
     */
    private String itemName;

    /**
     * 对方仓库编码
     */
    private String otherStoreCode;

    /**
     * 系统批次号
     */
    private String sysBatchCode;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 审核时间
     */
    private String auditDate;

    /**
     * 对方机构id
     */
    private String otherSideID;

    /**
     * 单据类型
     */
    private String billType;

    /**
     * 销售/调拨/配送，调拨税额
     */
    private String sellTaxMoney;

    /**
     * 单据业务类型
     */
    private String billBusTypeId;

    /**
     * 下推入库数量
     */
    private String pushInStoreAmount;

    /**
     * 业务单位入库数量
     */
    private String inBusAmount;

    /**
     * 业务时间
     */
    private String busDate;

    /**
     * 会计年
     */
    private String aPYear;

    /**
     * 品项大类编码
     */
    private String itemBigClassCode;

    /**
     * 销售/调拨/配送，非税金额
     */
    private BigDecimal saleMoney;

    /**
     * 仓库id
     */
    private String storeID;

    /**
     * 品项ABC类型
     */
    private String itemAbc;

    private String arrivalDate;

    /**
     * 会计月
     */
    private String aPMonth;

    /**
     * 财务分类名称
     */
    private String financeTypeName;

    /**
     * 对方机构编码
     */
    private String otherSideCode;

    /**
     * 业务单位（单据单位）
     */
    private String busUnit;

    /**
     * 含税金额（成本）
     */
    private String includeTaxMoney;

    private String parentBillNO;

    private String bomExpend;

    /**
     * 对方机构加盟标志 0：否 ，1：是
     */
    private String otherJoinFlag;

    private String theoryMoney;

    /**
     * 参考单价
     */
    private String referencePrice;

    /**
     * 参考金额
     */
    private String referenceMoney;

    private String outAssistAmount;

    /**
     * 直通供货商id
     */
    private String ztSupplierID;

    /**
     * 直通供货商名称
     */
    private String ztSupplierName;

    /**
     * 直通供货商编码
     */
    private String ztSupplierCode;

    private String arriveAssistAmount;

    /**
     * 实际成本金额
     */
    private BigDecimal realCostMoney;

    /**
     * 主键
     */
    private String zhujian;

    /**
     * 单据类型 (见附：单据业务类型)
     */
    private String billTypeId;

    private String unifiedOutBillNo;

    private String reAuditUserName;

    private String reAuditUserId;

    private String reAuditDate;

    private String reAuditUserCode;

    private String printCs;

    private BigDecimal rdcPrice;

    private String expDays;

    private String assistUnit;

    private String outBusPlanAmount;

    private String receiveMobile;

    private String receiveName;

    private String receiveAddr;

    private String noTransMoney;

    private String noTransInTaxesMoney;

    private String inAssistAmount;

    public String getZhujian() {
        return this.getStoreBillDTID() + this.getStoreID();
    }
}
