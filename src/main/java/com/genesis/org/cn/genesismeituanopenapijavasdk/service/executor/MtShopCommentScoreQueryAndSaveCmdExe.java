package com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.IMtShopScoreDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.MtShopScoreEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.base.BaseVO;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.mt.api.response.model.MtShopCommentScoreResponseData;
import com.sankuai.meituan.waimai.opensdk.factory.APIFactory;
import com.sankuai.meituan.waimai.opensdk.vo.SystemParam;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 美团SaaS-调用查询所有门店总评分并落库.
 *
 * @author steven
 * &#064;date  2023/10/29
 */
@Service
@Slf4j
public class MtShopCommentScoreQueryAndSaveCmdExe {

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

    @Resource
    private IMtShopScoreDao iMtShopScoreDao;

    /**
     * execute
     * 执行
     *
     * @return {@link BaseVO}
     */
    @SneakyThrows
    public BaseVO execute() {
        // 1. 根据美团AppId和AppSecret获取SystemParam.
        SystemParam systemParam = new SystemParam(appId, appSecret);

        // 如果shopIds为空,则更新所有门店评论.
        // 先查询所有门店id.
        String result = APIFactory.getPoiAPI().poiGetIds(systemParam);
        // 如果返回结果不为空,则转换为MtShopIdResponse对象.
        if (StrUtil.isNotBlank(result)) {
            // JSONArray 转换 List<String> 类型
            // 获取MtShopIdResponse对象中的data.
            List<String> responseShopIdList = JSON.parseArray(result, String.class);
            // 如果responseShopIdList不为空,则遍历查询门店详情并落库.
            if (CollectionUtil.isNotEmpty(responseShopIdList)) {
                for (String shopId : responseShopIdList) {
                    // 1.查询门店详情.
                    // SystemParam systemParam, String appPoiCodes
                    String poiCommentScoreResponseString = APIFactory.getPoiAPI().poiCommentScore(systemParam, shopId);
                    // 如果返回结果不为空,则转换为MtShopCommentScoreResponse对象.
                    if (StrUtil.isNotBlank(poiCommentScoreResponseString)) {
                        // JSONArray 转换 List<String> 类型
                        // 获取poiCommentScoreResponse中的data.
                        MtShopCommentScoreResponseData mtShopCommentScoreResponseData
                            = JSON.parseObject(poiCommentScoreResponseString, MtShopCommentScoreResponseData.class);

                        // 2.落库.
                        iMtShopScoreDao.saveOrUpdate(createMtShopScoreEntity(shopId
                            , mtShopCommentScoreResponseData));
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


    // 创建根据List<MtShopCommentResponseData> mtShopCommentResponseDataList 生成 MtShopEntity 的方法.
    private MtShopScoreEntity createMtShopScoreEntity(String shopId
        , MtShopCommentScoreResponseData mtShopCommentResponseData) {

        // 1 创建MtShopScoreEntity对象.
        return MtShopScoreEntity.builder()
            // id
            .id(shopId)
            // 写死品牌为米村.
            .brand("米村")
            // 美团门店id
            .shopId(shopId)
            // 平均评分
            .avgPoiScore(BigDecimal.valueOf(mtShopCommentResponseData.getAvgPoiScore()))
            // 口味评分
            .avgTasteScore(BigDecimal.valueOf(mtShopCommentResponseData.getAvgTasteScore()))
            // 包装评分
            .avgPackingScore(BigDecimal.valueOf(mtShopCommentResponseData.getAvgPackingScore()))
            // 配送评分
            .avgDeliveryScore(BigDecimal.valueOf(mtShopCommentResponseData.getAvgDeliveryScore()))
            // 配送满意度
            .deliverySatisfaction(mtShopCommentResponseData.getDeliverySatisfaction())
            // 创建人
            .createBy("system")
            // 创建时间
            .createTime(LocalDateTime.now())
            // 更新人
            .updateBy("system")
            // 更新时间
            .updateTime(LocalDateTime.now())
            .build();

    }

}
