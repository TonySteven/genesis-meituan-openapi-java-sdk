package com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.IMtShopDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.MtShopEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.base.BaseVO;
import com.sankuai.meituan.waimai.opensdk.factory.APIFactory;
import com.sankuai.meituan.waimai.opensdk.vo.SystemParam;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 美团SaaS-查询shopId并落库api具体逻辑.
 *
 * @author steven
 * &#064;date  2023/10/29
 */
@Service
@Slf4j
public class MtShopIdQueryAndSaveCmdExe {

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
    private IMtShopDao iMtShopDao;

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
        // 打印日志.
        log.info("查询所有门店id结果:{}", result);
        // 如果返回结果不为空,则转换为MtShopIdResponse对象.
        if (StrUtil.isNotBlank(result)) {
            // JSONArray 转换 List<String> 类型
            // 获取MtShopIdResponse对象中的data.
            List<String> responseShopIdList = JSON.parseArray(result, String.class);
            // 如果responseShopIdList不为空,则落库.
            if (CollectionUtil.isNotEmpty(responseShopIdList)) {
                // 落库.
                iMtShopDao.saveOrUpdateBatch(createMtShopEntity(responseShopIdList));
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
    private List<MtShopEntity> createMtShopEntity(List<String> responseShopIdList) {

        // 1. 创建返回值
        List<MtShopEntity> result = new ArrayList<>();

        // 2. 遍历mtShopCommentResponseDataList,转换为MtShopEntity对象.
        for (String shopId : responseShopIdList) {
            // 2.1 创建MtShopEntity对象.
            MtShopEntity mtShopEntity = MtShopEntity.builder()
                .id(shopId)
                // 写死品牌为米村.
                .brand("米村")
                .shopId(shopId)
                // 创建人
                .createBy("system")
                // 创建时间
                .createTime(LocalDateTime.now())
                // 更新人
                .updateBy("system")
                // 更新时间
                .updateTime(LocalDateTime.now())
                .build();
            // 2.2 添加到result中.
            result.add(mtShopEntity);
        }

        return result;
    }

}
