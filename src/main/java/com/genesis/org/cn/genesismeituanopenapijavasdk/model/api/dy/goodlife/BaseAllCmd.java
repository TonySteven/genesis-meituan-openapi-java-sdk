package com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy.goodlife;

import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy.BaseCmd;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
public class BaseAllCmd extends BaseCmd {

    /**
     * 企业号商家总店id（验券准备接口中返回）
     */
    private List<String> accountIds;

    /**
     * 门店id列表，不传默认返回商家所有门店核销记录，多个值使用,拼接
     */
    private List<String> poiIds;

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
