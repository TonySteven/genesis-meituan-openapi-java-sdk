package com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.IMtShopInfoDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.MtShopInfoEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.base.BaseVO;
import com.sankuai.meituan.waimai.opensdk.factory.APIFactory;
import com.sankuai.meituan.waimai.opensdk.vo.PoiParam;
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
public class MtShopInfoQueryAndSaveCmdExe {

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
    private IMtShopInfoDao iMtShopInfoDao;

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
                    List<PoiParam> poiParamList = APIFactory.getPoiAPI().poiMget(systemParam, shopId);
                    // 2.落库.
                    iMtShopInfoDao.saveOrUpdateBatch(createMtShopInfoEntity(poiParamList));
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
    private List<MtShopInfoEntity> createMtShopInfoEntity(List<PoiParam> poiParamList) {

        // 1. 创建返回值
        List<MtShopInfoEntity> result = new ArrayList<>();

        // 2. 遍历poiParamList,转换为MtShopInfoEntity对象.
        for (PoiParam poiParam : poiParamList) {
            // 2.1 创建MtShopInfoEntity对象.
            MtShopInfoEntity shopInfoEntity = MtShopInfoEntity.builder()
                // id
                .id(poiParam.getApp_poi_code())
                // 品牌
                .brand("米村")
                // 美团门店id
                .shopId(poiParam.getApp_poi_code())
                // 是否上线
                .isOnline(poiParam.getIs_online())
                // 更新时间
                .utime(String.valueOf(poiParam.getUtime()))
                // 门店电话
                .phone(poiParam.getPhone())
                // 备用电话
                .standbyTel(poiParam.getStandby_tel())
                // 门店图片
                .picUrl(poiParam.getPic_url())
                // 营业状态
                .openLevel(poiParam.getOpen_level())
                // 城市id
                .cityId(String.valueOf(poiParam.getCity_id()))
                // 门店名称
                .name(poiParam.getName())
                // 最小预定天数
                .preBookMinDays(poiParam.getPre_book_min_days())
                // 经度
                .longitude(String.valueOf(poiParam.getLatitude()))
                // 纬度
                .latitude(String.valueOf(poiParam.getLatitude()))
                // 促销信息
                .promotionInfo(poiParam.getPromotion_info())
                // 标签名称
                .tagName(poiParam.getTag_name())
                // 配送费
                .shippingFee(String.valueOf(poiParam.getShipping_fee()))
                // 创建时间
                .ctime(String.valueOf(poiParam.getCtime()))
                // location_id
                .locationId(poiParam.getLocation_id())
                // 是否支持预定
                .preBook(poiParam.getPre_book())
                // 是否支持开发票
                .invoiceSupport(poiParam.getInvoice_support())
                // 配送时间
                .shippingTime(poiParam.getShipping_time())
                // 门店地址
                .address(poiParam.getAddress())
                // 最大预定天数
                .preBookMaxDays(poiParam.getPre_book_max_days())
                // 开发票最低消费
                .invoiceMinPrice(poiParam.getInvoice_min_price())
                // 是否支持时间选择
                .timeSelect(poiParam.getTime_select())
                // 开发票说明
                .invoiceDescription(poiParam.getInvoice_description())
                // 第三方标签名称
                .thirdTagName(poiParam.getThird_tag_name())
                // 创建人
                .createBy("system")
                // 创建时间
                .createTime(LocalDateTime.now())
                // 更新人
                .updateBy("system")
                // 更新时间
                .updateTime(LocalDateTime.now())
                .build();

            // 2.2 新增到返回值中.
            result.add(shopInfoEntity);
        }


        return result;
    }

}
