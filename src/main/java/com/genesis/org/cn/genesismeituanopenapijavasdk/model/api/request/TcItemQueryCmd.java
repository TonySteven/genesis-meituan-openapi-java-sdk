package com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request;

import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.request.TcItemQueryRequest;
import lombok.Data;

/**
 * tc item query cmd
 */
@Data
public class TcItemQueryCmd {

    /**
     * 大类ID
     */
    private String bigClassId;

    /**
     * 小类ID
     */
    private String smallClassId;

    /**
     * 品牌ID
     */
    private String brandId;

    /**
     * 最近同步时间，用于增量同步品项档案修改记录，格式：yyyy-MM-dd HH:mm:ss
     */
    private String lastTime;

    public TcItemQueryRequest toRequestByCmd(){
        TcItemQueryRequest request = new TcItemQueryRequest();
        request.setBigClassId(this.getBigClassId());
        request.setSmallClassId(this.getSmallClassId());
        request.setBrandId(this.getBrandId());
        request.setLastTime(this.getLastTime());
        return request;
    }
}
