package com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * kingdee payable bill called cmd
 * 金蝶调用应付单入参 没三天调用一次
 *
 * @author steven
 * &#064;date  2024/03/17
 */
@Data
public class KingdeePayableBillCalledCmd {

    /**
     * 门店名称
     */
    @ApiModelProperty("门店名称, 如果不传查询全部门店")
    private String shopName;

    /**
     * supplier code
     */
    @ApiModelProperty("供应商code")
    private String supplierCode;
}
