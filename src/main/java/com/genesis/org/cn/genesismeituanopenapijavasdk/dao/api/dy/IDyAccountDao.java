package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.dy;


import com.genesis.org.cn.genesismeituanopenapijavasdk.enums.DyAccountEnums;

import java.util.List;

/**
 * <p>
 * 抖音本地生活商家账户信息 服务类
 * </p>
 *
 * @author LiPei
 * @since 2024-02-03
 */
public interface IDyAccountDao {

    /**
     * 获取抖音本地生活商家账户信息
     * @param accountIds 账户id集合
     * @return 结果
     */
    List<DyAccountEnums> getAccountList(List<String> accountIds);
}
