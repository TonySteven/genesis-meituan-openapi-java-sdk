package com.genesis.org.cn.genesismeituanopenapijavasdk.api.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * base vo
 *
 * @author steven
 * &#064;date  2023/10/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("base vo")
public class BaseVO {

    @ApiModelProperty(value = "单据编号")
    private String id;

    @ApiModelProperty(value = "状态（成功/失败/处理中）")
    private String state;

    @ApiModelProperty(value = "原因")
    private String msg;
}
