package com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor;

import com.genesis.org.cn.genesismeituanopenapijavasdk.config.TcConfig;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.IScmGysDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.ScmGysEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.result.ApiResult;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.QueryShopInfoAction;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcScmGysDataResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcScmGysResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * tc get item info query and save cmd exe
 * 天财SaaS-调用查询供应商实时并落库api具体逻辑.
 *
 * @author LP
 * &#064;date  2023/12/10
 */
@Service
@Slf4j
public class TcScmGysQueryAndSaveCmdExe {

    /**
     * 天才配置
     */
    @Resource
    private TcConfig tcConfig;

    @Resource
    private IScmGysDao scmGysDao;

    /**
     * execute
     * 执行
     *
     * @return {@link ApiResult}
     */
    @SneakyThrows
    public ApiResult<Object> execute() {
        // 打印日志 - 开始.
        log.info("TcScmPxQueryAndSaveCmdExe.execute() - start");

        return syncData();
    }

    public ApiResult<Object> syncData() {
        // 2. 调用天财接口获取所有供应商信息实时信息.
        // 2.0 先同步集团信息.
        List<TcScmGysResponse> responseList = queryResponseAll();

        if(ObjectUtils.isEmpty(responseList)){
            return ApiResult.success("未获取到供应商");
        }

        // 2.3 将查询出来的供应商信息信息转成实体类.
        List<ScmGysEntity> tcCategoryEntityList = ScmGysEntity.toEntityListByResponse(responseList);

        // 3 操作数据库
        // 3.0 查询数据库中已有的信息.
        Map<String, ScmGysEntity> dbMap = scmGysDao.getMap();

        // 3.1 根据入库list列表查询数据库是否存在该数据，如果存在直接更新，不存在就新增
        List<ScmGysEntity> saveList = new ArrayList<>();

        List<ScmGysEntity> updateList = new ArrayList<>();

        for (ScmGysEntity responseEntity : tcCategoryEntityList) {
            // 3.1.1 查询数据库中是否存在该信息.
            ScmGysEntity tcEntity = dbMap.get(responseEntity.getSupplierId());
            // 3.1.2 如果数据库中不存在该信息,则新增.
            if (ObjectUtils.isEmpty(tcEntity)) {
                saveList.add(responseEntity);
            }else if(!responseEntity.equals(tcEntity)){
                // 3.1.3 如果数据库中存在该信息,则更新.
                updateList.add(responseEntity);
            }
        }

        // 3.2 批量操作数据库.
        scmGysDao.operateBatch(saveList, updateList);


        // 打印日志 - 结束.
        log.info("TcScmPxQueryAndSaveCmdExe.execute() - end");
        log.info("TcScmPxQueryAndSaveCmdExe.execute() 完成落库, 对应的落库数量表:{}", tcCategoryEntityList.size());

        return ApiResult.success();
    }

    private List<TcScmGysResponse> queryResponseAll(){
        List<TcScmGysResponse> list = new ArrayList<>();
        try {
            TcScmGysDataResponse response = QueryShopInfoAction.queryScmGysList(tcConfig);

            list.addAll(response.getData());
        }catch (Exception e){
            log.error("【scm px】查询供应商异常:{}",e.getMessage(),e);
        }
        return list;
    }
}
