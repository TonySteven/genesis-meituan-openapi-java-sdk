package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

 /**
 * 门店限制列表
 * @date : 2024-03-15 13:07:52
 */
@Data
public class TcPaywayDetailShopResponse {

    /** 结算方式id */
    private String payway_id;

    /** 集团编号 */
    private String center_code;

    /** 门店id */
    private String shop_id;

    /** 有效期限制的开始时间  只有is_enable_shop_time_limit(启用有效期设置)为true时,才有值 */
    private LocalDateTime limit_begin_time;

    /** 有效期限制的结束时间  只有is_enable_shop_time_limit(启用有效期设置)为true时,才有值 */
    private LocalDateTime limit_end_time;

    /** 开票金额  开票设置中的开票金额(为空或者不存在,说明门店没有设置该结算方式的开票金额) */
    private BigDecimal invoice_amount;

}
