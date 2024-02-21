package com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy.goodlife.settle_ledger;

import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy.BaseCmd;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 抖音分账记录历史查询请求模型
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DySettleLedgerRecordSyncCmd extends BaseCmd {

    /**
     * 抖音 APP ID
     */
    @NotBlank(message = "抖音appId不能为空")
    private String appId;

    /**
     * 企业号商家总店id（验券准备接口中返回）
     */
    @NotBlank(message = "企业号商家总店id不能为空")
    private String accountId;

    /**
     * 门店id列表，不传默认返回商家所有门店核销记录，多个值使用,拼接
     */
    private List<String> poiIds;

    /**
     * 券id列表
     */
    private List<String> certificateIds;

    /**
     * 起始时间，不传表示今天
     */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 截止时间
     */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}
