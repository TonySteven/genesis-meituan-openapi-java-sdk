package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.MtShopCommentEntity;

/**
 * MtShopComment表数据层接口
 *
 * @author 人工智能
 * &#064;date  2023-10-29 15:12:23
 */
public interface IMtShopCommentDao extends IService<MtShopCommentEntity> {

    /**
     * query last one by shop id
     *
     * @return {@link String}
     */
    String queryLastOneByShopId();

}
