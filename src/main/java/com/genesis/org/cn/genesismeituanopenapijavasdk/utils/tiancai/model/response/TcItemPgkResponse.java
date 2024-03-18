package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;
import lombok.Data;

import java.math.BigDecimal;


/**
 * 品项套餐表
 * @date : 2024-03-16 13:45:32
 */
@Data
public class TcItemPgkResponse{


    /**
     * 集团编号
     */
    private String center_code;


    /**
     * 品项id
     */
    private String item_id;


    /**
     * 品项编码
     */
    private String item_code;


    /**
     * 品项名称
     */
    private String item_name;


    /**
     * 规格id
     */
    private String size_id;


    /**
     * 规格名称
     */
    private String size_name;


    /**
     * 套餐id
     */
    private String pkg_id;


    /**
     * 默认数量
     */
    private Integer default_qty;


    /**
     * 标准价格
     */
    private BigDecimal std_price;


    /**
     * 是否按数量加价  true:是，false:否
     */
    private Boolean is_raise_by_qty;


    /**
     * 加价金额
     */
    private BigDecimal raise_price;


    /**
     * 限定数量
     */
    private Integer limited_qty;


    /**
     * 套餐分组id
     */
    private String pkg_class_id;


    /**
     * 套餐分组名称
     */
    private String pkg_class_name;

}
