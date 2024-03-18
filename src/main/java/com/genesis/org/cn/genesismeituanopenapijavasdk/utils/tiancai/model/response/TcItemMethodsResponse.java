package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 品项类别信息
 * @date : 2024-03-15 10:03:23
 */
@Data
public class TcItemMethodsResponse {

    /** 类别id */
    private String id;

    /** 类别编号 */
    private String code;

    /** 类别名称 */
    private String name;

    /** 类别名称拼音 */
    private String pinyin;

    /** 顺序号 */
    private Integer sort_order;

    /** 品项专用做法  true:是，false:否 */
    private Boolean is_only_for_item;

    /** 所属类别id */
    private String parent_id;

    /** 所属类别名称 */
    private String parent_name;

    /** 是否计费  true:是，false:否 */
    private Boolean is_need_fee;

    /** 制作费用 */
    private BigDecimal fee;

    /** 费用与数量有关 */
    private Boolean is_rela_count;

    /** 费用是否可修改 */
    private Boolean is_fee_editable;

    /** 删除标识  0表示正常（默认），1表示在回收站，2表示从回收站删除（不可恢复） */
    private Integer delflg;

    /** 是否可以创建临时品项  true：可以创建临时品项，false:不可创建临时品项 */
    private Boolean is_create_temp_item;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime create_time;

    /** 最后修改时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime modify_time;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TcItemMethodsResponse that = (TcItemMethodsResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(create_shop_code, that.create_shop_code) && Objects.equals(center_code, that.center_code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, create_shop_code, center_code);
    }
}
