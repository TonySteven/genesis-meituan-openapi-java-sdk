package com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * mt shop comment qry cmd
 * 购买发票添加cmd
 * 美团查询评论入参
 *
 * @author steven
 * &#064;date  2023/10/29
 */
@Data
public class MtShopCommentQryCmd {

    /**
     * 门店id list
     */
    @ApiModelProperty("门店ids 默认不穿查询全部id")
    private List<String> shopIds;

    @ApiModelProperty(value = "开始时间 yyyyMMdd格式 实例：20210101")
    @NotBlank(message = "开始时间不能为空")
    private String beginDate;

    @ApiModelProperty(value = "结束时间 yyyyMMdd格式 实例：20210101")
    @NotBlank(message = "结束时间不能为空")
    private String endDate;

}
