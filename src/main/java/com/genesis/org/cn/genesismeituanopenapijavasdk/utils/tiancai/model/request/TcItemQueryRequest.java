package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.request;

import lombok.Data;

/**
 * query item info data request
 */
@Data
public class TcItemQueryRequest {
    /**
     * 餐饮集团ID
     */
    private String centerId;

    /**
     * 合作店门店ID
     */
    private String shopId;

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

    /**
     * 分页号码
     */
    private Integer pageNo;

    /**
     * 分页大小（最多300 大于300系统默认300）
     */
    private Integer pageSize;
}
