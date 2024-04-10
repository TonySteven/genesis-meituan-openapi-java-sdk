package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.DateUtils;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcScmDjmxResponse;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 门店(中心)库存流水集市(过滤了配送中心的单据)
 * </p>
 *
 * @author LiPei
 * @since 2024-04-07
 */
@Data
@TableName("scm_djmx")
public class ScmDjmxEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 对方机构名称
     */
    @TableField("OtherSideName")
    private String otherSideName;

    /**
     * 机构名称
     */
    @TableField("ShopName")
    private String shopName;

    /**
     * 品项大类名称
     */
    @TableField("ItemBigClassName")
    private String itemBigClassName;

    @TableField("ItemAssistNO")
    private String itemAssistNO;

    /**
     * 品项规格
     */
    @TableField("ItemSpec")
    private String itemSpec;

    /**
     * 销售/调拨/配送，税率
     */
    @TableField("SellTaxes")
    private String sellTaxes;

    /**
     * 门店加盟标志 0：否 ，1：是
     */
    @TableField("shopJoinFlag")
    private String shopJoinFlag;

    /**
     * 品项类别编码
     */
    @TableField("ItemSmallClassCode")
    private String itemSmallClassCode;

    /**
     * EDI发货金额
     */
    @TableField("ArriveMoney")
    private String arriveMoney;

    /**
     * 仓库编码
     */
    @TableField("StoreCode")
    private String storeCode;

    /**
     * 产出率
     */
    @TableField("outputRate")
    private String outputRate;

    @TableField("StoreBillRemark")
    private String storeBillRemark;

    /**
     * 最小单位出库数量
     */
    @TableField("OutMainAmount")
    private String outMainAmount;

    /**
     * 品项助记符
     */
    @TableField("ItemSign")
    private String itemSign;

    /**
     * 门店编码
     */
    @TableField("ShopCode")
    private String shopCode;

    /**
     * 税额（成本)
     */
    @TableField("TaxMoney")
    private String taxMoney;

    /**
     * 对方仓库id
     */
    @TableField("otherStoreID")
    private String otherStoreID;

    /**
     * 单据明细id
     */
    @TableField("StoreBillDTID")
    private String storeBillDTID;

    /**
     * 修改时间
     */
    @TableField("modifyDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime modifyDate;

    /**
     * 仓库名称
     */
    @TableField("StoreName")
    private String storeName;

    @TableField("StoreBillIDRemark")
    private String storeBillIDRemark;

    /**
     * 最小单位
     */
    @TableField("MainUnit")
    private String mainUnit;

    /**
     * 赠品标记
     */
    @TableField("GiftFlag")
    private String giftFlag;

    /**
     * 出库金额（成本）
     */
    @TableField("OutStoreMoney")
    private BigDecimal outStoreMoney;

    /**
     * 财务分类编码
     */
    @TableField("FanceTypeCode")
    private String fanceTypeCode;

    /**
     * 品项id
     */
    @TableField("ItemID")
    private String itemID;

    /**
     * 品项类别名称
     */
    @TableField("ItemSmallClassName")
    private String itemSmallClassName;

    /**
     * 对方仓库名称
     */
    @TableField("otherStoreName")
    private String otherStoreName;

    /**
     * 单号
     */
    @TableField("BillNO")
    private String billNO;

    /**
     * 整单复审状态
     */
    @TableField("StoreBillReState")
    private String storeBillReState;

    /**
     * 业务人
     */
    @TableField("BusUser")
    private String busUser;

    /**
     * 税率
     */
    @TableField("Taxes")
    private String taxes;

    /**
     * 批次号
     */
    @TableField("BatchCode")
    private String batchCode;

    /**
     * 入库金额（成本）
     */
    @TableField("InStoreMoney")
    private String inStoreMoney;

    /**
     * 是否是出库类型
     */
    @TableField("isOut")
    private String isOut;

    /**
     * 门店id
     */
    @TableField("ShopID")
    private String shopID;

    /**
     * 生产日期
     */
    @TableField("MakeTime")
    private String makeTime;

    /**
     * 单据业务类型（见附：单据业务类型）
     */
    @TableField("BillBusType")
    private String billBusType;

    /**
     * 加工费
     */
    @TableField("processMoney")
    private String processMoney;

    /**
     * 品项编码
     */
    @TableField("ItemCode")
    private String itemCode;

    /**
     * 下推出库金额
     */
    @TableField("PushOutStoreMoney")
    private String pushOutStoreMoney;

    /**
     * 最小单位入库数量
     */
    @TableField("InMainAmount")
    private String inMainAmount;

    /**
     * 下推出库数量
     */
    @TableField("PushOutStoreAmount")
    private String pushOutStoreAmount;

    /**
     * 主单据id
     */
    @TableField("StoreBillID")
    private String storeBillID;

    /**
     * 销售/调拨/配送，含税金额
     */
    @TableField("SellIncludeTaxMoney")
    private BigDecimal sellIncludeTaxMoney;

    /**
     * 业务单位出库数量
     */
    @TableField("OutBusAmount")
    private String outBusAmount;

    /**
     * 对方机构类型
     */
    @TableField("OtherSideType")
    private String otherSideType;

    /**
     * 下推入库金额
     */
    @TableField("PushInStoreMoney")
    private String pushInStoreMoney;

    @TableField("transMoney")
    private String transMoney;

    /**
     * 出成率
     */
    @TableField("yield")
    private String yield;

    /**
     * EDI发货数量
     */
    @TableField("ArriveAmount")
    private String arriveAmount;

    /**
     * 品项名称
     */
    @TableField("ItemName")
    private String itemName;

    /**
     * 对方仓库编码
     */
    @TableField("otherStoreCode")
    private String otherStoreCode;

    /**
     * 系统批次号
     */
    @TableField("SysBatchCode")
    private String sysBatchCode;

    /**
     * 创建时间
     */
    @TableField("createDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDate;

    /**
     * 审核时间
     */
    @TableField("auditDate")
    private String auditDate;

    /**
     * 对方机构id
     */
    @TableField("OtherSideID")
    private String otherSideID;

    /**
     * 单据类型
     */
    @TableField("BillType")
    private String billType;

    /**
     * 销售/调拨/配送，调拨税额
     */
    @TableField("SellTaxMoney")
    private String sellTaxMoney;

    /**
     * 单据业务类型
     */
    @TableField("BillBusTypeId")
    private String billBusTypeId;

    /**
     * 下推入库数量
     */
    @TableField("PushInStoreAmount")
    private String pushInStoreAmount;

    /**
     * 业务单位入库数量
     */
    @TableField("InBusAmount")
    private String inBusAmount;

    /**
     * 业务时间
     */
    @TableField("busDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime busDate;

    /**
     * 会计年
     */
    @TableField("APYear")
    private String aPYear;

    /**
     * 品项大类编码
     */
    @TableField("ItemBigClassCode")
    private String itemBigClassCode;

    /**
     * 销售/调拨/配送，非税金额
     */
    @TableField("SaleMoney")
    private BigDecimal saleMoney;

    /**
     * 仓库id
     */
    @TableField("StoreID")
    private String storeID;

    /**
     * 品项ABC类型
     */
    @TableField("ItemAbc")
    private String itemAbc;

    @TableField("ArrivalDate")
    private String arrivalDate;

    /**
     * 会计月
     */
    @TableField("APMonth")
    private String aPMonth;

    /**
     * 财务分类名称
     */
    @TableField("FinanceTypeName")
    private String financeTypeName;

    /**
     * 对方机构编码
     */
    @TableField("OtherSideCode")
    private String otherSideCode;

    /**
     * 业务单位（单据单位）
     */
    @TableField("BusUnit")
    private String busUnit;

    /**
     * 含税金额（成本）
     */
    @TableField("IncludeTaxMoney")
    private String includeTaxMoney;

    @TableField("ParentBillNO")
    private String parentBillNO;

    @TableField("bomExpend")
    private String bomExpend;

    /**
     * 对方机构加盟标志 0：否 ，1：是
     */
    @TableField("otherJoinFlag")
    private String otherJoinFlag;

    @TableField("theoryMoney")
    private String theoryMoney;

    /**
     * 参考单价
     */
    @TableField("referencePrice")
    private String referencePrice;

    /**
     * 参考金额
     */
    @TableField("referenceMoney")
    private String referenceMoney;

    @TableField("OutAssistAmount")
    private String outAssistAmount;

    /**
     * 直通供货商id
     */
    @TableField("ztSupplierID")
    private String ztSupplierID;

    /**
     * 直通供货商名称
     */
    @TableField("ztSupplierName")
    private String ztSupplierName;

    /**
     * 直通供货商编码
     */
    @TableField("ztSupplierCode")
    private String ztSupplierCode;

    @TableField("arriveAssistAmount")
    private String arriveAssistAmount;

    /**
     * 实际成本金额
     */
    @TableField("realCostMoney")
    private BigDecimal realCostMoney;

    /**
     * 主键
     */
    @TableId("zhujian")
    private String zhujian;

    /**
     * 单据类型 (见附：单据业务类型)
     */
    @TableField("BillTypeId")
    private String billTypeId;

    @TableField("unifiedOutBillNo")
    private String unifiedOutBillNo;

    @TableField("reAuditUserName")
    private String reAuditUserName;

    @TableField("reAuditUserId")
    private String reAuditUserId;

    @TableField("reAuditDate")
    private String reAuditDate;

    @TableField("reAuditUserCode")
    private String reAuditUserCode;

    @TableField("printCs")
    private String printCs;

    @TableField("rdcPrice")
    private BigDecimal rdcPrice;

    @TableField("expDays")
    private String expDays;

    @TableField("AssistUnit")
    private String assistUnit;

    @TableField("outBusPlanAmount")
    private String outBusPlanAmount;

    @TableField("receiveMobile")
    private String receiveMobile;

    @TableField("receiveName")
    private String receiveName;

    @TableField("receiveAddr")
    private String receiveAddr;

    @TableField("noTransMoney")
    private String noTransMoney;

    @TableField("noTransInTaxesMoney")
    private String noTransInTaxesMoney;

    @TableField("InAssistAmount")
    private String inAssistAmount;



    public static List<ScmDjmxEntity> toEntityListByResponse(List<TcScmDjmxResponse> responseList){
        return responseList.stream().map(ScmDjmxEntity::toEntityByResponse).toList();
    }

    public static ScmDjmxEntity toEntityByResponse(TcScmDjmxResponse response){
        ScmDjmxEntity entity = new ScmDjmxEntity();
        BeanUtils.copyProperties(response, entity);
        entity.setZhujian(response.getStoreBillDTID() + response.getStoreID());

        // 特殊时间类型处理，时间转换
        entity.setCreateDate(DateUtils.parseSpecialLocalDateTime(response.getCreateDate()));
        entity.setBusDate(DateUtils.parseSpecialLocalDateTime(response.getBusDate()));
        entity.setModifyDate(DateUtils.parseSpecialLocalDateTime(response.getModifyDate()));
        entity.setAuditDate(DateUtils.formatSpecialDate(response.getAuditDate()));
        entity.setArrivalDate(DateUtils.formatSpecialDate(response.getArrivalDate()));
        entity.setMakeTime(DateUtils.formatSpecialDate(response.getMakeTime()));
        entity.setReAuditDate(DateUtils.formatSpecialDate(response.getReAuditDate()));
        return entity;
    }
}
