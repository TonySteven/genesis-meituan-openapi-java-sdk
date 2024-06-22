package com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor;

import cn.hutool.core.date.DatePattern;
import com.genesis.org.cn.genesismeituanopenapijavasdk.config.TcConfig;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.IScmDjmxDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.ScmDjmxEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request.TcScmDjmxCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.result.ApiResult;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.DateUtils;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.QueryShopInfoAction;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.request.TcScmDjmxRequest;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcScmDjmxDataResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcScmDjmxResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * tc get item info query and save cmd exe
 * 天财SaaS-调用查询单据明细实时并落库api具体逻辑.
 *
 * @author LP
 * &#064;date  2023/12/10
 */
@Service
@Slf4j
public class TcScmDjmxQueryAndSaveCmdExe {

    /**
     * 天才配置
     */
    @Resource
    private TcConfig tcConfig;

    @Resource
    private IScmDjmxDao scmDjmxDao;

    /**
     * execute
     * 执行
     *
     * @return {@link ApiResult}
     */
    public ApiResult<Object> execute(TcScmDjmxCmd cmd) {
        // 打印日志 - 开始.
        log.info("TcScmDjmxQueryAndSaveCmdExe.execute() - start");

        // 校验cmd.getItemQueryCmd().getLastTime()是否为空,如果为空,则默认查询前两天的数据.
        if (ObjectUtils.isEmpty(cmd.getBeginDate()) || ObjectUtils.isEmpty(cmd.getEndDate())) {
            // 打印日志 进入默认查询前一天的数据
            log.info("TcScmDjmxQueryAndSaveCmdExe cmd.getBeginDate() or cmd.getEndDate() is blank" +
                ", default query previous day data.");
            // 获取前一天的时间的0点和24点.
            // 获取当天最大时间
            LocalDateTime now = DateUtils.withEndLocalTime(LocalDateTime.now());

            if(ObjectUtils.isEmpty(cmd.getBeginDate())){
                // 获取三天内的0点 因为前一天的分账状态不一定是终态，往前推迟一天
                LocalDateTime startOfDay = DateUtils.withStartLocalTime(now.minusDays(2));

                cmd.setBeginDate(startOfDay);
            }

            if(ObjectUtils.isEmpty(cmd.getEndDate())){
                // 结束时间为空，设置结束时间为当前时间
                cmd.setEndDate(now);
            }
        }
        return syncData(cmd);
    }

    public ApiResult<Object> syncData(TcScmDjmxCmd cmd) {
        log.info("TcScmDjmxQueryAndSaveCmdExe.syncData() - start,cmd:{}",cmd);
        try {

            // 获取两个时间相差天数
            long daysBetween = ChronoUnit.DAYS.between(cmd.getBeginDate(), cmd.getEndDate());

            int responseCount = 0;

            // 2.1 查询新单据列表.
            // 循环天数 获取单据信息
            for(long i = 0;i<=daysBetween;i++){
                // 设置业务时间
                LocalDateTime busDate;
                if(i > 0){
                    busDate = cmd.getBeginDate().plusDays(i);
                }else{
                    busDate = cmd.getBeginDate();
                }
                List<TcScmDjmxResponse> responseList = queryResponseAll(busDate.format(DatePattern.NORM_DATE_FORMATTER),cmd);


                // 2. 调用天财接口获取所有单据明细信息实时信息.
                // 2.0 查询反审的单据
                List<TcScmDjmxResponse> deleteResponseList = queryDeleteResponseAll(DateUtils.withStartLocalTime(busDate),DateUtils.withEndLocalTime(busDate));

                List<ScmDjmxEntity> saveList = new ArrayList<>();

                List<ScmDjmxEntity> updateList = new ArrayList<>();

                List<String> removeList = new ArrayList<>();

                if(ObjectUtils.isNotEmpty(deleteResponseList)){
                    deleteResponseList.forEach(response -> removeList.add(response.getStoreBillID()));
                }

                if(ObjectUtils.isNotEmpty(responseList)){

                    // 2.3 将查询出来的单据明细信息信息转成实体类.
                    List<ScmDjmxEntity> tcCategoryEntityList = ScmDjmxEntity.toEntityListByResponse(responseList);

                    if(ObjectUtils.isEmpty(tcCategoryEntityList)){
                        return ApiResult.success();
                    }

                    // 获取三方信息主键
                    List<String> zhujianList = tcCategoryEntityList.stream().map(ScmDjmxEntity::getZhujian).toList();

                    // 3 操作数据库
                    // 3.0 查询数据库中已有的信息.
                    Map<String, ScmDjmxEntity> dbMap = scmDjmxDao.getMap(zhujianList);

                    // 3.1 根据入库list列表查询数据库是否存在该数据，如果存在直接更新，不存在就新增

                    for (ScmDjmxEntity responseEntity : tcCategoryEntityList) {
                        // 3.1.1 查询数据库中是否存在该信息.
                        ScmDjmxEntity tcEntity = dbMap.get(responseEntity.getZhujian());
                        // 3.1.2 如果数据库中不存在该信息,则新增.
                        if (ObjectUtils.isEmpty(tcEntity)) {
                            saveList.add(responseEntity);
                        }else if(!responseEntity.equals(tcEntity)){
                            // 3.1.3 如果数据库中存在该信息,则更新.
                            updateList.add(responseEntity);
                        }
                    }
                }

                // 3.2 批量操作数据库.
                scmDjmxDao.operateBatch(saveList, updateList,removeList);

                responseCount += responseList.size() + deleteResponseList.size();
            }


            // 打印日志 - 结束.
            log.info("TcScmDjmxQueryAndSaveCmdExe.execute() - end");
            log.info("TcScmDjmxQueryAndSaveCmdExe.execute() 完成落库, 对应的落库数量表:{}", responseCount);

            return ApiResult.success();
        }catch (Exception e){
            log.error("TcScmDjmxQueryAndSaveCmdExe.execute() - 落库异常",e);
            throw e;
        }
    }

    private List<TcScmDjmxResponse> queryResponseAll(String busDate,TcScmDjmxCmd cmd){
        List<TcScmDjmxResponse> list = new ArrayList<>();
        try {
            TcScmDjmxRequest request = new TcScmDjmxRequest();
            request.setBusDate(busDate);
            request.setBillNo(cmd.getBillNo());
            TcScmDjmxDataResponse response = QueryShopInfoAction.queryScmDjmxList(tcConfig,request);

            list.addAll(response.getData());
        }catch (Exception e){
            log.error("【scm px】查询品项异常:{}",e.getMessage(),e);
        }
        return list;
    }

    private List<TcScmDjmxResponse> queryDeleteResponseAll(LocalDateTime beginDate,LocalDateTime endDate){
        List<TcScmDjmxResponse> list = new ArrayList<>();
        try {
            TcScmDjmxRequest request = new TcScmDjmxRequest();
            request.setBeginDate(beginDate.format(DatePattern.NORM_DATETIME_FORMATTER));
            request.setEndDate(endDate.format(DatePattern.NORM_DATETIME_FORMATTER));
//            request.setBeginDate(cmd.getBeginDate().format(DatePattern.NORM_DATETIME_FORMATTER));
//            request.setEndDate(cmd.getEndDate().format(DatePattern.NORM_DATETIME_FORMATTER));
            TcScmDjmxDataResponse response = QueryShopInfoAction.queryDeleteScmDjmxList(tcConfig,request);

            list.addAll(response.getData());
        }catch (Exception e){
            log.error("【scm px】查询品项异常:{}",e.getMessage(),e);
        }
        return list;
    }
}
