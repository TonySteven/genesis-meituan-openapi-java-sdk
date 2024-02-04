package com.genesis.org.cn.genesismeituanopenapijavasdk.external.dy.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 抖音查询基础请求模型
 */
@Data
public class BaseQueryRequest implements Serializable {

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
    private Integer start_time;

    /**
     * 截止时间戳，单位秒
     */
    private Integer end_time;

    public String getCursor() {
        if(cursor == null){
            cursor = "0";
        }
        return cursor;
    }

    public Integer getSize() {
        if(size == null){
            size = 20;
        }
        return size;
    }
}
