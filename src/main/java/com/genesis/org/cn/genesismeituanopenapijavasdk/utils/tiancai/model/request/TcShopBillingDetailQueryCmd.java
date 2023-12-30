package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * tc shop billing detail query cmd
 *
 * @author steven
 * &#064;date  2023/12/30
 */
@Data
public class TcShopBillingDetailQueryCmd {

    /**
     * 开始时间
     */
    @ApiModelProperty(name = "开始时间", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "开始时间不能为空")
    private Date beginDate;

    /**
     * 结束时间
     */
    @ApiModelProperty(name = "结束时间", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "结束时间不能为空")
    private Date endDate;

}
