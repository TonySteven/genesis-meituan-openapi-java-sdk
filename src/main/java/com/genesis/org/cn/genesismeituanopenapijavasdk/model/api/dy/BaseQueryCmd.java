package com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.dy;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 抖音查询基础请求模型
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseQueryCmd extends BaseCmd {

    /**
     * 游标，传前一页最后一条记录的游标（首页传0）
     */
    private String cursor = "0";

    /**
     * 页大小，取值范围1～20
     */
    private Integer size = 20;

    /**
     * 起始时间戳，单位秒，不传表示今天
     */
    private Long startTime;

    /**
     * 截止时间戳，单位秒
     */
    private Long endTime;

    public Integer getSize() {
        if(size == null){
            size = 20;
        }
        return size;
    }
}
