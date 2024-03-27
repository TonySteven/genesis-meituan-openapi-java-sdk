package com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor;

import com.genesis.org.cn.genesismeituanopenapijavasdk.config.TcConfig;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcPaywayDetailDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcPaywayDetailShopDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcPaywayDetailEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcPaywayDetailShopEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.result.ApiResult;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.LoginToServerAction;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.QueryShopInfoAction;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcPaywayDetailDataResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcPaywayDetailResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * tc get paywayDetail query and save cmd exe
 * 天财SaaS-调用查询品项做法档案信息实时并落库api具体逻辑.
 *
 * @author LP
 * &#064;date  2023/12/10
 */
@Service
@Slf4j
public class TcPaywayDetailQueryAndSaveCmdExe {

    /**
     * 天才配置
     */
    @Resource
    private TcConfig tcConfig;

    @Resource
    private ITcPaywayDetailDao tcPaywayDetailDao;

    @Resource
    private ITcPaywayDetailShopDao tcPaywayDetailShopDao;

    /**
     * execute
     * 执行
     *
     * @return {@link ApiResult}
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public ApiResult<Object> execute() {
        // 打印日志 - 开始.
        log.info("TcPaywayDetailQueryAndSaveCmdExe.execute() - start");
        // 1. 根据天财AppId和accessId进行鉴权.
        String accessToken = LoginToServerAction.getAccessToken(tcConfig);
        // 打印日志 - 鉴权成功.
        log.info("TcPaywayDetailQueryAndSaveCmdExe.execute() 鉴权成功, accessToken:{}", accessToken);

        return syncData(accessToken);
    }

    public ApiResult<Object> syncData(String accessToken) {
        // 2. 调用天财接口获取所有品项做法档案信息实时信息.
        // 2.0 先同步集团信息.
        List<TcPaywayDetailResponse> tcResponses = queryResponseAll(accessToken, null);

        // 2.2 将查询出来的品项做法档案信息去重
        List<TcPaywayDetailResponse> responseList = tcResponses.stream().distinct().toList();

        if(ObjectUtils.isEmpty(responseList)){
            return ApiResult.success("未获取到品项做法档案信息");
        }
        Map<String, List<TcPaywayDetailShopEntity>> shopEntityGroup = new HashMap<>();

        for(TcPaywayDetailResponse response : responseList){
            shopEntityGroup.put(response.getPayway_id(), TcPaywayDetailShopEntity.toEntityListByResponse(tcConfig.getApi().getCenterId(),response));
        }

        // 2.3 将查询出来的品项做法档案信息信息转成实体类.
        List<TcPaywayDetailEntity> tcEntityList = TcPaywayDetailEntity.toEntityListByResponse(tcResponses,tcConfig.getApi().getCenterId());

        // 3 操作数据库
        // 3.0 查询数据库中已有的信息.
        Map<String, TcPaywayDetailEntity> dbMap = tcPaywayDetailDao.getMapByCenterId(tcConfig.getApi().getCenterId());

        // 保存结算方式信息
        savePaywayDetail(tcEntityList, dbMap);

        // 保存结算方式门店限制列表
        savePaywayDetailShop(responseList, shopEntityGroup);


        // 打印日志 - 结束.
        log.info("TcPaywayDetailQueryAndSaveCmdExe.execute() - end");
        log.info("TcPaywayDetailQueryAndSaveCmdExe.execute() 完成落库, 对应的落库数量表1:{}", tcEntityList.size());

        return ApiResult.success();
    }

    private void savePaywayDetail(List<TcPaywayDetailEntity> tcEntityList, Map<String, TcPaywayDetailEntity> dbMap) {
        // 3.1 根据入库list列表查询数据库是否存在该数据，如果存在直接更新，不存在就新增
        List<TcPaywayDetailEntity> saveList = new ArrayList<>();

        List<TcPaywayDetailEntity> updateList = new ArrayList<>();

        for (TcPaywayDetailEntity responseEntity : tcEntityList) {
            // 3.1.1 查询数据库中是否存在该信息.
            TcPaywayDetailEntity tcEntity = dbMap.get(responseEntity.getPaywayId());
            // 3.1.2 如果数据库中不存在该信息,则新增.
            if (ObjectUtils.isEmpty(tcEntity)) {
                saveList.add(responseEntity);
            }else if(!responseEntity.equals(tcEntity)){
                // 3.1.3 如果数据库中存在该信息,则更新.
                updateList.add(responseEntity);
            }
        }

        // 3.2 批量操作数据库.
        tcPaywayDetailDao.operateBatch(saveList, updateList);
    }

    private void savePaywayDetailShop(List<TcPaywayDetailResponse> responseList, Map<String, List<TcPaywayDetailShopEntity>> shopEntityGroup) {
        // 获取结算方式ID
        List<String> paywayIdList = responseList.stream().map(TcPaywayDetailResponse::getPayway_id).toList();
        // 查询数据库中的门店限制详情
        Map<String, List<TcPaywayDetailShopEntity>> dbShopEntityGroup = tcPaywayDetailShopDao.getGroupByCenterId(tcConfig.getApi().getCenterId(), paywayIdList);

        // 要删除的门店限制列表关联关系 key:paywayId value:shopId
        Map<String,List<String>> removeList = new HashMap<>();

        // 要保存的门店限制列表关联关系
        List<TcPaywayDetailShopEntity> saveList = new ArrayList<>();

        // 要修改的门店限制列表关联关系
        List<TcPaywayDetailShopEntity> updateList = new ArrayList<>();


        for(String paywayId : shopEntityGroup.keySet()){
            // 获取三方门店限制列表
            List<TcPaywayDetailShopEntity> shopEntityList = shopEntityGroup.get(paywayId);

            // 获取数据库中的门店限制详情
            List<TcPaywayDetailShopEntity> dbShopEntityList = dbShopEntityGroup.get(paywayId);

            // 如果数据库和三方门店限制列表都为空，则跳过
            if(ObjectUtils.isEmpty(shopEntityList) && ObjectUtils.isEmpty(dbShopEntityList)){
                continue;
            }

            if(ObjectUtils.isEmpty(dbShopEntityList) && ObjectUtils.isNotEmpty(shopEntityList)){
                // 如果数据库中不存在该信息,则新增.
                saveList.addAll(shopEntityList);
            }else if(ObjectUtils.isNotEmpty(dbShopEntityList) && ObjectUtils.isNotEmpty(shopEntityList)){
                // 数据库和三方都存在则对比数据

                // 将数据库列表根据门店id转map
                Map<String, TcPaywayDetailShopEntity> dbShopEntityMap = dbShopEntityList.stream().collect(Collectors.toMap(TcPaywayDetailShopEntity::getShopId, Function.identity()));

                // 将三方数据根据门店id转map
                Map<String, TcPaywayDetailShopEntity> shopEntityMap = shopEntityList.stream().collect(Collectors.toMap(TcPaywayDetailShopEntity::getShopId, Function.identity()));

                // 汇总两个数据的门店id并去重
                List<String> shopIdList = Stream.concat(dbShopEntityMap.keySet().stream(), shopEntityMap.keySet().stream()).distinct().toList();

                // 循环门店ID对比数据
                for(String shopId : shopIdList){
                    // 根据门店ID获取三方和数据库数据
                    TcPaywayDetailShopEntity shopEntity = shopEntityMap.get(shopId);
                    TcPaywayDetailShopEntity dbShopEntity = dbShopEntityMap.get(shopId);
                    if(ObjectUtils.isEmpty(shopEntity) && ObjectUtils.isEmpty(dbShopEntity)){
                        // 如果数据库和三方都不存在该信息,则跳过.
                        continue;
                    }

                    if(ObjectUtils.isNotEmpty(shopEntity) && ObjectUtils.isEmpty(dbShopEntity)){
                        // 如果数据库中不存在该信息,则新增.
                        saveList.add(shopEntity);
                    }else if(ObjectUtils.isNotEmpty(shopEntity) && ObjectUtils.isNotEmpty(dbShopEntity)){
                        // 如果数据库中存并且三方也存在在该信息,则更新.
                        if(!shopEntity.equals(dbShopEntity)){
                            updateList.add(shopEntity);
                        }
                    }else if(ObjectUtils.isEmpty(shopEntity) && ObjectUtils.isNotEmpty(dbShopEntity)){
                        // 如果三方中不存在该信息,则删除.
                        removeList.computeIfAbsent(paywayId, k -> new ArrayList<>()).add(shopId);
                    }
                }

            }else  if(ObjectUtils.isEmpty(dbShopEntityList) && ObjectUtils.isNotEmpty(shopEntityList)){
                // 如果三方中不存在该信息,则删除.
                removeList.put(paywayId, null);
            }
        }

        // 持久化数据库
        tcPaywayDetailShopDao.operateBatch(tcConfig.getApi().getCenterId(),saveList, updateList, removeList);

    }

    private List<TcPaywayDetailResponse> queryResponseAll(String accessToken, String shopId){
        int pageNo = 1;
        int pageSize = 50;

        List<TcPaywayDetailResponse> resultList = new ArrayList<>();

        do {
            TcPaywayDetailDataResponse response = QueryShopInfoAction.queryPaywayDetailList(tcConfig, accessToken, pageNo, pageSize, shopId);

            if(ObjectUtils.isEmpty(response) || ObjectUtils.isEmpty(response.getData())){
                break;
            }

            resultList.addAll(response.getData().getPaywayDetail());

            if(pageNo >= response.getData().getPageInfo().getPageTotal()){
                break;
            }

            pageNo++;
        }while (true);

        return resultList;
    }
}
