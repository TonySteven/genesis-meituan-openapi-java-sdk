package com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.genesis.org.cn.genesismeituanopenapijavasdk.config.TcConfig;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcShopBillingTicketDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcShopDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcShopBillingTicketEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcShopEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request.TcShopBillingTicketQueryCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.result.ApiResult;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.DateUtils;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.LoginToServerAction;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.QueryShopInfoAction;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.request.TcShopBillingTicketQueryRequest;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcShopBillingTicketDataResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcShopBillingTicketResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * tc get o2o ticket query and save cmd exe
 * 天财SaaS-调用查询账单明细数据实时并落库api具体逻辑.
 *
 * @author LP
 * &#064;date  2024/05/13
 */
@Service
@Slf4j
public class TcShopBillingO2oTicketQueryAndSaveCmdExe {

    /**
     * 天才配置
     */
    @Resource
    private TcConfig tcConfig;

    @Resource
    private ITcShopDao iTcShopDao;

    @Resource
    private ITcShopBillingTicketDao tcShopBillingTicketDao;

    /**
     * execute
     * 执行
     *
     * @return {@link ApiResult}
     */
    @SneakyThrows
    public ApiResult<Object> execute(TcShopBillingTicketQueryCmd cmd) {
        // 打印日志 - 开始.
        log.info("TcO2oTicketQueryAndSaveCmdExe.execute() - start");
        // 1. 根据天财AppId和accessId进行鉴权.
        String accessToken = LoginToServerAction.getAccessToken(tcConfig);
        // 打印日志 - 鉴权成功.
        log.info("TcO2oTicketQueryAndSaveCmdExe.execute() 鉴权成功, accessToken:{}", accessToken);

        return syncData(cmd,accessToken);
    }

    public ApiResult<Object> syncData(TcShopBillingTicketQueryCmd cmd, String accessToken) {
        if(StringUtils.isBlank(cmd.getEnd())){
            // 如果结束时间不为空，则拿当前时间作为结束时间
            cmd.setEnd(LocalDateTimeUtil.format(DateUtils.withEndLocalTime(LocalDateTime.now().minusDays(1)), DatePattern.NORM_DATETIME_PATTERN));
        }
        if(StringUtils.isBlank(cmd.getBegin())){
            // 如果开始时间为空，则根据结束时间往前推2天
            cmd.setBegin(LocalDateTimeUtil.format(DateUtils.withStartLocalTime(DateUtils.getLocalDateTime(cmd.getEnd())).minusDays(1), DatePattern.NORM_DATETIME_PATTERN));
        }

        List<String> errorMsg = new ArrayList<>();

        // 2.1 获取所有店铺ids
        LambdaQueryWrapper<TcShopEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TcShopEntity::getCenterId, tcConfig.getApi().getCenterId());
        if(ObjectUtils.isNotEmpty(cmd.getShopIdList())){
            queryWrapper.in(TcShopEntity::getShopId, cmd.getShopIdList());
        }
        List<TcShopEntity> tcShopEntityList = iTcShopDao.list(queryWrapper);
        if(ObjectUtils.isEmpty(tcShopEntityList)){
            return ApiResult.error("未获取到店铺信息");
        }
        // 遍历门店信息,根据门店同步账单明细实时信息.
        for(TcShopEntity shop : tcShopEntityList){
            try {
                // 2. 调用天财接口获取所有单据明细信息实时信息.
                List<TcShopBillingTicketResponse> responseList = queryResponseAll(accessToken, shop.getShopId(),cmd);

                if(ObjectUtils.isEmpty(responseList)){
                    errorMsg.add(String.format("店铺%s未获取到单据明细信息",shop.getShopId()));
                    continue;
                }

                // 2.3 将查询出来的单据明细信息信息转成实体类.
                List<TcShopBillingTicketEntity> tcEntityList = TcShopBillingTicketEntity.toEntityListByResponse(tcConfig.getApi().getCenterId(),responseList);

                // 3 操作数据库
                // 3.0 查询数据库中已有的信息.

                // 获取营业流水ID
                List<String> bsIdList = tcEntityList.stream().map(TcShopBillingTicketEntity::getBsId).distinct().toList();
                // 获取券码集合
                List<String> ticketCodeList = tcEntityList.stream().map(TcShopBillingTicketEntity::getTicketCode).distinct().toList();
                Map<String, TcShopBillingTicketEntity> dbMap = tcShopBillingTicketDao.getMapByCenterId(tcConfig.getApi().getCenterId(),shop.getShopId(),bsIdList,ticketCodeList);

                // 3.1 根据入库list列表查询数据库是否存在该数据，如果存在直接更新，不存在就新增
                List<TcShopBillingTicketEntity> saveList = new ArrayList<>();

                List<TcShopBillingTicketEntity> updateList = new ArrayList<>();

                for (TcShopBillingTicketEntity responseEntity : tcEntityList) {
                    // 3.1.1 查询数据库中是否存在该信息.
                    if("[]".equals(responseEntity.getTicketCode())){
                        log.error("券码为空，请检查数据");
                    }
                    // 拼接唯一索引key
                    String key = responseEntity.getShopId() + responseEntity.getBsId() +
                        responseEntity.getTicketCode() +
                        LocalDateTimeUtil.format(responseEntity.getUseTime(), DatePattern.PURE_DATETIME_PATTERN);

                    TcShopBillingTicketEntity tcEntity = dbMap.get(key);
                    // 3.1.2 如果数据库中不存在该信息,则新增.
                    if (ObjectUtils.isEmpty(tcEntity)) {
                        saveList.add(responseEntity);
                    }else if(!responseEntity.equals(tcEntity)){
                        // 3.1.3 如果数据库中存在该信息,则更新.
                        updateList.add(responseEntity);
                    }
                }

                // 3.2 批量操作数据库.
                tcShopBillingTicketDao.operateBatch(saveList, updateList);


                // 打印门店开始日志
                log.info("TcO2oTicketQueryAndSaveCmdExe.execute() 完成落库, 对应的落库数量表1:{}, shopId:{}", tcEntityList.size(),shop.getShopId());
            }catch (Exception e){
                log.error("TcO2oTicketQueryAndSaveCmdExe.execute() - error, shopId:{}",shop.getShopId(),e);
                errorMsg.add(String.format("门店【%s】单据明细数据同步失败，失败原因：%s",shop.getShopId(),e.getMessage()));
            }
        }
        // 打印日志 - 结束.
        log.info("TcO2oTicketQueryAndSaveCmdExe.execute() - end");
        if(ObjectUtils.isNotEmpty(errorMsg)){
            return ApiResult.error(errorMsg);
        }

        return ApiResult.success();
    }

    private List<TcShopBillingTicketResponse> queryResponseAll(String accessToken, String shopId, TcShopBillingTicketQueryCmd cmd){

        TcShopBillingTicketQueryRequest request = new TcShopBillingTicketQueryRequest();
        request.setCenterId(tcConfig.getApi().getCenterId());
        request.setShopId(shopId);
        request.setBegin(cmd.getBegin());
        request.setEnd(cmd.getEnd());
        request.setPageNo(1);
        request.setPageSize(500);
        request.setSeller(cmd.getSeller());


        List<TcShopBillingTicketResponse> resultList = new ArrayList<>();

        do {
            TcShopBillingTicketDataResponse response = QueryShopInfoAction.queryO2oTicketList(tcConfig, accessToken, request);

            if(ObjectUtils.isEmpty(response) || ObjectUtils.isEmpty(response.getData())){
                break;
            }

            resultList.addAll(response.getData().getTicketDataList());

            if(request.getPageNo() >= response.getData().getPageInfo().getPageTotal()){
                break;
            }

            request.setPageNo(request.getPageNo() + 1);
        }while (true);

        return resultList;
    }
}
