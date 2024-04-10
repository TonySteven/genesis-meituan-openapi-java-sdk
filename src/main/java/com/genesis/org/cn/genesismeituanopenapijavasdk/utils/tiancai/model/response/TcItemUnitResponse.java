package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import lombok.Data;


/**
 * 品项单位
 */
@Data
public class TcItemUnitResponse {

    /**
     * ID
     */
    private String id;

    /**
     * 集团ID
     */
    private String center_id;

    /**
     * 单位编码
     */
    private String code;

    /**
     * 单位名称
     */
    private String name;

    /**
     * 单位拼音
     */
    private String pinyin;

    /**
     * 删除标识 0表示正常（默认），1表示在回收站，2表示从回收站删除（不可恢复）
     */
    private Integer delflg;
}
