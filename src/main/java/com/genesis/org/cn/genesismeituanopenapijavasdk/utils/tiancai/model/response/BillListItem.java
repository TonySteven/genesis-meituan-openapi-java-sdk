package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * bill list item
 *
 * @author steven
 * &#064;date  2023/12/19
 */
@Data
public class BillListItem {

    /**
     * bs id
     */
    private String bsId;
    /**
     * bs code
     */
    private String bsCode;
    /**
     * ts code
     */
    private String tsCode;
    /**
     * owo open id
     */
    private String owoOpenId;
    /**
     * owo union id
     */
    private String owoUnionId;
    /**
     * btpw user account
     */
    private String btpwUserAccount;
    /**
     * area code
     */
    private String areaCode;
    /**
     * area id
     */
    private String areaId;
    /**
     * area name
     */
    private String areaName;
    /**
     * point code
     */
    private String pointCode;
    /**
     * point name
     */
    private String pointName;
    /**
     * people qty
     */
    private Integer peopleQty;
    /**
     * open time
     */
    private String openTime;
    /**
     * settle time
     */
    private String settleTime;
    /**
     * settle biz date
     */
    private String settleBizDate;
    /**
     * settle biz month
     */
    private String settleBizMonth;
    /**
     * state
     */
    private Integer state;
    /**
     * trt user id
     */
    private String trtUserId;
    /**
     * waiter code
     */
    private String waiterCode;
    /**
     * waiter name
     */
    private String waiterName;
    /**
     * trt salesman id
     */
    private String trtSalesmanId;
    /**
     * salesman code
     */
    private String salesmanCode;
    /**
     * salesman name
     */
    private String salesmanName;
    /**
     * item income total
     */
    private BigDecimal itemIncomeTotal;
    /**
     * item orig money
     */
    private BigDecimal itemOrigMoney;
    /**
     * orig server fee
     */
    private BigDecimal origServerFee;
    /**
     * orig zdxfbq
     */
    private BigDecimal origZdxfbq;
    /**
     * orig total
     */
    private BigDecimal origTotal;
    /**
     * disc total
     */
    private BigDecimal discTotal;
    /**
     * disc money
     */
    private BigDecimal discMoney;
    /**
     * quota money
     */
    private BigDecimal quotaMoney;
    /**
     * present money
     */
    private BigDecimal presentMoney;
    /**
     * member money
     */
    private BigDecimal memberMoney;
    /**
     * promote money
     */
    private BigDecimal promoteMoney;
    /**
     * wipe money
     */
    private BigDecimal wipeMoney;
    /**
     * income total
     */
    private BigDecimal incomeTotal;
    /**
     * not income money
     */
    private BigDecimal notIncomeMoney;
    /**
     * sale type id
     */
    private String saleTypeId;
    /**
     * sale type name
     */
    private String saleTypeName;
    /**
     * order origin id
     */
    private String orderOriginId;
    /**
     * order origin name
     */
    private String orderOriginName;
    /**
     * dinner type id
     */
    private String dinnerTypeId;
    /**
     * dinner type name
     */
    private String dinnerTypeName;
    /**
     * settle state
     */
    private String settleState;
    /**
     * order type
     */
    private String orderType;
    /**
     * de from
     */
    private String deFrom;
    /**
     * is continued bill
     */
    private boolean isContinuedBill;
    /**
     * is continued bill name
     */
    private String isContinuedBillName;
    /**
     * tax money
     */
    private BigDecimal taxMoney;
    /**
     * order code
     */
    private String orderCode;
    /**
     * center code
     */
    private String centerCode;
    /**
     * center name
     */
    private String centerName;
    /**
     * brand code
     */
    private String brandCode;
    /**
     * brand name
     */
    private String brandName;
    /**
     * shop code
     */
    private String shopCode;
    /**
     * shop id
     */
    private String shopId;
    /**
     * shop out code
     */
    private String shopOutCode;
    /**
     * shop name
     */
    private String shopName;
    /**
     * last total
     */
    private BigDecimal lastTotal;
    /**
     * is give invoice
     */
    private String isGiveInvoice;
    /**
     * invoice no
     */
    private String invoiceNo;
    /**
     * invoice code
     */
    private String invoiceCode;
    /**
     * create time
     */
    private String createTime;
    /**
     * modify time
     */
    private String modifyTime;
    /**
     * bs remark
     */
    private String bsRemark;
    /**
     * team id
     */
    private String teamId;
    /**
     * pos id
     */
    private String posId;
    /**
     * pos code
     */
    private String posCode;
    /**
     * pos name
     */
    private String posName;
    /**
     * orig wd bs id
     */
    private String origWdBsId;
    /**
     * orig wd bs code
     */
    private String origWdBsCode;
    /**
     * account bill state
     */
    private String accountBillState;
    /**
     * sn
     */
    private String sn;
    /**
     * give change
     */
    private String giveChange;
    /**
     * de no
     */
    private String deNo;
    /**
     * delivery time
     */
    private String deliveryTime;
    /**
     * service fee
     */
    private String serviceFee;
    /**
     * gde member id
     */
    private String gdeMemberId;
    /**
     * member card no
     */
    private String memberCardNo;
    /**
     * member id
     */
    private String memberId;
    /**
     * member name
     */
    private String memberName;
    /**
     * member mobile
     */
    private String memberMobile;
    /**
     * card kind name
     */
    private String cardKindName;
    /**
     * service fee income money
     */
    private BigDecimal serviceFeeIncomeMoney;
    /**
     * service fee not income money
     */
    private BigDecimal serviceFeeNotIncomeMoney;
    /**
     * service fee last total
     */
    private BigDecimal serviceFeeLastTotal;
    /**
     * zdxf income money
     */
    private BigDecimal zdxfIncomeMoney;
    /**
     * third serial
     */
    private String thirdSerial;
    /**
     * table qty
     */
    private String tableQty;
    /**
     * delflg
     */
    private int delflg;

    /**
     * is designates
     */
    private String isDesignates;

    /**
     * item
     */
    private List<BillListItemItem> item;
    /**
     * settle detail
     */
    private List<SettleDetail> settleDetail;
    /**
     * discount detail
     */
    private List<String> discountDetail;
    /**
     * promote detail
     */
    private List<String> promoteDetail;
    /**
     * fulloff detail
     */
    private List<String> fulloffDetail;
    /**
     * ticket detail
     */
    private List<String> ticketDetail;

    public Boolean getIsContinuedBill() {
        return isContinuedBill;
    }
}
