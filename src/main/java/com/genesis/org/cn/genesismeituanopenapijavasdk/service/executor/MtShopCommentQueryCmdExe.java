package com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor;

import com.bty.scm.boot.jointblock.core.Executor;
import com.genesis.org.cn.genesismeituanopenapijavasdk.api.base.BaseVO;
import com.genesis.org.cn.genesismeituanopenapijavasdk.api.request.MtShopCommentQryCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.IMtShopCommentDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 采购发票业务-查询列表执行具体逻辑
 *
 * @author steven
 * &#064;date  2023/01/10
 */
@Service
public class MtShopCommentQueryCmdExe implements Executor<MtShopCommentQryCmd, BaseVO> {

    @Resource
    private IMtShopCommentDao iMtShopCommentDao;

    /**
     * execute
     * 执行
     *
     * @param cmd cmd
     * @return {@link BaseVO}
     */
    @Override
    public BaseVO execute(MtShopCommentQryCmd cmd) {
        // 1. 参数校验.
        cmd.validate();


        // 返回参数.
        return BaseVO.builder()
            .id(cmd.getShopId())
            .state("success")
            .msg("成功")
            .build();
    }

}
