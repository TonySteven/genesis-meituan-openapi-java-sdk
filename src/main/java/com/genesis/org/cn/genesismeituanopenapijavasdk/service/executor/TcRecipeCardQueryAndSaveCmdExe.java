package com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor;

import cn.hutool.core.collection.ListUtil;
import com.alibaba.fastjson.JSON;
import com.genesis.org.cn.genesismeituanopenapijavasdk.config.TcConfig;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcItemDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcRecipeCardDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcRecipeCardDetailsDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcRecipeCardDetailsShopDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcItemEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcRecipeCardDetailsEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcRecipeCardDetailsShopEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcRecipeCardEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.result.ApiResult;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.LoginToServerAction;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.QueryShopInfoAction;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.request.TcRecipeCardQueryCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.request.TcRecipeCardQueryRequest;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcRecipeCardDataResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcRecipeCardResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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

@Slf4j
@Service
public class TcRecipeCardQueryAndSaveCmdExe {

    /**
     * 天才配置
     */
    @Resource
    private TcConfig tcConfig;

    @Resource
    private ITcItemDao tcItemDao;

    @Resource
    private ITcRecipeCardDao iTcRecipeCardDao;

    @Resource
    private ITcRecipeCardDetailsDao iTcRecipeCardDetailsDao;

    @Resource
    private ITcRecipeCardDetailsShopDao iTcRecipeCardDetailsShopDao;

    /**
     * execute
     * 执行
     *
     * @return {@link ApiResult}
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public ApiResult<Object> execute(TcRecipeCardQueryCmd cmd) {
        // 打印日志 - 开始.
        log.info("TcPayTypeQueryAndSaveCmdExe.execute() - start");
        // 1. 根据天财AppId和accessId进行鉴权.
        String accessToken = LoginToServerAction.getAccessToken(tcConfig);
        // 打印日志 - 鉴权成功.
        log.info("TcPayTypeQueryAndSaveCmdExe.execute() 鉴权成功, accessToken:{}", accessToken);

        // 2.1 获取所有店铺ids
        List<TcItemEntity> tcItemEntities =  tcItemDao.getListByCenterId(tcConfig.getApi().getCenterId(),cmd.getDishJkidList());
        List<String> itemIds = tcItemEntities.stream().map(TcItemEntity::getItemId).toList();


        List<TcRecipeCardResponse> responses = queryResponseAll(itemIds);

        // 出参转实体
        List<TcRecipeCardEntity> cartList = TcRecipeCardEntity.toEntityListByResponse(tcConfig.getApi().getCenterId(),responses);

        // 将三方数据根据品项id和品项规格id转map
        Map<String, TcRecipeCardEntity> cartMap = cartList.stream()
            .collect(Collectors.toMap(item -> item.getDishJkId() + item.getDishUnitJkId(), Function.identity()));

        // 查询数据库数据
        Map<String, TcRecipeCardEntity> dbCartMap = iTcRecipeCardDao.getMapByCenterId(tcConfig.getApi().getCenterId());

        // 合并数据库与三方的key并去重
        List<String> cardIdList = Stream.concat(cartMap.keySet().stream(), dbCartMap.keySet().stream()).distinct().toList();

        // 保存成本卡信息
        saveCard(cartMap, dbCartMap,cardIdList);

        // 保存成本卡详情
        saveCardDetails(cartList, itemIds);

        return ApiResult.success();
    }

    /**
     * 保存成本卡信息
     * @param cartMap 三方成本卡
     * @param dbCartMap 数据库成本卡
     */
    private void saveCard(Map<String, TcRecipeCardEntity> cartMap, Map<String, TcRecipeCardEntity> dbCartMap,List<String> cardIdList) {


        // 3.1 根据入库list列表查询数据库是否存在该数据，如果存在直接更新，不存在就新增
        List<TcRecipeCardEntity> saveList = new ArrayList<>();

        List<TcRecipeCardEntity> updateList = new ArrayList<>();


        List<String> removeList = new ArrayList<>();


        for (String cardId : cardIdList) {
            // 3.1.1 查询数据库中是否存在该信息.
            TcRecipeCardEntity tcEntity = dbCartMap.get(cardId);
            // 查询三方是否存在
            TcRecipeCardEntity responseEntity = cartMap.get(cardId);
            if (ObjectUtils.isEmpty(tcEntity) && ObjectUtils.isEmpty(responseEntity)) {
                continue;
            }
            if (ObjectUtils.isEmpty(tcEntity) && ObjectUtils.isNotEmpty(responseEntity)) {
                // 3.1.2 如果数据库中不存在该信息,则新增.
                saveList.add(responseEntity);
            }else if(ObjectUtils.isNotEmpty(tcEntity) && ObjectUtils.isNotEmpty(responseEntity) && !responseEntity.equals(tcEntity)){
                // 3.1.3 如果数据库中存在该信息,则更新.
                updateList.add(responseEntity);
            }else if(ObjectUtils.isNotEmpty(tcEntity) && ObjectUtils.isEmpty(responseEntity)){
                // 3.1.4 如果三方不存在该信息,则删除.
                removeList.add(tcEntity.getId());
            }
        }

        // 操作数据库保存
        iTcRecipeCardDao.operateBatch(tcConfig.getApi().getCenterId(),saveList,updateList,removeList);
    }

    public void saveCardDetails(List<TcRecipeCardEntity> cartList, List<String> itemIds){

        // 查询数据库明细数据
        Map<String, TcRecipeCardDetailsEntity> dbEntityMap = iTcRecipeCardDetailsDao.getMapByCenterId(tcConfig.getApi().getCenterId(),itemIds);

        // 获取三方明细数据
        Map<String,TcRecipeCardDetailsEntity> entityMap = new HashMap<>();

        for (TcRecipeCardEntity cart : cartList) {
            if(ObjectUtils.isNotEmpty(cart.getRecipecardDts())){
                for(TcRecipeCardDetailsEntity entity : cart.getRecipecardDts()){
                    entityMap.put(entity.getId(), entity);
                }
            }
        }

        // 合并数据库map和三方map的key并去重
        List<String> idList = Stream.concat(entityMap.keySet().stream(), dbEntityMap.keySet().stream()).distinct().toList();

        // 要保存的成本卡明细
        List<TcRecipeCardDetailsEntity> saveList = new ArrayList<>();
        // 要修改的成本卡明细
        List<TcRecipeCardDetailsEntity> updateList = new ArrayList<>();
        // 要删除的成本卡明细
        List<String> removeIdList = new ArrayList<>();
        for (String id : idList) {
            // 3.1.1 查询数据库中是否存在该信息.
            TcRecipeCardDetailsEntity tcEntity = dbEntityMap.get(id);
            // 查询三方是否存在
            TcRecipeCardDetailsEntity responseEntity = entityMap.get(id);
            if (ObjectUtils.isEmpty(tcEntity) && ObjectUtils.isEmpty(responseEntity)) {
                continue;
            }
            if (ObjectUtils.isEmpty(tcEntity) && ObjectUtils.isNotEmpty(responseEntity)) {
                // 3.1.2 如果数据库中不存在该信息,则新增.
                saveList.add(responseEntity);
            }else if(ObjectUtils.isNotEmpty(tcEntity) && ObjectUtils.isNotEmpty(responseEntity) && !responseEntity.equals(tcEntity)){
                // 3.1.3 如果数据库中存在该信息,则更新.
                updateList.add(responseEntity);
            }else if(ObjectUtils.isNotEmpty(tcEntity) && ObjectUtils.isEmpty(responseEntity)){
                // 3.1.4 如果三方不存在该信息,则删除.
                removeIdList.add(tcEntity.getId());
            }
        }

        // 操作数据库保存
        iTcRecipeCardDetailsDao.operateBatch(tcConfig.getApi().getCenterId(),saveList,updateList,removeIdList);

        // 保存成本卡适用门店
        saveCardDetailsShop(entityMap, idList);
    }

    /**
     * 保存成本卡适用门店
     * @param entityMap 三方成本卡数据
     * @param idList 成本卡id
     */
    public void saveCardDetailsShop(Map<String,TcRecipeCardDetailsEntity> entityMap,List<String> idList) {
        // 查询数据库中成本卡信息适用门店信息
        Map<String, List<TcRecipeCardDetailsShopEntity>> dbShopMap = iTcRecipeCardDetailsShopDao.getGroupByCenterId(tcConfig.getApi().getCenterId(),idList);

        List<TcRecipeCardDetailsShopEntity> saveList = new ArrayList<>();
        List<TcRecipeCardDetailsShopEntity> updateList = new ArrayList<>();
        Map<String,List<String>> removeList = new HashMap<>();

        for (String id : idList) {
            List<TcRecipeCardDetailsShopEntity> shopEntityList = ObjectUtils.isNotEmpty(entityMap.get(id)) ? entityMap.get(id).getShopList() : null;


            List<TcRecipeCardDetailsShopEntity> dbShopEntityList = dbShopMap.get(id);



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
                Map<String, TcRecipeCardDetailsShopEntity> dbShopEntityMap = dbShopEntityList.stream().collect(Collectors.toMap(TcRecipeCardDetailsShopEntity::getShopId, Function.identity()));

                // 将三方数据根据门店id转map
                Map<String, TcRecipeCardDetailsShopEntity> shopEntityMap = shopEntityList.stream().collect(Collectors.toMap(TcRecipeCardDetailsShopEntity::getShopId, Function.identity()));

                // 汇总两个数据的门店id并去重
                List<String> shopIdList = Stream.concat(dbShopEntityMap.keySet().stream(), shopEntityMap.keySet().stream()).distinct().toList();

                // 循环门店ID对比数据
                for(String shopId : shopIdList){
                    // 根据门店ID获取三方和数据库数据
                    TcRecipeCardDetailsShopEntity shopEntity = shopEntityMap.get(shopId);
                    TcRecipeCardDetailsShopEntity dbShopEntity = dbShopEntityMap.get(shopId);
                    if(ObjectUtils.isEmpty(shopEntity) && ObjectUtils.isEmpty(dbShopEntity)){
                        // 如果数据库和三方都不存在该信息,则跳过.
                        continue;
                    }

                    if(ObjectUtils.isNotEmpty(shopEntity) && ObjectUtils.isEmpty(dbShopEntity)){
                        // 如果数据库中不存在该信息,则新增.
                        saveList.add(dbShopEntity);
                    }else if(ObjectUtils.isNotEmpty(shopEntity) && ObjectUtils.isNotEmpty(dbShopEntity)){
                        // 如果数据库中存并且三方也存在在该信息,则更新.
                        if(!shopEntity.equals(dbShopEntity)){
                            updateList.add(shopEntity);
                        }
                    }else if(ObjectUtils.isEmpty(shopEntity) && ObjectUtils.isNotEmpty(dbShopEntity)){
                        // 如果三方中不存在该信息,则删除.
                        removeList.computeIfAbsent(id, k -> new ArrayList<>()).add(shopId);
                    }
                }

            }else  if(ObjectUtils.isEmpty(dbShopEntityList) && ObjectUtils.isNotEmpty(shopEntityList)){
                // 如果三方中不存在该信息,则删除.
                removeList.put(id, null);
            }


        }

        // 持久化数据库
        iTcRecipeCardDetailsShopDao.operateBatch(tcConfig.getApi().getCenterId(),saveList, updateList, removeList);

    }

    private List<TcRecipeCardResponse> queryResponseAll(List<String> itemIds) {
        List<TcRecipeCardResponse> list = new ArrayList<>();

        // 将shopid分为50一组，进行分组查询
        List<List<String>> itemIdsList = ListUtil.split(itemIds, 30);

        // itemIdsList,获取每个itemIdsList的成本卡
        for(List<String> itemIdList : itemIdsList){
            TcRecipeCardQueryRequest request =  new TcRecipeCardQueryRequest();
            request.setDishJkid(StringUtils.join(itemIdList, ","));
            try {
                TcRecipeCardDataResponse response = QueryShopInfoAction.queryRecipeCardList(tcConfig, request);

                list.addAll(response.getData());
            }catch (Exception e){
                log.error("查询成本卡异常:{}, 入参:{}",e.getMessage(), JSON.toJSONString(request),e);
            }
        }
        return list;
    }
}
