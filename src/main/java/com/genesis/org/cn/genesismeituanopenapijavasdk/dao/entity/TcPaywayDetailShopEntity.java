package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcPaywayDetailResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcPaywayDetailShopResponse;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @date : 2024-03-15 11:35:20
 */
@Data
@TableName("tc_payway_detail_shop")
public class TcPaywayDetailShopEntity implements Serializable{

     @Serial
     private static final long serialVersionUID = 1L;

    /** id */
    @TableId("id")
    private String id;

    /** 结算方式id */
    @TableField("payway_id")
    private String paywayId;

    /**
     * 集团ID
     */
    @TableField("center_id")
    private String centerId;

    /** 门店id */
    @TableField("shop_id")
    private String shopId;

    /** 有效期限制的开始时间  只有is_enable_shop_time_limit(启用有效期设置)为true时,才有值 */
    @TableField("limit_begin_time")
    private LocalDateTime limitBeginTime;

    /** 有效期限制的结束时间  只有is_enable_shop_time_limit(启用有效期设置)为true时,才有值 */
    @TableField("limit_end_time")
    private LocalDateTime limitEndTime;

    /** 开票金额  开票设置中的开票金额(为空或者不存在,说明门店没有设置该结算方式的开票金额) */
    @TableField("invoice_amount")
    private BigDecimal invoiceAmount;

    public static List<TcPaywayDetailShopEntity> toEntityListByResponse(String centerId,TcPaywayDetailResponse detailResponse)
    {
        if(ObjectUtils.isNotEmpty(detailResponse.getShopList())){
            List<TcPaywayDetailShopEntity> entityList = new ArrayList<>();
            for (TcPaywayDetailShopResponse response : detailResponse.getShopList()) {
                entityList.add(toEntityByResponse(centerId,detailResponse,response));
            }
            return entityList;
        }
        return null;
    }

    public static TcPaywayDetailShopEntity toEntityByResponse(String centerId,TcPaywayDetailResponse detailResponse,TcPaywayDetailShopResponse response)
    {
        TcPaywayDetailShopEntity entity = new TcPaywayDetailShopEntity();
        entity.setPaywayId(detailResponse.getPayway_id());
        entity.setCenterId(centerId);
        entity.setShopId(response.getShop_id());
        entity.setLimitBeginTime(response.getLimit_begin_time());
        entity.setLimitEndTime(response.getLimit_end_time());

        return entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TcPaywayDetailShopEntity that = (TcPaywayDetailShopEntity) o;
        return Objects.equals(paywayId, that.paywayId)
            && Objects.equals(centerId, that.centerId)
            && Objects.equals(shopId, that.shopId)
            && Objects.equals(limitBeginTime, that.limitBeginTime)
            && Objects.equals(limitEndTime, that.limitEndTime)
            && Objects.equals(invoiceAmount, that.invoiceAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paywayId, centerId, shopId, limitBeginTime, limitEndTime, invoiceAmount);
    }
}
