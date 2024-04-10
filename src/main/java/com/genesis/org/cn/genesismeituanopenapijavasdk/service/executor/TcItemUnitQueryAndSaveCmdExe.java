package com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor;

import com.genesis.org.cn.genesismeituanopenapijavasdk.config.TcConfig;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcItemUnitDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcItemUnitEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.result.ApiResult;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.LoginToServerAction;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.QueryShopInfoAction;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcItemUnitDataResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcItemUnitResponse;
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
 * 天财SaaS-调用查询品项单位信息实时并落库api具体逻辑.
 *
 * @author LP
 * &#064;date  2023/12/10
 */
@Service
@Slf4j
public class TcItemUnitQueryAndSaveCmdExe {

    /**
     * 天才配置
     */
    @Resource
    private TcConfig tcConfig;

    @Resource
    private ITcItemUnitDao iTcItemUnitDao;

    /**
     * execute
     * 执行
     *
     * @return {@link ApiResult}
     */
    @SneakyThrows
    public ApiResult<Object> execute() {
        // 打印日志 - 开始.
        log.info("TcItemUnitQueryAndSaveCmdExe.execute() - start");
        // 1. 根据天财AppId和accessId进行鉴权.
        String accessToken = LoginToServerAction.getAccessToken(tcConfig);
        // 打印日志 - 鉴权成功.
        log.info("TcItemUnitQueryAndSaveCmdExe.execute() 鉴权成功, accessToken:{}", accessToken);

        return syncData(accessToken);
    }

    public ApiResult<Object> syncData(String accessToken) {
        // 2. 调用天财接口获取所有品项单位信息实时信息.
        // 2.0 先同步集团信息.
        List<TcItemUnitResponse> responseList = queryResponseAll(accessToken,null);

        if(ObjectUtils.isEmpty(responseList)){
            return ApiResult.success("未获取到品项单位信息");
        }

        // 2.3 将查询出来的品项单位信息信息转成实体类.
        List<TcItemUnitEntity> tcCategoryEntityList = TcItemUnitEntity.toEntityListByResponse(tcConfig.getApi().getCenterId(),responseList);

        // 3 操作数据库
        // 3.0 查询数据库中已有的信息.
        Map<String, TcItemUnitEntity> dbMap = iTcItemUnitDao.getMapByCenterId(tcConfig.getApi().getCenterId());

        // 3.1 根据入库list列表查询数据库是否存在该数据，如果存在直接更新，不存在就新增
        List<TcItemUnitEntity> saveList = new ArrayList<>();

        List<TcItemUnitEntity> updateList = new ArrayList<>();

        for (TcItemUnitEntity responseEntity : tcCategoryEntityList) {
            // 3.1.1 查询数据库中是否存在该信息.
            TcItemUnitEntity tcEntity = dbMap.get(responseEntity.getId());
            // 3.1.2 如果数据库中不存在该信息,则新增.
            if (ObjectUtils.isEmpty(tcEntity)) {
                saveList.add(responseEntity);
            }else if(!responseEntity.equals(tcEntity)){
                // 3.1.3 如果数据库中存在该信息,则更新.
                updateList.add(responseEntity);
            }
        }

        // 3.2 批量操作数据库.
        iTcItemUnitDao.operateBatch(saveList, updateList);


        // 打印日志 - 结束.
        log.info("TcItemUnitQueryAndSaveCmdExe.execute() - end");
        log.info("TcItemUnitQueryAndSaveCmdExe.execute() 完成落库, 对应的落库数量表1:{}", tcCategoryEntityList.size());

        return ApiResult.success();
    }

    private List<TcItemUnitResponse> queryResponseAll(String accessToken,String shopId){
        int pageNo = 1;
        int pageSize = 50;

        List<TcItemUnitResponse> resultList = new ArrayList<>();

//        do {
            TcItemUnitDataResponse response = QueryShopInfoAction.queryItemUnitList(tcConfig, accessToken, pageNo, pageSize, shopId);

            if(ObjectUtils.isEmpty(response) || ObjectUtils.isEmpty(response.getData())){
                return resultList;
            }

            resultList.addAll(response.getData().getUnit());

//            if(pageSize > response.getData().getUnit().size()){
//                break;
//            }
//
//            pageNo++;
//        }while (true);

        return resultList;
    }
}
