package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * group list
 * query shop info data response
 *
 * @author steven
 * &#064;date  2023/12/10
 * @date 2023/12/10
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class GroupList {

    /**
     * one level id
     */
    private String oneLevelId;
    /**
     * one level code
     */
    private String oneLevelCode;
    /**
     * one level name
     */
    private String oneLevelName;
    /**
     * two level id
     */
    private String twoLevelId;
    /**
     * two level code
     */
    private String twoLevelCode;
    /**
     * two level name
     */
    private String twoLevelName;
    /**
     * three level id
     */
    private String threeLevelId;
    /**
     * three level code
     */
    private String threeLevelCode;
    /**
     * three level name
     */
    private String threeLevelName;
    /**
     * four level id
     */
    private String fourLevelId;
    /**
     * four level code
     */
    private String fourLevelCode;
    /**
     * four level name
     */
    private String fourLevelName;

}
