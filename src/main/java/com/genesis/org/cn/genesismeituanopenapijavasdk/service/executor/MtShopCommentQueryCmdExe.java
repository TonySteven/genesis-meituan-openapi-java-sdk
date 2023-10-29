package com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.IMtShopCommentDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.MtShopCommentEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.base.BaseVO;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request.MtShopCommentQryCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.mt.api.response.MtShopCommentResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.mt.api.response.model.MtShopCommentResponseData;
import com.sankuai.meituan.waimai.opensdk.factory.APIFactory;
import com.sankuai.meituan.waimai.opensdk.vo.SystemParam;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 美团SaaS-定时调用并落库api具体逻辑.
 *
 * @author steven
 * &#064;date  2023/10/29
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class MtShopCommentQueryCmdExe {

    /**
     * 美团AppId
     */
    @Value("${meituan.api.appId}")
    private String appId;

    /**
     * 美团AppSecret
     */
    @Value("${meituan.api.appSecret}")
    private String appSecret;

    /**
     * 美团AppSecret
     */
    @Value("${meituan.api.maxCommentCount}")
    private String maxCommentCount;

    @Resource
    private IMtShopCommentDao iMtShopCommentDao;

    /**
     * execute
     * 执行
     *
     * @param cmd cmd
     * @return {@link BaseVO}
     */
    @SneakyThrows
    public BaseVO execute(MtShopCommentQryCmd cmd) {
        // 1. 根据美团AppId和AppSecret获取SystemParam.
        SystemParam systemParam = new SystemParam(appId, appSecret);

        // 2. 业务校验.
        // 2.1 根据shopId查询是否存在.
        List<String> shopIds = cmd.getShopIds();
        String beginDate = cmd.getBeginDate();
        String endDate = cmd.getEndDate();

        // 3. 执行业务逻辑.
        // 如果shopIds不为空,则更新指定门店评论.
        if (CollectionUtil.isNotEmpty(shopIds)) {
            // 遍历shopIds,查询每个门店的评论.
            for (String shopId : shopIds) {
                queryShopCommentAndSave(shopId, beginDate, endDate, systemParam);
            }

        } else {
            // 如果shopIds为空,则更新所有门店评论.
            // 先查询所有门店id.
            String result = APIFactory.getPoiAPI().poiGetIds(systemParam);
            // 打印日志.
            log.info("查询所有门店id结果:{}", result);
            // 如果返回结果不为空,则转换为MtShopIdResponse对象.
            if (StrUtil.isNotBlank(result)) {
                // result(["140476","142037"]) 转换为List<String>对象.
                result = result.replace("[", "").replace("]", "");
                result = result.replace("\"", "");
                result = result.replace(" ", "");
                result = result.replace("\n", "");
                result = result.replace("\r", "");
                String[] resultArray = result.split(",");
                // 获取MtShopIdResponse对象中的data.
                List<String> responseShopIdList = Arrays.asList(resultArray);
                // 如果data不为空,则遍历data,查询每个门店的评论.
                if (CollectionUtil.isNotEmpty(responseShopIdList)) {
                    // 遍历shopIds,查询每个门店的评论.
                    for (String shopId : responseShopIdList) {
                        queryShopCommentAndSave(shopId, beginDate, endDate, systemParam);
                    }
                }
            }
        }

        // 返回参数.
        return BaseVO.builder()
            .id("1")
            .state("success")
            .msg("成功")
            .build();
    }

    /**
     * query shop comment and save
     *
     * @param shopId      shop id
     * @param beginDate   begin date
     * @param endDate     end date
     * @param systemParam system param
     */
    private void queryShopCommentAndSave(String shopId, String beginDate, String endDate, SystemParam systemParam) {
        // 调用根据shopId查询评论方法.
        List<MtShopCommentResponseData> mtShopCommentResponseDataList = this.queryCommentByShopId(systemParam
            , shopId, beginDate, endDate);

        // 如果mtShopCommentResponseDataList不为空,则落库.
        if (CollectionUtil.isNotEmpty(mtShopCommentResponseDataList)) {
            // 落库.
            iMtShopCommentDao.saveBatch(createMtShopCommentEntity(shopId, mtShopCommentResponseDataList));
        }
    }

    /**
     * 根据shopId查询评论.
     *
     * @param systemParam system param
     * @param beginDate   begin date
     * @param endDate     end date
     * @param shopId      shop id
     * @return {@link List}<{@link MtShopCommentResponseData}>
     */
    @SneakyThrows
    private List<MtShopCommentResponseData> queryCommentByShopId(SystemParam systemParam, String shopId
        , String beginDate, String endDate) {

        // 设置返回对象.
        List<MtShopCommentResponseData> result = new ArrayList<>();
        // beginDateString转换成int类型.
        int beginDateInt = Integer.parseInt(beginDate);
        // endDateString转换成int类型.
        int endDateInt = Integer.parseInt(endDate);
        // maxCommentCount转换成int类型.
        int maxCommentCountInt = Integer.parseInt(this.maxCommentCount);
        // 设置最大步长
        int maxStep = 20;
        // 请求次数 maxCommentCountInt/maxStep + 1
        int requestCount = maxCommentCountInt / maxStep + 1;

        // 根据maxCommentCount 每次查询最大步长,查询所有评论.
        for (int i = 0; i < requestCount; i += maxStep) {
            // 查询门店评论.
            // 偏移量 = i * maxStep
            int offset = i * maxStep;
            // systemParam, String appPoiCode, int startTime, int endTime, int page-offset, int page-size
            String poiCommentSting = APIFactory.getPoiAPI().poiComment(systemParam, shopId, beginDateInt, endDateInt
                , offset, maxStep);
            // 打印日志.
            log.info("查询门店评论结果:{}", poiCommentSting);
            // 如果返回结果不为空,则转换为MtShopCommentResponse对象.
            if (StrUtil.isNotBlank(poiCommentSting)) {
                // poiCommentSting 转换为MtShopCommentResponse对象.
                MtShopCommentResponse mtShopCommentResponse = MtShopCommentResponse.parse(poiCommentSting);
                // 获取MtShopCommentResponse对象中的data.
                List<MtShopCommentResponseData> mtShopCommentResponseDataList = mtShopCommentResponse.getData();
                // 如果data不为空,则添加到result中.
                if (CollectionUtil.isNotEmpty(mtShopCommentResponseDataList)) {
                    result.addAll(mtShopCommentResponseDataList);
                }
            } else {
                // 如果返回结果为空,则跳出循环.
                break;
            }
        }

        return result;
    }

    // 创建根据List<MtShopCommentResponseData> mtShopCommentResponseDataList 生成 MtShopCommentEntity 的方法.
    private List<MtShopCommentEntity> createMtShopCommentEntity(String shopId
        , List<MtShopCommentResponseData> mtShopCommentResponseDataList) {

        // 1. 创建返回值
        List<MtShopCommentEntity> result = new ArrayList<>();

        // 2. 遍历mtShopCommentResponseDataList,转换为MtShopCommentEntity对象.
        for (MtShopCommentResponseData mtShopCommentResponseData : mtShopCommentResponseDataList) {
            // 2.1 创建MtShopCommentEntity对象.
            MtShopCommentEntity mtShopCommentEntity = MtShopCommentEntity.builder()
                // 写死品牌为米村.
                .brand("米村")
                .shopId(shopId)
                // 结果
                .result(mtShopCommentResponseData.getResult())
                // 评论id
                .commentId(mtShopCommentResponseData.getCommentId())
                // 评论内容
                .commentContent(mtShopCommentResponseData.getCommentContent())
                // 订单评分
                .orderCommentScore(mtShopCommentResponseData.getOrderCommentScore())
                // 食物评分
                .foodCommentScore(mtShopCommentResponseData.getFoodCommentScore())
                // 快递评分
                .deliveryCommentScore(mtShopCommentResponseData.getDeliveryCommentScore())
                // 新增评论
                .addComment(mtShopCommentResponseData.getAddComment())
                // 新增评论时间
                .addCommentTime(mtShopCommentResponseData.getAddCommentTime())
                // 包装评分
                .packingScore(mtShopCommentResponseData.getPackingScore())
                // 评论时间
                .commentTime(mtShopCommentResponseData.getCommentTime())
                // 评论标签
                .commentLables(mtShopCommentResponseData.getCommentLables())
                // 次数
                .shipTime(mtShopCommentResponseData.getShipTime())
                // 评论图片
                .commentPictures(mtShopCommentResponseData.getCommentPictures())
                // 点赞食物列表
                .praiseFoodList(mtShopCommentResponseData.getPraiseFoodList())
                // 吐槽食物列表
                .criticFoodList(mtShopCommentResponseData.getCriticFoodList())
                // 回复状态
                .replyStatus(mtShopCommentResponseData.getReplyStatus())
                // 超时原因
                .overDeliveryTimeDesc(mtShopCommentResponseData.getOverDeliveryTimeDesc())
                // 商家回复内容
                .eReplyContent(mtShopCommentResponseData.getEReplyContent())
                // 商家回复时间
                .eReplyTime(mtShopCommentResponseData.getEReplyTime())
                // 评论类型
                .commentType(mtShopCommentResponseData.getCommentType())
                // 是否有效
                .valid(mtShopCommentResponseData.getValid())
                .build();
            // 2.2 添加到result中.
            result.add(mtShopCommentEntity);
        }

        return result;
    }

}
