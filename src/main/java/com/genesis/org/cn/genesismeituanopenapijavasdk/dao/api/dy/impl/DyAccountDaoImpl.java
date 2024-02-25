package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.dy.impl;


import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.dy.IDyAccountDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.enums.DyAccountEnums;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 抖音本地生活商家账户信息 服务类
 * </p>
 *
 * @author LiPei
 * @since 2024-02-03
 */
@Service
public class DyAccountDaoImpl implements IDyAccountDao {

    /**
     * 获取抖音本地生活商家账户信息
     * @param accountIds 账户id集合
     * @return 结果
     */
    @Override
    public List<DyAccountEnums> getAccountList(List<String> accountIds){

        List<DyAccountEnums> accountList;
        if(ObjectUtils.isEmpty(accountIds)){
            // 如果传入的账户id为空，那么就查询所有账户id
            accountList = Arrays.stream(DyAccountEnums.values()).toList();
        }else{
            accountList = DyAccountEnums.getByAccountIds(accountIds);
        }

        return accountList;
    }
}
