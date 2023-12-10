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
public class BasePageInfo {

    /**
     * total size
     */
    private int totalSize;

    /**
     * page total
     */
    private int pageTotal;

    /**
     * page no
     */
    private int pageNo;

    /**
     * page size
     */
    private int pageSize;

}
