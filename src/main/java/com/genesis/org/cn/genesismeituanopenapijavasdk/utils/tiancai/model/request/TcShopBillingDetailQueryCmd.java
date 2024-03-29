package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * tc shop billing detail query cmd
 *
 * @author steven
 * &#064;date  2023/12/30
 */
@Data
public class TcShopBillingDetailQueryCmd {

    /**
     * shop id
     */
    @ApiModelProperty(name = "门店id", notes = "如果不传则查询所有门店,从头开始,传了则从传入的门店开始查询,不传则查询所有门店,从头开始")
    private String shopId;

    /**
     * shop id List
     */
    @ApiModelProperty(name = "门店idList", notes = "如果不传则查询所有门店,如果传了则查询传入的门店,不传则查询所有门店")
    private List<String> shopIdList;

    /**
     * begin date
     */
    @ApiModelProperty(name = "开始时间", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "开始时间不能为空")
    private String beginDate;

    /**
     * end date
     */
    @ApiModelProperty(name = "结束时间", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "结束时间不能为空")
    private String endDate;

}
