package com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcShopDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcShopGroupInfoDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcShopEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcShopGroupInfoEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.base.BaseVO;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.LoginToServerAction;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.QueryShopInfoAction;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.enums.ResponseStatusEnum;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.GroupList;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.LoginResult;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.QueryShopInfoDataResponseShopList;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.QueryShopInfoResponse;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.base.BasePageInfo;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 天财SaaS-查询shopId并落库api具体逻辑.
 *
 * @author steven
 * &#064;date  2023/10/29
 */
@Service
@Slf4j
public class TcShopInfoQueryAndSaveCmdExe {

    /**
     * 服务器请求协议
     */
    @Value("${tiancai.protocol}")
    private String protocol;

    /**
     * 服务器地址
     */
    @Value("${tiancai.url}")
    private String applicationServer;
    /**
     * 服务器端口
     */
    @Value("${tiancai.port}")
    private Integer applicationPort;

    /**
     * 天财AppId
     */
    @Value("${tiancai.api.appId}")
    private String appId;

    /**
     * 天财accessId
     */
    @Value("${tiancai.api.accessId}")
    private String accessId;

    /**
     * 餐饮集团ID
     */
    @Value("${tiancai.api.centerId}")
    private String centerId;

    @Resource
    private ITcShopDao iTcShopDao;

    @Resource
    private ITcShopGroupInfoDao iTcShopGroupInfoDao;

    /**
     * execute
     * 执行
     *
     * @return {@link BaseVO}
     */
    @SneakyThrows
    public BaseVO execute() {
        // 打印日志 - 开始.
        log.info("TcShopInfoQueryAndSaveCmdExe.execute() start");
        // 1. 根据天财AppId和accessId进行鉴权.
        LoginResult loginResult = LoginToServerAction.login(protocol, applicationServer, applicationPort
            , appId, accessId);
        String msg = loginResult.getMsg();
        // 如果msg不为success,则抛出异常.
        if (!ResponseStatusEnum.SUCCESS.getValue().equals(msg)) {
            throw new Exception("鉴权失败!");
        }
        // 如果msg为success,则获取accessToken.
        String accessToken = loginResult.getAccessToken();
        // 打印日志 - 鉴权成功.
        log.info("TcShopInfoQueryAndSaveCmdExe.execute() 鉴权成功, accessToken:{}", accessToken);

        // 2. 调用天财接口获取所有门店信息.
        // 初始化分页参数.
        Integer pageNo = 1;
        Integer pageSize = 50;
        // 2.1 获取所有门店信息.
        QueryShopInfoResponse queryShopInfoResponse = QueryShopInfoAction.queryShopInfo(protocol
            , applicationServer, applicationPort, accessId, accessToken, pageNo, pageSize);
        Boolean success = queryShopInfoResponse.getSuccess();
        // 如果success为false,则抛出异常.
        if (Boolean.FALSE.equals(success)) {
            throw new Exception("获取所有门店信息失败!");
        }
        // 如果success为true,则获取门店信息.
        List<QueryShopInfoDataResponseShopList> shopList = queryShopInfoResponse.getData().getShopList();
        BasePageInfo pageInfo = queryShopInfoResponse.getData().getPageInfo();
        // 获取总页数, 如果总页数大于1, 则遍历获取所有门店信息.
        int totalPage = pageInfo.getPageTotal();
        if (totalPage > 1) {
            // 遍历获取所有门店信息.
            for (int i = 2; i <= totalPage; i++) {
                // 获取门店信息.
                QueryShopInfoResponse queryShopInfoResponse1 = QueryShopInfoAction.queryShopInfo(protocol
                    , applicationServer, applicationPort, accessId, accessToken, i, pageSize);
                Boolean success1 = queryShopInfoResponse1.getSuccess();
                // 如果success为false,则抛出异常.
                if (Boolean.FALSE.equals(success1)) {
                    throw new Exception("获取所有门店信息失败!");
                }
                // 如果success为true,则获取门店信息.
                List<QueryShopInfoDataResponseShopList> shopListItem = queryShopInfoResponse1.getData().getShopList();
                // 将shopListItem添加到shopList中.
                shopList.addAll(shopListItem);
            }
        }

        // 3. 转换为TcShopEntity对象.
        MergeEntity mergeEntity = createTcShopEntity(centerId, shopList);
        List<TcShopEntity> tcShopEntityList = mergeEntity.getTcShopEntityList();
        List<TcShopGroupInfoEntity> tcShopGroupInfoEntityList = mergeEntity.getTcShopGroupInfoEntityList();

        // 4. 落库.
        boolean saveBatchTcShopEntity = iTcShopDao.saveBatch(tcShopEntityList);
        // 如果saveOrUpdateBatch为false, 则抛出异常.
        if (!saveBatchTcShopEntity) {
            throw new Exception("落库失败!");
        }
        boolean saveBatchTcShopGroupInfoEntity = iTcShopGroupInfoDao.saveBatch(tcShopGroupInfoEntityList);
        // 如果saveBatchTcShopGroupInfoEntity为false, 则抛出异常.
        if (!saveBatchTcShopGroupInfoEntity) {
            throw new Exception("落库失败!");
        }

        // 返回参数.
        return BaseVO.builder()
            .id("1")
            .state("success")
            .msg("成功")
            .build();
    }


    /**
     * create tc shop entity
     *
     * @param shopList shop list
     * @return {@link List}<{@link TcShopEntity}>
     */
    private MergeEntity createTcShopEntity(String centerId, List<QueryShopInfoDataResponseShopList> shopList) {

        // 1. 创建返回值
        List<TcShopEntity> resultTcShopEntity = new ArrayList<>();
        List<TcShopGroupInfoEntity> resultTcShopGroupInfoEntityList = new ArrayList<>();

        // 2. 遍历shopList, 创建TcShopEntity对象.
        for (QueryShopInfoDataResponseShopList shopListInfo : shopList) {
            // 初始化shopId
            String shopId = shopListInfo.getShopId();
            String shopInfoEntityId = IdUtil.objectId();
            String id = ObjectUtil.cloneByStream(shopInfoEntityId);

            // 2.1 创建TcShopEntity对象.
            TcShopEntity shopInfoEntity = TcShopEntity.builder()
                // id
                .id(shopInfoEntityId)
                // 集团id
                .centerId(centerId)
                // 天财门店id
                .shopId(shopId)
                // 天财门店编码
                .shopCode(shopListInfo.getShopCode())
                // 天财门店名称
                .shopName(shopListInfo.getShopName())
                // crm门店号
                .crmShopId(Integer.valueOf(shopListInfo.getCrmShopId()))
                // 云端集团号
                .gcId(Integer.valueOf(shopListInfo.getGcId()))
                // 云端门店号
                .mcId(Integer.valueOf(shopListInfo.getMcId()))
                // 区域ID
                .countyId(shopListInfo.getCountyId())
                // 区域名称
                .cityName(shopListInfo.getCityName())
                // 省份ID
                .provinceId(shopListInfo.getProvinceId())
                // 省份名称
                .provinceName(shopListInfo.getProvinceName())
                // 城市ID
                .cityId(shopListInfo.getCityId())
                // 城市名称
                .cityName(shopListInfo.getCityName())
                // 区划ID
                .regionId(shopListInfo.getRegionId())
                // 区划名称
                .regionName(shopListInfo.getRegionName())
                // 管理模式
                .manageTypeName(shopListInfo.getManageTypeId())
                // 品牌id
                .brandId(shopListInfo.getBrandId())
                // 品牌编码
                .brandCode(shopListInfo.getBrandCode())
                // 品牌名称
                .brandName(shopListInfo.getBrandName())
                // 开店时间
                .openTime(shopListInfo.getOpenTime())
                // 地理位置坐标-纬度
                .gcX(new BigDecimal(shopListInfo.getGcX()))
                // 地理位置坐标-经度
                .gcY(new BigDecimal(shopListInfo.getGcY()))
                // 门店地址
                .address(shopListInfo.getAddress())
                // 联系电话
                .contactTel(shopListInfo.getContactTel())
                // 面积
                .area(shopListInfo.getArea())
                // 创建人
                .createBy("system")
                // 创建时间
                .createTime(LocalDateTime.now())
                // 更新人
                .updateBy("system")
                // 更新时间
                .updateTime(LocalDateTime.now())
                .build();

            // 获取门店分组信息.
            List<GroupList> groupList = shopListInfo.getGroupList();
            // 如果shopListInfo.getGroupList()不为空, 则设置门店组信息.
            if (groupList != null && !groupList.isEmpty()) {
                // 遍历groupList, 创建TcShopGroupInfoEntity对象.
                for (GroupList groupListInfo : groupList) {
                    // 2.2 创建TcShopGroupInfoEntity对象.
                    TcShopGroupInfoEntity tcShopGroupInfoEntity = TcShopGroupInfoEntity.builder()
                        // id cachedUidGenerator
                        .id(IdUtil.objectId())
                        // 集团id
                        .centerId(centerId)
                        // 门店id
                        .shopId(shopId)
                        // 门店编码
                        .tcShopId(id)
                        // 一级分组id
                        .oneLevelId(groupListInfo.getOneLevelId())
                        // 一级分组code
                        .oneLevelCode(groupListInfo.getOneLevelCode())
                        // 一级分组名称
                        .oneLevelName(groupListInfo.getOneLevelName())
                        // 二级分组id
                        .twoLevelId(groupListInfo.getTwoLevelId())
                        // 二级分组code
                        .twoLevelCode(groupListInfo.getTwoLevelCode())
                        // 二级分组名称
                        .twoLevelName(groupListInfo.getTwoLevelName())
                        // 三级分组id
                        .threeLevelId(groupListInfo.getThreeLevelId())
                        // 三级分组code
                        .threeLevelCode(groupListInfo.getThreeLevelCode())
                        // 三级分组名称
                        .threeLevelName(groupListInfo.getThreeLevelName())
                        // 四级分组id
                        .fourLevelId(groupListInfo.getFourLevelId())
                        // 四级分组code
                        .fourLevelCode(groupListInfo.getFourLevelCode())
                        // 四级分组名称
                        .fourLevelName(groupListInfo.getFourLevelName())
                        // 创建人
                        .createBy("system")
                        // 创建时间
                        .createTime(LocalDateTime.now())
                        // 更新人
                        .updateBy("system")
                        // 更新时间
                        .updateTime(LocalDateTime.now())
                        .build();

                    // 2.3 新增到返回值中.
                    resultTcShopGroupInfoEntityList.add(tcShopGroupInfoEntity);
                }
            }
            // 2.2 新增到返回值中.
            resultTcShopEntity.add(shopInfoEntity);
        }

        return MergeEntity.builder()
            .tcShopEntityList(resultTcShopEntity)
            .tcShopGroupInfoEntityList(resultTcShopGroupInfoEntityList)
            .build();
    }

    /**
     * merge entity 内部类
     *
     * @author steven
     * &#064;date  2023/12/10
     */
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class MergeEntity {
        List<TcShopEntity> tcShopEntityList;
        // 所有需要保存的子单据的子单据
        List<TcShopGroupInfoEntity> tcShopGroupInfoEntityList;
    }
}
