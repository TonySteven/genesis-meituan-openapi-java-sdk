package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcPaywayDetailResponse;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@TableName("tc_payway_detail")
public class TcPaywayDetailEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 结算方式id */
    @TableId("payway_id")
    private String paywayId;

    /**
     * 集团ID
     */
    @TableId("center_id")
    private String centerId;

    /** 结算方式编号 */
    @TableField("payway_code")
    private String paywayCode;

    /** 结算方式名称 */
    @TableField("payway_name")
    private String paywayName;

    /** 支付方式分组id */
    @TableField("payway_group_id")
    private String paywayGroupId;

    /** 结算方式类型id */
    @TableField("payway_type_id")
    private String paywayTypeId;

    /** 所属结算方式id */
    @TableField("belong_payway_id")
    private String belongPaywayId;

    /** 是否启用 */
    @TableField("is_enable")
    private Integer isEnable;

    /** 备注 */
    @TableField("remark")
    private String remark;

    /** 是否启用有效期设置 */
    @TableField("is_enable_shop_time_limit")
    private Integer isEnableShopTimeLimit;

    /** 券面值  结算方式类型为代金时有效,其他类型无效 */
    @TableField("ticket_value")
    private BigDecimal ticketValue;

    /** 纯收金额  结算方式类型为代金时有效,其他类型无效 */
    @TableField("income_money")
    private BigDecimal incomeMoney;

    /** 开票金额  结算方式中设置的开票金额(为空或者不存在,说明没有开票金额) */
    @TableField("invoice_amount")
    private BigDecimal invoiceAmount;

    /** 删除标识  0表示正常（默认），1表示在回收站，2表示从回收站删除（不可恢复） */
    @TableField("delflg")
    private Integer delflg;

    /** 创建店编号 */
    @TableField("create_shop_code")
    private String createShopCode;

    /** 创建店名称 */
    @TableField("create_shop_name")
    private String createShopName;

    /** 集团编号 */
    @TableField("center_code")
    private String centerCode;

    /** 集团名称 */
    @TableField("center_name")
    private String centerName;

    /** 品牌编号 */
    @TableField("brand_code")
    private String brandCode;

    /** 品牌名称 */
    @TableField("brand_name")
    private String brandName;

    /** 创建时间 */
    @TableField("create_time")
    private LocalDateTime createTime;

    /** 最后修改时间 */
    @TableField("modify_time")
    private LocalDateTime modifyTime;

    public static List<TcPaywayDetailEntity> toEntityListByResponse(List<TcPaywayDetailResponse> responseList, String centerId) {
        List<TcPaywayDetailEntity> entityList = new ArrayList<>();
        for (TcPaywayDetailResponse response : responseList) {
            entityList.add(toEntityByResponse(response,centerId));
        }
        return entityList;
    }

    public static TcPaywayDetailEntity toEntityByResponse(TcPaywayDetailResponse response, String centerId) {
        TcPaywayDetailEntity entity = new TcPaywayDetailEntity();
        entity.setCenterId(centerId);
        entity.setPaywayId(response.getPayway_id());
        entity.setPaywayCode(response.getPayway_code());
        entity.setPaywayName(response.getPayway_name());
        entity.setPaywayGroupId(response.getPayway_group_id());
        entity.setPaywayTypeId(response.getPayway_type_id());
        entity.setBelongPaywayId(response.getBelong_payway_id());
        entity.setIsEnable(Boolean.TRUE.equals(response.getIs_enable()) ? 1 : 0);
        entity.setRemark(response.getRemark());
        entity.setIsEnableShopTimeLimit(Boolean.TRUE.equals(response.getIs_enable_shop_time_limit()) ? 1 : 0);
        entity.setTicketValue(response.getTicket_value());
        entity.setIncomeMoney(response.getIncome_money());
        entity.setInvoiceAmount(response.getInvoice_amount());
        entity.setDelflg(response.getDelflg());
        entity.setCreateShopCode(response.getCreate_shop_code());
        entity.setCreateShopName(response.getCreate_shop_name());
        entity.setCenterCode(response.getCenter_code());
        entity.setCenterName(response.getCenter_name());
        entity.setBrandCode(response.getBrand_code());
        entity.setBrandName(response.getBrand_name());
        entity.setCreateTime(response.getCreate_time());
        entity.setModifyTime(response.getModify_time());
        return entity;
    }
}
