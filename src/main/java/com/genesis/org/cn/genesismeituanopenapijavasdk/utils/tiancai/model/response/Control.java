package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * control
 *
 * @author steven
 * &#064;date  2023/11/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Control {

    /**
     * allow edit
     */
    private boolean allowEdit;

    /**
     * allow delete
     */
    private boolean allowDelete;
}
