package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * query category info data response
 *
 * @author LP
 */
@Data
public class TcItemCategoryResponse {

    /**
     * 类别ID
     */
    private String class_id;

    /**
     * 类别编号
     */
    private String class_code;

    /**
     * 类别名称
     */
    private String class_name;

    /**
     * 删除标识 0表示正常（默认），1表示在回收站，2表示从回收站删除（不可恢复）
     */
    private Integer delflg;

    /**
     * 级别 0:大类；1:小类
     */
    private Integer level;

    /**
     * 顶级默认为0
     */
    private String parent_id;

    /**
     * 是否可以创建临时品项 true：可以创建临时品项，false:不可创建临时品项
     */
    private Boolean is_create_temp_item;

    /**
     * 创建店编号
     */
    private String create_shop_code;

    /**
     * 创建店名称
     */
    private String create_shop_name;

    /**
     * 集团编号
     */
    private String center_code;

    /**
     * 集团名称
     */
    private String center_name;

    /**
     * 品牌编号
     */
    private String brand_code;

    /**
     * 品牌名称
     */
    private String brand_name;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime create_time;

    /**
     * 最后修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime modify_time;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TcItemCategoryResponse that = (TcItemCategoryResponse) o;
        return Objects.equals(class_id, that.class_id) && Objects.equals(center_code, that.center_code) && Objects.equals(brand_code, that.brand_code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(class_id, center_code, brand_code);
    }
}
