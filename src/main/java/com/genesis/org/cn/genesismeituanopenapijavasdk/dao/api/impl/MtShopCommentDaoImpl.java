package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bty.scm.boot.mybatis.base.BaseDaoImpl;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.IMtShopCommentDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.MtShopCommentEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.mapper.MtShopCommentMapper;
import org.springframework.stereotype.Repository;

/**
 * MtShopComment表数据层实现
 *
 * @author 人工智能
 * &#064;date  2023-10-29 15:12:23
 */
@Repository
public class MtShopCommentDaoImpl extends BaseDaoImpl<MtShopCommentMapper, MtShopCommentEntity> implements IMtShopCommentDao {

    /**
     * query last one by shop id
     *
     * @return {@link String}
     */
    @Override
    public String queryLastOneByShopId() {
        // 根据 order shop_id by desc limit 1
        QueryWrapper<MtShopCommentEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true, false, "shop_id");
        queryWrapper.last("limit 1");
        return this.baseMapper.selectList(queryWrapper).get(0).getShopId();
    }
}
