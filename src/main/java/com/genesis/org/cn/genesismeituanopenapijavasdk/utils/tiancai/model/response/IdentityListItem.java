package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdentityListItem {

    /**
     * id
     */
    private String id;
    /**
     * pinyin
     */
    private String pinyin;
    /**
     * pinyin initial
     */
    private String pinyinInitial;
    /**
     * description
     */
    private String description;
    /**
     * name
     */
    private String name;
    /**
     * unique
     */
    private String unique;
    /**
     * distinguished name
     */
    private String distinguishedName;
    /**
     * person
     */
    private String person;
    /**
     * unit
     */
    private String unit;
    /**
     * unit name
     */
    private String unitName;
    /**
     * unit level
     */
    private int unitLevel;
    /**
     * unit level name
     */
    private String unitLevelName;
    /**
     * order number
     */
    private int orderNumber;
    /**
     * major
     */
    private boolean major;
    /**
     * create time
     */
    private String createTime;
    /**
     * update time
     */
    private String updateTime;
}
