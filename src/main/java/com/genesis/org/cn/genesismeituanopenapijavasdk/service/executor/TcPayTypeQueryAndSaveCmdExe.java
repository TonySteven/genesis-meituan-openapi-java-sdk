package com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor;

import com.genesis.org.cn.genesismeituanopenapijavasdk.config.TcConfig;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcPayTypeDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcPayTypeEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.result.ApiResult;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.LoginToServerAction;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.QueryShopInfoAction;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcPayTypeDataResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcPayTypeResponse;
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
 * 天财SaaS-调用查询结算方式类型实时并落库api具体逻辑.
 *
 * @author LP
 * &#064;date  2023/12/10
 */
@Service
@Slf4j
public class TcPayTypeQueryAndSaveCmdExe {

    /**
     * 天才配置
     */
    @Resource
    private TcConfig tcConfig;

    @Resource
    private ITcPayTypeDao tcPayTypeDao;

    /**
     * execute
     * 执行
     *
     * @return {@link ApiResult}
     */
    @SneakyThrows
    public ApiResult<Object> execute() {
        // 打印日志 - 开始.
        log.info("TcPayTypeQueryAndSaveCmdExe.execute() - start");
        // 1. 根据天财AppId和accessId进行鉴权.
        String accessToken = LoginToServerAction.getAccessToken(tcConfig);
        // 打印日志 - 鉴权成功.
        log.info("TcPayTypeQueryAndSaveCmdExe.execute() 鉴权成功, accessToken:{}", accessToken);

        // 2. 调用天财接口获取所有结算方式类型实时信息.
        // 2.0 先同步集团信息.
        List<TcPayTypeResponse> tcResponses = queryItemCategoryAll(accessToken);

        // 2.2 将查询出来的结算方式类型去重
        List<TcPayTypeResponse> responseList = tcResponses.stream().distinct().toList();

        if(ObjectUtils.isEmpty(responseList)){
            return ApiResult.success("未获取到结算方式类型");
        }

        // 2.3 将查询出来的结算方式类型信息转成实体类.
        List<TcPayTypeEntity> tcEntityList = TcPayTypeEntity.toEntityListByResponse(tcResponses,tcConfig.getApi().getCenterId());

        // 3 操作数据库
        // 3.0 查询数据库中已有的信息.
        Map<String, TcPayTypeEntity> dbMap = tcPayTypeDao.getMapByCenterId(tcConfig.getApi().getCenterId());

        // 3.1 根据入库list列表查询数据库是否存在该数据，如果存在直接更新，不存在就新增
        List<TcPayTypeEntity> saveList = new ArrayList<>();

        List<TcPayTypeEntity> updateList = new ArrayList<>();

        for (TcPayTypeEntity responseEntity : tcEntityList) {
            // 3.1.1 查询数据库中是否存在该信息.
            TcPayTypeEntity tcCategoryEntity = dbMap.get(responseEntity.getId());
            // 3.1.2 如果数据库中不存在该信息,则新增.
            if (ObjectUtils.isEmpty(tcCategoryEntity)) {
                saveList.add(responseEntity);
            }else{
                // 3.1.3 如果数据库中存在该信息,则更新.
                updateList.add(responseEntity);
            }
        }

        // 3.2 批量操作数据库.
        tcPayTypeDao.operateBatch(saveList, updateList);


        // 打印日志 - 结束.
        log.info("TcPayTypeQueryAndSaveCmdExe.execute() - end");
        log.info("TcPayTypeQueryAndSaveCmdExe.execute() 完成落库, 对应的落库数量表1:{}", tcEntityList.size());

        return ApiResult.success();
    }

    private List<TcPayTypeResponse> queryItemCategoryAll(String accessToken){
        int pageNo = 1;
        int pageSize = 50;

        List<TcPayTypeResponse> resultList = new ArrayList<>();

        do {
            TcPayTypeDataResponse response = QueryShopInfoAction.queryPayTypeList(tcConfig, accessToken, pageNo, pageSize);

            if(ObjectUtils.isEmpty(response) || ObjectUtils.isEmpty(response.getData())){
                break;
            }

            resultList.addAll(response.getData().getPaytype());

            if(pageNo >= response.getData().getPageInfo().getTotalSize()){
                break;
            }

            pageNo++;
        }while (true);

        return resultList;
    }
}
