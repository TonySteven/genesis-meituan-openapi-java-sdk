package com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor;

import com.genesis.org.cn.genesismeituanopenapijavasdk.config.TcConfig;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcItemMethodClassesDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcShopDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcItemMethodClassesEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.result.ApiResult;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.LoginToServerAction;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.QueryShopInfoAction;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcItemMethodClassesDataResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcItemMethodClassesResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * tc get item category query and save cmd exe
 * 天财SaaS-调用查询品项类别信息实时并落库api具体逻辑.
 *
 * @author LP
 * &#064;date  2023/12/10
 */
@Service
@Slf4j
public class TcItemMethodClassesQueryAndSaveCmdExe {

    /**
     * 天才配置
     */
    @Resource
    private TcConfig tcConfig;

    @Resource
    private ITcShopDao iTcShopDao;

    @Resource
    private ITcItemMethodClassesDao tcItemMethodClassesDao;

    /**
     * execute
     * 执行
     *
     * @return {@link ApiResult}
     */
    @SneakyThrows
    public ApiResult<Object> execute() {
        // 打印日志 - 开始.
        log.info("TcItemMethodClassesQueryAndSaveCmdExe.execute() - start");
        // 1. 根据天财AppId和accessId进行鉴权.
        String accessToken = LoginToServerAction.getAccessToken(tcConfig);
        // 打印日志 - 鉴权成功.
        log.info("TcItemMethodClassesQueryAndSaveCmdExe.execute() 鉴权成功, accessToken:{}", accessToken);

        return syncData(accessToken);
    }

    public ApiResult<Object> syncData(String accessToken) {
        // 2. 调用天财接口获取所有品项类别明细实时信息.
        // 2.0 先同步集团分类信息.
        List<TcItemMethodClassesResponse> responseList = queryResponseAll(accessToken, null);

//        // 2.1 获取所有店铺ids
//        List<TcShopEntity> tcShopEntityList = iTcShopDao.list();
//        List<String> shopIds = tcShopEntityList.stream().map(TcShopEntity::getShopId).toList();
//        // 遍历shopIds,获取每个shopId的账单明细实时信息.
//        for(String shopId : shopIds){
//            tcItemMethodClassesResponses.addAll(queryResponseAll(accessToken, shopId));
//        }
//
//        // 2.2 将查询出来的品项类别去重
//        List<TcItemMethodClassesResponse> responseList = tcResponses.stream().distinct().toList();

        if(ObjectUtils.isEmpty(responseList)){
            return ApiResult.success("未获取到品项类别");
        }

        // 2.3 将查询出来的品项类别明细信息转成实体类.
        List<TcItemMethodClassesEntity> tcCategoryEntityList = TcItemMethodClassesEntity.toEntityListByResponse(tcConfig.getApi().getCenterId(),responseList);

        // 3 操作数据库
        // 3.0 查询数据库中已有的分类信息.
        Map<String, TcItemMethodClassesEntity> dbMap = tcItemMethodClassesDao.getMapByCenterId(tcConfig.getApi().getCenterId());

        // 3.1 根据入库list列表查询数据库是否存在该数据，如果存在直接更新，不存在就新增
        List<TcItemMethodClassesEntity> saveList = new ArrayList<>();

        List<TcItemMethodClassesEntity> updateList = new ArrayList<>();

        for (TcItemMethodClassesEntity responseEntity : tcCategoryEntityList) {
            // 3.1.1 查询数据库中是否存在该分类信息.
            TcItemMethodClassesEntity tcEntity = dbMap.get(responseEntity.getId());
            // 3.1.2 如果数据库中不存在该分类信息,则新增.
            if (ObjectUtils.isEmpty(tcEntity)) {
                saveList.add(responseEntity);
            }else if(!responseEntity.equals(tcEntity)){
                // 3.1.3 如果数据库中存在该分类信息,则更新.
                updateList.add(responseEntity);
            }
        }

        // 3.2 批量操作数据库.
        tcItemMethodClassesDao.operateBatch(saveList, updateList);


        // 打印日志 - 结束.
        log.info("TcItemMethodClassesQueryAndSaveCmdExe.execute() - end");
        log.info("TcItemMethodClassesQueryAndSaveCmdExe.execute() 完成落库, 对应的落库数量表1:{}", tcCategoryEntityList.size());

        return ApiResult.success();
    }

    private List<TcItemMethodClassesResponse> queryResponseAll(String accessToken, String shopId){
        int pageNo = 1;
        int pageSize = 50;

        List<TcItemMethodClassesResponse> resultList = new ArrayList<>();

        do {
            TcItemMethodClassesDataResponse response = QueryShopInfoAction.queryItemMethodClassesList(tcConfig, accessToken, pageNo, pageSize, shopId);

            if(ObjectUtils.isEmpty(response) || ObjectUtils.isEmpty(response.getData())){
                break;
            }

            resultList.addAll(response.getData().getMethodClass());

            if(pageNo >= response.getData().getPageInfo().getPageTotal()){
                break;
            }

            pageNo++;
        }while (true);

        return resultList;
    }
}
