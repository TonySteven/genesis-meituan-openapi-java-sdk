package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 结算方式信息
 * @date : 2024-03-15 13:05:59
 */
@Data
public class TcPaywayDetailResponse {

    /** 结算方式id */
    private String payway_id;

    /** 结算方式编号 */
    private String payway_code;

    /** 结算方式名称 */
    private String payway_name;

    /** 支付方式分组id */
    private String payway_group_id;

    /** 结算方式类型id */
    private String payway_type_id;

    /** 所属结算方式id */
    private String belong_payway_id;

    /** 是否启用 */
    private Boolean is_enable;

    /** 备注 */
    private String remark;

    /** 是否启用有效期设置 */
    private Boolean is_enable_shop_time_limit;

    /** 券面值  结算方式类型为代金时有效,其他类型无效 */
    private BigDecimal ticket_value;

    /** 纯收金额  结算方式类型为代金时有效,其他类型无效 */
    private BigDecimal income_money;

    /** 开票金额  结算方式中设置的开票金额(为空或者不存在,说明没有开票金额) */
    private BigDecimal invoice_amount;

    /** 删除标识  0表示正常（默认），1表示在回收站，2表示从回收站删除（不可恢复） */
    private Integer delflg;

    /** 创建店编号 */
    private String create_shop_code;

    /** 创建店名称 */
    private String create_shop_name;

    /** 集团编号 */
    private String center_code;

    /** 集团名称 */
    private String center_name;

    /** 品牌编号 */
    private String brand_code;

    /** 品牌名称 */
    private String brand_name;

    /** 创建时间 */
    private LocalDateTime create_time;

    /** 最后修改时间 */
    private LocalDateTime modify_time;

    /**
     * 门店限制列表
     */
    private List<TcPaywayDetailShopResponse> shopList;

}
