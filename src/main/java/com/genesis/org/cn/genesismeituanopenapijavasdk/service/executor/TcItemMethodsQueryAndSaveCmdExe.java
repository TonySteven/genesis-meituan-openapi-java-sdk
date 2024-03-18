package com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor;

import com.genesis.org.cn.genesismeituanopenapijavasdk.config.TcConfig;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcItemMethodsDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcShopDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcItemMethodsEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcShopEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.result.ApiResult;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.LoginToServerAction;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.QueryShopInfoAction;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcItemMethodsDataResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcItemMethodsResponse;
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
 * 天财SaaS-调用查询品项做法档案信息实时并落库api具体逻辑.
 *
 * @author LP
 * &#064;date  2023/12/10
 */
@Service
@Slf4j
public class TcItemMethodsQueryAndSaveCmdExe {

    /**
     * 天才配置
     */
    @Resource
    private TcConfig tcConfig;

    @Resource
    private ITcShopDao iTcShopDao;

    @Resource
    private ITcItemMethodsDao iTcItemMethodsDao;

    /**
     * execute
     * 执行
     *
     * @return {@link ApiResult}
     */
    @SneakyThrows
    public ApiResult<Object> execute() {
        // 打印日志 - 开始.
        log.info("TcItemMethodsQueryAndSaveCmdExe.execute() - start");
        // 1. 根据天财AppId和accessId进行鉴权.
        String accessToken = LoginToServerAction.getAccessToken(tcConfig);
        // 打印日志 - 鉴权成功.
        log.info("TcItemMethodsQueryAndSaveCmdExe.execute() 鉴权成功, accessToken:{}", accessToken);

        // 2. 调用天财接口获取所有品项做法档案信息实时信息.
        // 2.0 先同步集团信息.
        List<TcItemMethodsResponse> tcItemMethodClassesResponses = queryItemCategoryAll(accessToken, null,null);

        // 2.1 获取所有店铺ids
        List<TcShopEntity> tcShopEntityList = iTcShopDao.list();
        List<String> shopIds = tcShopEntityList.stream().map(TcShopEntity::getShopId).toList();
        // 遍历shopIds,获取每个shopId的账单明细实时信息.
        for(String shopId : shopIds){
            tcItemMethodClassesResponses.addAll(queryItemCategoryAll(accessToken, shopId,null));
        }

        // 2.2 将查询出来的品项做法档案信息去重
        List<TcItemMethodsResponse> responseList = tcItemMethodClassesResponses.stream().distinct().toList();

        if(ObjectUtils.isEmpty(responseList)){
            return ApiResult.success("未获取到品项做法档案信息");
        }

        // 2.3 将查询出来的品项做法档案信息信息转成实体类.
        List<TcItemMethodsEntity> tcCategoryEntityList = TcItemMethodsEntity.toEntityListByResponse(tcConfig.getApi().getCenterId(),tcItemMethodClassesResponses);

        // 3 操作数据库
        // 3.0 查询数据库中已有的信息.
        Map<String, TcItemMethodsEntity> dbMap = iTcItemMethodsDao.getMapByCenterId(tcConfig.getApi().getCenterId());

        // 3.1 根据入库list列表查询数据库是否存在该数据，如果存在直接更新，不存在就新增
        List<TcItemMethodsEntity> saveList = new ArrayList<>();

        List<TcItemMethodsEntity> updateList = new ArrayList<>();

        for (TcItemMethodsEntity responseEntity : tcCategoryEntityList) {
            // 3.1.1 查询数据库中是否存在该信息.
            TcItemMethodsEntity tcEntity = dbMap.get(responseEntity.getId());
            // 3.1.2 如果数据库中不存在该信息,则新增.
            if (ObjectUtils.isEmpty(tcEntity)) {
                saveList.add(responseEntity);
            }else if(!responseEntity.equals(tcEntity)){
                // 3.1.3 如果数据库中存在该信息,则更新.
                updateList.add(responseEntity);
            }
        }

        // 3.2 批量操作数据库.
        iTcItemMethodsDao.operateBatch(saveList, updateList);


        // 打印日志 - 结束.
        log.info("TcItemMethodsQueryAndSaveCmdExe.execute() - end");
        log.info("TcItemMethodsQueryAndSaveCmdExe.execute() 完成落库, 对应的落库数量表1:{}", tcCategoryEntityList.size());

        return ApiResult.success();
    }

    private List<TcItemMethodsResponse> queryItemCategoryAll(String accessToken, String shopId, String classId){
        int pageNo = 1;
        int pageSize = 50;

        List<TcItemMethodsResponse> resultList = new ArrayList<>();

        do {
            TcItemMethodsDataResponse response = QueryShopInfoAction.queryItemMethodsList(tcConfig, accessToken, pageNo, pageSize, shopId,classId);

            if(ObjectUtils.isEmpty(response) || ObjectUtils.isEmpty(response.getData())){
                break;
            }

            resultList.addAll(response.getData().getMethod());

            if(pageNo >= response.getData().getPageInfo().getPageTotal()){
                break;
            }

            pageNo++;
        }while (true);

        return resultList;
    }
}
