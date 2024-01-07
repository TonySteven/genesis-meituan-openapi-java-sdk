package com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor;

import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcShopBillingDetailDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcShopBillingDetailItemDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcShopBillingSettleDetailDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcShopDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcShopBillingDetailEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcShopBillingDetailItemEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcShopBillingSettleDetailEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcShopEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.base.BaseVO;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.LoginToServerAction;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.QueryShopInfoAction;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.enums.ResponseStatusEnum;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.request.TcShopBillingDetailQueryCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.BillListItem;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.LoginResult;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.QueryBillDetailsDataResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.QueryBillDetailsResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.base.BasePageInfo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * tc shop billing detail query and save cmd exe
 * 天财SaaS-调用获取账单明细并落库api具体逻辑.
 *
 * @author steven
 * &#064;date  2023/12/30
 */
@Service
@Slf4j
public class TcShopBillingDetailQueryAndSaveCmdExe {

    /**
     * 服务器请求协议
     */
    @Value("${tiancai.protocol}")
    private String protocol;

    /**
     * 服务器地址
     */
    @Value("${tiancai.url}")
    private String applicationServer;
    /**
     * 服务器端口
     */
    @Value("${tiancai.port}")
    private Integer applicationPort;

    /**
     * 天财AppId
     */
    @Value("${tiancai.api.appId}")
    private String appId;

    /**
     * 天财accessId
     */
    @Value("${tiancai.api.accessId}")
    private String accessId;

    /**
     * 餐饮集团ID
     */
    @Value("${tiancai.api.centerId}")
    private String centerId;

    @Resource
    private ITcShopDao iTcShopDao;

    @Resource
    private ITcShopBillingDetailDao iTcShopBillingDetailDao;

    @Resource
    private ITcShopBillingDetailItemDao iTcShopBillingDetailItemDao;

    @Resource
    private ITcShopBillingSettleDetailDao iTcShopBillingSettleDetailDao;

    @Resource
    private TcShopBillingDetailInRealTimeQueryAndSaveCmdExe tcShopBillingDetailInRealTimeQueryAndSaveCmdExe;

    /**
     * execute
     * 执行
     *
     * @return {@link BaseVO}
     */
    @SneakyThrows
    public BaseVO execute(TcShopBillingDetailQueryCmd cmd) {
        // 打印日志 - 开始.
        log.info("TcShopBillingDetailQueryAndSaveCmdExe.execute() - start");
        // 1. 根据天财AppId和accessId进行鉴权.
        LoginResult loginResult = LoginToServerAction.login(protocol, applicationServer, applicationPort
            , appId, accessId);
        String msg = loginResult.getMsg();
        // 如果msg不为success,则抛出异常.
        if (!ResponseStatusEnum.SUCCESS.getValue().equals(msg)) {
            throw new Exception("鉴权失败!");
        }
        // 如果msg为success,则获取accessToken.
        String accessToken = loginResult.getAccessToken();
        // 打印日志 - 鉴权成功.
        log.info("TcShopBillingDetailQueryAndSaveCmdExe.execute() 鉴权成功, accessToken:{}", accessToken);

        // 2. 调用天财接口获取所有账单明细实时信息.
        // 2.0 获取所有店铺ids
        List<TcShopEntity> tcShopEntityList = iTcShopDao.list();
        List<String> shopIds = tcShopEntityList.stream().map(TcShopEntity::getShopId).toList();
        // 获取cmd中的shopIds
        String cmdShopId = cmd.getShopId();
        // 如果cmd中的shopId不为空,则shopIds截取cmdShopId后面的shopIds.
        if (StringUtils.isNotBlank(cmdShopId) && shopIds.contains(cmdShopId)) {
            shopIds = shopIds.subList(shopIds.indexOf(cmdShopId), shopIds.size());

            // 如果入参中的shopId不为空,则需要将库中shopId为此入参的数据删除,再重新落库.
            // 因为如果不删除,则会出现重复数据.
            deleteByShopId(centerId, cmdShopId);
        }

        // 遍历shopIds,获取每个shopId的账单明细实时信息.
        // 2.1 获取入参
        Date beginDate = cmd.getBeginDate();
        Date endDate = cmd.getEndDate();
        for (String shopId : shopIds) {
            // 打印日志 - 获取到的门店id.
            log.info("TcShopBillingDetailQueryAndSaveCmdExe.execute() 获取到的门店id:{}", shopId);
            // 打印日志 - 执行百分比
            log.info("TcShopBillingDetailQueryAndSaveCmdExe.execute() 执行第{}家:"
                , (shopIds.indexOf(shopId) + 1));
            // 初始化分页参数.
            Integer pageNo = 1;
            Integer pageSize = 50;
            // 2.1 获取所有门店实时账单信息.
            QueryBillDetailsResponse queryBillDetailsResponse = QueryShopInfoAction
                .queryBillingDetails(protocol, applicationServer, applicationPort, accessId
                    , accessToken, pageNo, pageSize, shopId, beginDate, endDate);

            // 2.2 获取账单明细列表.
            QueryBillDetailsDataResponse data = queryBillDetailsResponse.getData();
            List<BillListItem> billList = data.getBillList();
            // 如果billList为空,则跳过.
            if (CollectionUtils.isEmpty(billList)) {
                continue;
            }
            // 落库.
            saveBillDetail(shopId, billList);

            BasePageInfo pageInfo = data.getPageInfo();
            // 获取总页数, 如果总页数大于1, 则遍历获取所有门店信息.
            int totalPage = pageInfo.getPageTotal();
            if (totalPage > 1) {
                for (int i = 2; i <= totalPage; i++) {
                    // 获取所有门店实时账单信息.
                    QueryBillDetailsResponse queryBillDetailsResponse2 = QueryShopInfoAction
                        .queryBillingDetails(protocol, applicationServer, applicationPort, accessId
                            , accessToken, i, pageSize, shopId, beginDate, endDate);
                    // 如果success为false,则抛出异常.
                    if (!ResponseStatusEnum.SUCCESS.getInfo().equals(queryBillDetailsResponse2.getMsg())) {
                        throw new Exception("获取所有门店实时账单信息失败!");
                    }
                    // 如果success为true,则获取门店实时账单.
                    List<BillListItem> billList1 = queryBillDetailsResponse2.getData().getBillList();
                    // 因为将billList1添加到billList中,数据量很大, 所有改为查一次落库一次.
                    saveBillDetail(shopId, billList1);
                }
            }
        }

        // 返回参数.
        return BaseVO.builder()
            .id("1")
            .state("success")
            .msg("成功")
            .build();
    }

    /**
     * save bill detail
     *
     * @param shopId   shop id
     * @param billList bill list
     */
    private void saveBillDetail(String shopId, List<BillListItem> billList) throws Exception {

        // 1. 根据billList获取所有账单明细实时信息.
        TcShopBillingDetailInRealTimeQueryAndSaveCmdExe.MergeEntity mergeEntity
            = tcShopBillingDetailInRealTimeQueryAndSaveCmdExe.createTcShopBillingDetailEntity(centerId
            , shopId, billList);
        List<TcShopBillingDetailEntity> tcShopBillingDetailEntityList = mergeEntity
            .getTcShopBillingDetailEntityList();
        List<TcShopBillingDetailItemEntity> tcShopBillingDetailItemEntityList = mergeEntity
            .getTcShopBillingDetailItemEntityList();
        List<TcShopBillingSettleDetailEntity> tcShopBillingSettleDetailEntityList = mergeEntity
            .getTcShopBillingSettleDetailEntityList();

        // 3. 落库.
        // 打印日志 哪家门店的账单明细正在落库. 对应的落库数量
        log.info("TcShopBillingDetailQueryAndSaveCmdExe.execute() 门店:{} 的账单明细正在落库. 对应的落库数量表1:{}" +
                ",对应的落库数量表2:{},对应的落库数量表3:{} ", shopId
            , tcShopBillingDetailEntityList.size()
            , tcShopBillingDetailItemEntityList.size(), tcShopBillingSettleDetailEntityList.size());
        tcShopBillingDetailInRealTimeQueryAndSaveCmdExe.saveBillDetail(tcShopBillingDetailEntityList
            , tcShopBillingDetailItemEntityList, tcShopBillingSettleDetailEntityList);
    }

    // 创建deleteByShopId方法.
    private void deleteByShopId(String centerId, String shopId) {
        // 打印日志 - 开始.
        log.info("TcShopBillingDetailQueryAndSaveCmdExe.deleteByShopId() - start");

        // 删除 TcShopBillingDetailEntity, TcShopBillingDetailItemEntity, TcShopBillingSettleDetailEntity 此shopId的数据.
        iTcShopBillingDetailDao.deleteTcShopBillingDetailEntityByShopId(centerId, shopId);
        iTcShopBillingDetailItemDao.deleteTcShopBillingDetailItemEntityByShopId(centerId, shopId);
        iTcShopBillingSettleDetailDao.deleteTcShopBillingSettleDetailEntityByShopId(centerId, shopId);

        // 打印日志 删除成功.
        log.info("TcShopBillingDetailQueryAndSaveCmdExe.deleteByShopId() - 门店: {} success", shopId);
    }

}
