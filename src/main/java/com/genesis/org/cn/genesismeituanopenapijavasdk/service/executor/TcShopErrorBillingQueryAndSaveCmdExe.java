package com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor;

import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcShopBillingErrorInfoDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcShopBillingErrorInfoEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.base.BaseVO;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.request.TcShopBillingDetailQueryCmd;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * tc shop billing detail query and save cmd exe
 * 天财SaaS-调用错误账单明细并落库api具体逻辑.
 *
 * @author steven
 * &#064;date  2024/04/07
 */
@Service
@Slf4j
public class TcShopErrorBillingQueryAndSaveCmdExe {

    @Resource
    private ITcShopBillingErrorInfoDao iTcShopBillingErrorInfoDao;

    @Resource
    private TcShopBillingDetailQueryAndSaveCmdExe tcShopBillingDetailQueryAndSaveCmdExe;


    /**
     * execute
     * 执行
     *
     * @return {@link BaseVO}
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public BaseVO execute() {
        // 打印日志 - 开始. 打印cmd.
        log.info("TcShopErrorBillingQueryAndSaveCmdExe.execute() - start");


        // 1 获取所有pos单错误日志
        List<TcShopBillingErrorInfoEntity> tcShopBillingErrorInfoEntityList = iTcShopBillingErrorInfoDao.list();

        // 2 遍历 tcShopBillingErrorInfoEntityList
        if (!CollectionUtils.isEmpty(tcShopBillingErrorInfoEntityList)) {
            // 遍历 tcShopBillingErrorInfoEntityList
            tcShopBillingErrorInfoEntityList.forEach(tcShopBillingErrorInfoEntity -> {
                // 获取shopId
                String shopId = tcShopBillingErrorInfoEntity.getShopId();
                // 获取beginDate
                Date beginDate = tcShopBillingErrorInfoEntity.getBeginDate();
                // 获取endDate
                Date endDate = tcShopBillingErrorInfoEntity.getEndDate();
                TcShopBillingDetailQueryCmd cmd = new TcShopBillingDetailQueryCmd();
                cmd.setShopIdList(Collections.singletonList(shopId));
                cmd.setBeginDate(String.valueOf(beginDate));
                cmd.setEndDate(String.valueOf(endDate));

                tcShopBillingDetailQueryAndSaveCmdExe.execute(cmd);

                // 如果执行完了,则把此记录删除.
                iTcShopBillingErrorInfoDao.removeById(tcShopBillingErrorInfoEntity.getId());
            });
        }

        // 返回参数.
        return BaseVO.builder()
            .id("1")
            .state("success")
            .msg("成功")
            .build();
    }

}
