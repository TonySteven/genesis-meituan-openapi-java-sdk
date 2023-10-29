package com.genesis.org.cn.genesismeituanopenapijavasdk.api.request;

import com.bty.scm.boot.jointblock.validator.IValidate;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * mt shop comment qry cmd
 * 购买发票添加cmd
 * 美团查询评论入参
 *
 * @author steven
 * &#064;date  2023/10/29
 */
@Data
public class MtShopCommentQryCmd implements IValidate {

    /**
     * 门店id
     */
    @ApiModelProperty("门店id")
    private @NotBlank String shopId;

    @ApiModelProperty(value = "开始时间 yyyyMMdd格式 实例：20210101")
    private Date beginTime;

    @ApiModelProperty(value = "结束时间 yyyyMMdd格式 实例：20210101")
    private Date endTime;

}
