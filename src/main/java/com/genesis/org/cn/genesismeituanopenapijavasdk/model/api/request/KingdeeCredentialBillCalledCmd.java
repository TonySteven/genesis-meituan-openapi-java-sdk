package com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * kingdee payable bill called cmd
 * 金蝶调用凭证入参 没三天调用一次
 *
 * @author steven
 * &#064;date  2024/03/17
 */
@Data
public class KingdeeCredentialBillCalledCmd {

    /**
     * 门店名称
     */
    @ApiModelProperty("门店名称, 如果不传查询全部门店")
    private List<String> shopNameList;

    /**
     * supplier code
     */
    @ApiModelProperty("供应商code")
    private List<String> supplierCodeList;
}
