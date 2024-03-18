package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * query item_method_classes info data response
 *
 * @author LP
 */
@Data
public class TcItemMethodClassesResponse {


    /** 类别id */
    private String id;

    /** 类别编号 */
    private String code;

    /** 类别名称 */
    private String name;

    /** 类别名称拼音 */
    private String pinyin;

    /** 上级类别id */
    private String pid;

    /** 删除标识  0表示正常（默认），1表示在回收站，2表示从回收站删除（不可恢复） */
    private Integer delflg;

    /** 上级类别id  顶级默认为0 */
    private String parent_id;

    /** 创建店编号 */
    private String shop_code;

    /** 创建店名称 */
    private String shop_name;

    /** 集团编号 */
    private String center_code;

    /** 集团名称 */
    private String center_name;

    /** 品牌编号 */
    private String brand_code;

    /** 品牌名称 */
    private String brand_name;

    private Boolean is_system;

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
        TcItemMethodClassesResponse that = (TcItemMethodClassesResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(shop_code, that.shop_code) && Objects.equals(center_code, that.center_code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shop_code, center_code);
    }
}
