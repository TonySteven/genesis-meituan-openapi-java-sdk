package com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor;

import com.genesis.org.cn.genesismeituanopenapijavasdk.config.TcConfig;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcItemDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcItemLabelDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcItemMethodDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcItemMultiBarcodeDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcItemPgkDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcItemSizeDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcShopDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcItemEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcItemLabelEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcItemMethodEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcItemMultiBarcodeEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcItemPgkEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcItemSizeEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcShopEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.result.ApiResult;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.LoginToServerAction;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.QueryShopInfoAction;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.request.TcItemQueryRequest;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcItemDataResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcItemResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * tc get item info query and save cmd exe
 * 天财SaaS-调用查询品项信息实时并落库api具体逻辑.
 *
 * @author LP
 * &#064;date  2023/12/10
 */
@Service
@Slf4j
public class TcItemQueryAndSaveCmdExe {

    /**
     * 天才配置
     */
    @Resource
    private TcConfig tcConfig;

    @Resource
    private ITcShopDao iTcShopDao;

    @Resource
    private ITcItemDao iTcItemDao;

    @Resource
    private ITcItemSizeDao tcItemSizeDao;

    @Resource
    private ITcItemPgkDao tcItemPgkDao;

    @Resource
    private ITcItemMethodDao tcItemMethodDao;

    @Resource
    private ITcItemLabelDao tcItemLabelDao;

    @Resource
    private ITcItemMultiBarcodeDao tcItemMultiBarcodeDao;

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
        log.info("TcItemQueryAndSaveCmdExe.execute() - start");
        // 1. 根据天财AppId和accessId进行鉴权.
        String accessToken = LoginToServerAction.getAccessToken(tcConfig);
        // 打印日志 - 鉴权成功.
        log.info("TcItemQueryAndSaveCmdExe.execute() 鉴权成功, accessToken:{}", accessToken);

        // 当前时间 - 2天作为最近同步时间
        String lastTime = LocalDateTime.now().minusDays(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // 2. 调用天财接口获取所有品项类别明细实时信息.
        // 2.0 先同步集团分类信息.
        List<TcItemResponse> tcResponses = queryItemCategoryAll(accessToken, null,lastTime);

        // 2.1 获取所有店铺ids
        List<TcShopEntity> tcShopEntityList = iTcShopDao.list();
        List<String> shopIds = tcShopEntityList.stream().map(TcShopEntity::getShopId).toList();
        // 遍历shopIds,获取每个shopId的账单明细实时信息.
        for(String shopId : shopIds){
            tcResponses.addAll(queryItemCategoryAll(accessToken, shopId,lastTime));
        }

        // 2.2 将查询出来的品项类别去重
        List<TcItemResponse> responseList = tcResponses.stream().distinct().toList();

        if(ObjectUtils.isEmpty(responseList)){
            return ApiResult.success("未获取到品项类别");
        }

        List<TcItemEntity> tcEntityList = TcItemEntity.toEntityListByResponse(tcConfig.getApi().getCenterId(), responseList);

        // 获取三方品项的itemIds
        List<String> itemIds = tcEntityList.stream().map(TcItemEntity::getItemId).toList();

        // 3 操作数据库
        // 3.0 查询数据库中已有的分类信息.
        Map<String, TcItemEntity> dbMap = iTcItemDao.getMapByCenterId(tcConfig.getApi().getCenterId(),itemIds);

        // 保存品项
        saveItem(tcEntityList, dbMap);

        // 保存品项规格
        saveItemSize(tcEntityList,itemIds);

        // 保存套餐
        saveItemPgk(tcEntityList,itemIds);

        // 保存做法
        saveItemMethod(tcEntityList,itemIds);

        // 保存标签
        saveItemLabel(tcEntityList,itemIds);

        // 保存品项多条码
        saveItemMultiBarcode(tcEntityList,itemIds);

        // 打印日志 - 结束.
        log.info("TcItemQueryAndSaveCmdExe.execute() - end");
        log.info("TcItemQueryAndSaveCmdExe.execute() 完成落库, 对应的落库数量表1:{}", tcEntityList.size());
        return ApiResult.success();

    }

    private void saveItem(List<TcItemEntity> tcEntityList, Map<String, TcItemEntity> dbMap) {
        // 3.1 根据入库list列表查询数据库是否存在该数据，如果存在直接更新，不存在就新增
        List<TcItemEntity> saveList = new ArrayList<>();

        List<TcItemEntity> updateList = new ArrayList<>();

        for (TcItemEntity responseEntity : tcEntityList) {
            // 3.1.1 查询数据库中是否存在该分类信息.
            TcItemEntity tcCategoryEntity = dbMap.get(responseEntity.getItemId());
            // 3.1.2 如果数据库中不存在该分类信息,则新增.
            if (ObjectUtils.isEmpty(tcCategoryEntity)) {
                saveList.add(responseEntity);
            }else{
                // 3.1.3 如果数据库中存在该分类信息,则更新.
                updateList.add(responseEntity);
            }
        }

        // 3.2 批量操作数据库.
        iTcItemDao.operateBatch(saveList, updateList);
    }

    /**
     * 保存规格
     * @param tcEntityList 三方品项列表
     * @param itemIds 品项id列表
     */
    public void saveItemSize(List<TcItemEntity> tcEntityList, List<String> itemIds){

        Map<String, List<TcItemSizeEntity>> dbEntityGroup = tcItemSizeDao.getGroupByCenterId(tcConfig.getApi().getCenterId(),itemIds);

        // 要删除的品项规格列表 key:itemId value:sizeId
        Map<String,List<String>> removeList = new HashMap<>();

        // 要保存的品项规格列表
        List<TcItemSizeEntity> saveList = new ArrayList<>();

        // 要修改的品项规格列表
        List<TcItemSizeEntity> updateList = new ArrayList<>();


        for(TcItemEntity itemEntity : tcEntityList){
            // 获取三方品项规格列表
            List<TcItemSizeEntity> entityList = itemEntity.getItemSizeList();

            // 获取数据库中的品项规格列表
            List<TcItemSizeEntity> dbEntityList = dbEntityGroup.get(itemEntity.getItemId());

            // 如果数据库和三方品项规格列表都为空，则跳过
            if(ObjectUtils.isEmpty(entityList) && ObjectUtils.isEmpty(dbEntityList)){
                continue;
            }

            if(ObjectUtils.isEmpty(dbEntityList) && ObjectUtils.isNotEmpty(entityList)){
                // 如果数据库中不存在该信息,则新增.
                saveList.addAll(entityList);
            }else if(ObjectUtils.isNotEmpty(dbEntityList) && ObjectUtils.isNotEmpty(entityList)){
                // 数据库和三方都存在则对比数据

                // 将数据库列表根据品项id转map
                Map<String, TcItemSizeEntity> dbEntityMap = dbEntityList.stream().collect(Collectors.toMap(TcItemSizeEntity::getSizeId, Function.identity()));

                // 将三方数据根据品项id转map
                Map<String, TcItemSizeEntity> entityMap = entityList.stream().collect(Collectors.toMap(TcItemSizeEntity::getSizeId, Function.identity()));

                // 汇总两个数据的品项id并去重
                List<String> sizeIdList = Stream.concat(dbEntityMap.keySet().stream(), entityMap.keySet().stream()).distinct().toList();

                // 循环品项id对比数据
                for(String id : sizeIdList){
                    // 根据品项id获取三方和数据库数据
                    TcItemSizeEntity entity = entityMap.get(id);
                    TcItemSizeEntity dbEntity = dbEntityMap.get(id);
                    if(ObjectUtils.isEmpty(entity) && ObjectUtils.isEmpty(dbEntity)){
                        // 如果数据库和三方都不存在该信息,则跳过.
                        continue;
                    }

                    if(ObjectUtils.isNotEmpty(entity) && ObjectUtils.isEmpty(dbEntity)){
                        // 如果数据库中不存在该信息,则新增.
                        saveList.add(dbEntity);
                    }else if(ObjectUtils.isNotEmpty(entity) && ObjectUtils.isNotEmpty(dbEntity)){
                        // 如果数据库中存并且三方也存在在该信息,则更新.
                        if(!entity.equals(dbEntity)){
                            updateList.add(entity);
                        }
                    }else if(ObjectUtils.isEmpty(entity) && ObjectUtils.isNotEmpty(dbEntity)){
                        // 如果三方中不存在该信息,则删除.
                        removeList.computeIfAbsent(itemEntity.getItemId(), k -> new ArrayList<>()).add(id);
                    }
                }

            }else  if(ObjectUtils.isEmpty(dbEntityList) && ObjectUtils.isNotEmpty(entityList)){
                // 如果三方中不存在该信息,则删除.
                removeList.put(itemEntity.getItemId(), null);
            }
        }

        // 持久化数据库
        tcItemSizeDao.operateBatch(tcConfig.getApi().getCenterId(),saveList, updateList, removeList);
    }

    /**
     * 保存套餐
     * @param tcEntityList 三方品项列表
     * @param itemIds 品项id列表
     */
    public void saveItemPgk(List<TcItemEntity> tcEntityList, List<String> itemIds){

        Map<String, List<TcItemPgkEntity>> dbEntityGroup = tcItemPgkDao.getGroupByCenterId(tcConfig.getApi().getCenterId(),itemIds);

        // 要删除的品项套餐列表 key:itemId value:pkgId
        Map<String,List<String>> removeList = new HashMap<>();

        // 要保存的品项套餐列表
        List<TcItemPgkEntity> saveList = new ArrayList<>();

        // 要修改的品项套餐列表
        List<TcItemPgkEntity> updateList = new ArrayList<>();


        for(TcItemEntity itemEntity : tcEntityList){
            // 获取三方品项套餐列表
            List<TcItemPgkEntity> entityList = new ArrayList<>();

            // 获取三方品项常规套餐列表
            if(ObjectUtils.isNotEmpty(itemEntity.getPgkNormalItemList())){
                entityList.addAll(itemEntity.getPgkNormalItemList());
            }

            // 获取三方品项宴会套餐列表
            if(ObjectUtils.isNotEmpty(itemEntity.getPgkPartyItemList())){
                entityList.addAll(itemEntity.getPgkPartyItemList());
            }

            // 获取数据库中的品项套餐列表
            List<TcItemPgkEntity> dbEntityList = dbEntityGroup.get(itemEntity.getItemId());

            // 如果数据库和三方品项套餐列表都为空，则跳过
            if(ObjectUtils.isEmpty(entityList) && ObjectUtils.isEmpty(dbEntityList)){
                continue;
            }

            if(ObjectUtils.isEmpty(dbEntityList) && ObjectUtils.isNotEmpty(entityList)){
                // 如果数据库中不存在该信息,则新增.
                saveList.addAll(entityList);
            }else if(ObjectUtils.isNotEmpty(dbEntityList) && ObjectUtils.isNotEmpty(entityList)){
                // 数据库和三方都存在则对比数据

                // 将数据库列表根据品项套餐id转map
                Map<String, TcItemPgkEntity> dbEntityMap = dbEntityList.stream().collect(Collectors.toMap(TcItemPgkEntity::getPkgId, Function.identity()));

                // 将三方数据根据品项套餐id转map
                Map<String, TcItemPgkEntity> entityMap = entityList.stream().collect(Collectors.toMap(TcItemPgkEntity::getPkgId, Function.identity()));

                // 汇总两个数据的品项套餐id并去重
                List<String> pkgIdList = Stream.concat(dbEntityMap.keySet().stream(), entityMap.keySet().stream()).distinct().toList();

                // 循环品项套餐id对比数据
                for(String id : pkgIdList){
                    // 根据品项套餐id获取三方和数据库数据
                    TcItemPgkEntity entity = entityMap.get(id);
                    TcItemPgkEntity dbEntity = dbEntityMap.get(id);
                    if(ObjectUtils.isEmpty(entity) && ObjectUtils.isEmpty(dbEntity)){
                        // 如果数据库和三方都不存在该信息,则跳过.
                        continue;
                    }

                    if(ObjectUtils.isNotEmpty(entity) && ObjectUtils.isEmpty(dbEntity)){
                        // 如果数据库中不存在该信息,则新增.
                        saveList.add(dbEntity);
                    }else if(ObjectUtils.isNotEmpty(entity) && ObjectUtils.isNotEmpty(dbEntity)){
                        // 如果数据库中存并且三方也存在在该信息,则更新.
                        if(!entity.equals(dbEntity)){
                            updateList.add(entity);
                        }
                    }else if(ObjectUtils.isEmpty(entity) && ObjectUtils.isNotEmpty(dbEntity)){
                        // 如果三方中不存在该信息,则删除.
                        removeList.computeIfAbsent(itemEntity.getItemId(), k -> new ArrayList<>()).add(id);
                    }
                }

            }else  if(ObjectUtils.isEmpty(dbEntityList) && ObjectUtils.isNotEmpty(entityList)){
                // 如果三方中不存在该信息,则删除.
                removeList.put(itemEntity.getItemId(), null);
            }
        }

        // 持久化数据库
        tcItemPgkDao.operateBatch(tcConfig.getApi().getCenterId(),saveList, updateList, removeList);
    }

    /**
     * 保存标签
     * @param tcEntityList 三方品项列表
     * @param itemIds 品项id列表
     */
    public void saveItemLabel(List<TcItemEntity> tcEntityList, List<String> itemIds){

        Map<String, List<TcItemLabelEntity>> dbEntityGroup = tcItemLabelDao.getGroupByCenterId(tcConfig.getApi().getCenterId(),itemIds);

        // 要删除的品项标签列表 key:itemId value:pkgId
        Map<String,List<String>> removeList = new HashMap<>();

        // 要保存的品项标签列表
        List<TcItemLabelEntity> saveList = new ArrayList<>();

        // 要修改的品项标签列表
        List<TcItemLabelEntity> updateList = new ArrayList<>();


        for(TcItemEntity itemEntity : tcEntityList){
            // 获取三方品项标签列表
            List<TcItemLabelEntity> entityList = itemEntity.getItemLabelList();

            // 获取数据库中的品项标签列表
            List<TcItemLabelEntity> dbEntityList = dbEntityGroup.get(itemEntity.getItemId());

            // 如果数据库和三方品项标签列表都为空，则跳过
            if(ObjectUtils.isEmpty(entityList) && ObjectUtils.isEmpty(dbEntityList)){
                continue;
            }

            if(ObjectUtils.isEmpty(dbEntityList) && ObjectUtils.isNotEmpty(entityList)){
                // 如果数据库中不存在该信息,则新增.
                saveList.addAll(entityList);
            }else if(ObjectUtils.isNotEmpty(dbEntityList) && ObjectUtils.isNotEmpty(entityList)){
                // 数据库和三方都存在则对比数据

                // 将数据库列表根据品项标签id转map
                Map<String, TcItemLabelEntity> dbEntityMap = dbEntityList.stream().collect(Collectors.toMap(TcItemLabelEntity::getLabelId, Function.identity()));

                // 将三方数据根据品项标签id转map
                Map<String, TcItemLabelEntity> entityMap = entityList.stream().collect(Collectors.toMap(TcItemLabelEntity::getLabelId, Function.identity()));

                // 汇总两个数据的品项标签id并去重
                List<String> labelIdList = Stream.concat(dbEntityMap.keySet().stream(), entityMap.keySet().stream()).distinct().toList();

                // 循环品项标签id对比数据
                for(String id : labelIdList){
                    // 根据品项标签id获取三方和数据库数据
                    TcItemLabelEntity entity = entityMap.get(id);
                    TcItemLabelEntity dbEntity = dbEntityMap.get(id);
                    if(ObjectUtils.isEmpty(entity) && ObjectUtils.isEmpty(dbEntity)){
                        // 如果数据库和三方都不存在该信息,则跳过.
                        continue;
                    }

                    if(ObjectUtils.isNotEmpty(entity) && ObjectUtils.isEmpty(dbEntity)){
                        // 如果数据库中不存在该信息,则新增.
                        saveList.add(dbEntity);
                    }else if(ObjectUtils.isNotEmpty(entity) && ObjectUtils.isNotEmpty(dbEntity)){
                        // 如果数据库中存并且三方也存在在该信息,则更新.
                        if(!entity.equals(dbEntity)){
                            updateList.add(entity);
                        }
                    }else if(ObjectUtils.isEmpty(entity) && ObjectUtils.isNotEmpty(dbEntity)){
                        // 如果三方中不存在该信息,则删除.
                        removeList.computeIfAbsent(itemEntity.getItemId(), k -> new ArrayList<>()).add(id);
                    }
                }

            }else  if(ObjectUtils.isEmpty(dbEntityList) && ObjectUtils.isNotEmpty(entityList)){
                // 如果三方中不存在该信息,则删除.
                removeList.put(itemEntity.getItemId(), null);
            }
        }

        // 持久化数据库
        tcItemLabelDao.operateBatch(tcConfig.getApi().getCenterId(),saveList, updateList, removeList);
    }

    /**
     * 保存做法
     * @param tcEntityList 三方品项列表
     * @param itemIds 品项id列表
     */
    public void saveItemMethod(List<TcItemEntity> tcEntityList, List<String> itemIds){

        Map<String, List<TcItemMethodEntity>> dbEntityGroup = tcItemMethodDao.getGroupByCenterId(tcConfig.getApi().getCenterId(),itemIds);

        // 要删除的品项做法列表 key:itemId value:pkgId
        Map<String,List<String>> removeList = new HashMap<>();

        // 要保存的品项做法列表
        List<TcItemMethodEntity> saveList = new ArrayList<>();

        // 要修改的品项做法列表
        List<TcItemMethodEntity> updateList = new ArrayList<>();


        for(TcItemEntity itemEntity : tcEntityList){
            // 获取三方品项做法列表
            List<TcItemMethodEntity> entityList = new ArrayList<>();

            // 获取三方品项通用做法列表
            if(ObjectUtils.isNotEmpty(itemEntity.getPublicMethodList())){
                entityList.addAll(itemEntity.getPublicMethodList());
            }

            // 获取三方品项专用做法列表
            if(ObjectUtils.isNotEmpty(itemEntity.getPrivateMethodList())){
                entityList.addAll(itemEntity.getPrivateMethodList());
            }

            // 获取数据库中的品项做法列表
            List<TcItemMethodEntity> dbEntityList = dbEntityGroup.get(itemEntity.getItemId());

            // 如果数据库和三方品项做法列表都为空，则跳过
            if(ObjectUtils.isEmpty(entityList) && ObjectUtils.isEmpty(dbEntityList)){
                continue;
            }

            if(ObjectUtils.isEmpty(dbEntityList) && ObjectUtils.isNotEmpty(entityList)){
                // 如果数据库中不存在该信息,则新增.
                saveList.addAll(entityList);
            }else if(ObjectUtils.isNotEmpty(dbEntityList) && ObjectUtils.isNotEmpty(entityList)){
                // 数据库和三方都存在则对比数据

                // 将数据库列表根据做法id转map
                Map<String, TcItemMethodEntity> dbEntityMap = dbEntityList.stream().collect(Collectors.toMap(TcItemMethodEntity::getMethodId, Function.identity()));

                // 将三方数据根据做法id转map
                Map<String, TcItemMethodEntity> entityMap = entityList.stream().collect(Collectors.toMap(TcItemMethodEntity::getMethodId, Function.identity()));

                // 汇总两个数据的做法id并去重
                List<String> methodIdList = Stream.concat(dbEntityMap.keySet().stream(), entityMap.keySet().stream()).distinct().toList();

                // 循环品项做法id对比数据
                for(String id : methodIdList){
                    // 根据品项做法id获取三方和数据库数据
                    TcItemMethodEntity entity = entityMap.get(id);
                    TcItemMethodEntity dbEntity = dbEntityMap.get(id);
                    if(ObjectUtils.isEmpty(entity) && ObjectUtils.isEmpty(dbEntity)){
                        // 如果数据库和三方都不存在该信息,则跳过.
                        continue;
                    }

                    if(ObjectUtils.isNotEmpty(entity) && ObjectUtils.isEmpty(dbEntity)){
                        // 如果数据库中不存在该信息,则新增.
                        saveList.add(dbEntity);
                    }else if(ObjectUtils.isNotEmpty(entity) && ObjectUtils.isNotEmpty(dbEntity)){
                        // 如果数据库中存并且三方也存在在该信息,则更新.
                        if(!entity.equals(dbEntity)){
                            updateList.add(entity);
                        }
                    }else if(ObjectUtils.isEmpty(entity) && ObjectUtils.isNotEmpty(dbEntity)){
                        // 如果三方中不存在该信息,则删除.
                        removeList.computeIfAbsent(itemEntity.getItemId(), k -> new ArrayList<>()).add(id);
                    }
                }

            }else  if(ObjectUtils.isEmpty(dbEntityList) && ObjectUtils.isNotEmpty(entityList)){
                // 如果三方中不存在该信息,则删除.
                removeList.put(itemEntity.getItemId(), null);
            }
        }

        // 持久化数据库
        tcItemMethodDao.operateBatch(tcConfig.getApi().getCenterId(),saveList, updateList, removeList);
    }

    /**
     * 保存一品多条码
     * @param tcEntityList 三方品项列表
     * @param itemIds 品项id列表
     */
    public void saveItemMultiBarcode(List<TcItemEntity> tcEntityList, List<String> itemIds){

        Map<String, List<TcItemMultiBarcodeEntity>> dbEntityGroup = tcItemMultiBarcodeDao.getGroupByCenterId(tcConfig.getApi().getCenterId(),itemIds);

        // 要删除的品项条码列表 key:itemId value:pkgId
        Map<String,List<String>> removeList = new HashMap<>();

        // 要保存的品项条码列表
        List<TcItemMultiBarcodeEntity> saveList = new ArrayList<>();

        // 要修改的品项条码列表
        List<TcItemMultiBarcodeEntity> updateList = new ArrayList<>();


        for(TcItemEntity itemEntity : tcEntityList){
            // 获取三方品项条码列表
            List<TcItemMultiBarcodeEntity> entityList = itemEntity.getMultiBarcodeList();

            // 获取数据库中的品项条码列表
            List<TcItemMultiBarcodeEntity> dbEntityList = dbEntityGroup.get(itemEntity.getItemId());

            // 如果数据库和三方品项条码列表都为空，则跳过
            if(ObjectUtils.isEmpty(entityList) && ObjectUtils.isEmpty(dbEntityList)){
                continue;
            }

            if(ObjectUtils.isEmpty(dbEntityList) && ObjectUtils.isNotEmpty(entityList)){
                // 如果数据库中不存在该信息,则新增.
                saveList.addAll(entityList);
            }else if(ObjectUtils.isNotEmpty(dbEntityList) && ObjectUtils.isNotEmpty(entityList)){
                // 数据库和三方都存在则对比数据

                // 将数据库列表根据品项条码转map
                Map<String, TcItemMultiBarcodeEntity> dbEntityMap = dbEntityList.stream().collect(Collectors.toMap(TcItemMultiBarcodeEntity::getBarcode, Function.identity()));

                // 将三方数据根据品项条码转map
                Map<String, TcItemMultiBarcodeEntity> entityMap = entityList.stream().collect(Collectors.toMap(TcItemMultiBarcodeEntity::getBarcode, Function.identity()));

                // 汇总两个数据的品项条码并去重
                List<String> barcodeList = Stream.concat(dbEntityMap.keySet().stream(), entityMap.keySet().stream()).distinct().toList();

                // 循环品项条码id对比数据
                for(String id : barcodeList){
                    // 根据品项条码id获取三方和数据库数据
                    TcItemMultiBarcodeEntity entity = entityMap.get(id);
                    TcItemMultiBarcodeEntity dbEntity = dbEntityMap.get(id);
                    if(ObjectUtils.isEmpty(entity) && ObjectUtils.isEmpty(dbEntity)){
                        // 如果数据库和三方都不存在该信息,则跳过.
                        continue;
                    }

                    if(ObjectUtils.isNotEmpty(entity) && ObjectUtils.isEmpty(dbEntity)){
                        // 如果数据库中不存在该信息,则新增.
                        saveList.add(dbEntity);
                    }else if(ObjectUtils.isNotEmpty(entity) && ObjectUtils.isNotEmpty(dbEntity)){
                        // 如果数据库中存并且三方也存在在该信息,则更新.
                        if(!entity.equals(dbEntity)){
                            updateList.add(entity);
                        }
                    }else if(ObjectUtils.isEmpty(entity) && ObjectUtils.isNotEmpty(dbEntity)){
                        // 如果三方中不存在该信息,则删除.
                        removeList.computeIfAbsent(itemEntity.getItemId(), k -> new ArrayList<>()).add(id);
                    }
                }

            }else  if(ObjectUtils.isEmpty(dbEntityList) && ObjectUtils.isNotEmpty(entityList)){
                // 如果三方中不存在该信息,则删除.
                removeList.put(itemEntity.getItemId(), null);
            }
        }

        // 持久化数据库
        tcItemMultiBarcodeDao.operateBatch(tcConfig.getApi().getCenterId(),saveList, updateList, removeList);
    }

    private List<TcItemResponse> queryItemCategoryAll(String accessToken, String shopId,String lastTime){

        TcItemQueryRequest tcItemQueryRequest = new TcItemQueryRequest();
        tcItemQueryRequest.setPageNo(1);
        tcItemQueryRequest.setPageSize(300);
        tcItemQueryRequest.setShopId(shopId);
        tcItemQueryRequest.setLastTime(lastTime);
        tcItemQueryRequest.setCenterId(tcConfig.getApi().getCenterId());


        List<TcItemResponse> resultList = new ArrayList<>();

        do {
            TcItemDataResponse tcDataResponse = QueryShopInfoAction.queryItemList(tcConfig, accessToken, tcItemQueryRequest);

            if(ObjectUtils.isEmpty(tcDataResponse) || ObjectUtils.isEmpty(tcDataResponse.getData())){
                break;
            }

            resultList.addAll(tcDataResponse.getData().getItem());

            if(tcItemQueryRequest.getPageNo() >= tcDataResponse.getData().getPageInfo().getTotalSize()){
                break;
            }

            tcItemQueryRequest.setPageNo(tcItemQueryRequest.getPageNo() + 1);
        }while (true);

        return resultList;
    }
}
