package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 保存流程返回结果
 *
 * @author steven
 * &#064;date  2023/10/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseDataResponse {

    /**
     * type
     */
    private String type;
    /**
     * message
     */
    private String message;
    /**
     * date
     */
    private String date;
    /**
     * spent
     */
    private int spent;
    /**
     * size
     */
    private int size;
    /**
     * count
     */
    private int count;
    /**
     * position
     */
    private int position;

}
