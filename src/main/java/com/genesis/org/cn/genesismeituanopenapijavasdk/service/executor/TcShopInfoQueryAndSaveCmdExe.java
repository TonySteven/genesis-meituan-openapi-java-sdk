package com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor;

import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcShopDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcShopGroupInfoDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcShopEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.base.BaseVO;
import com.sankuai.meituan.waimai.opensdk.vo.PoiParam;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
     * 天财AppId
     */
    @Value("${tiancai.api.appId}")
    private String appId;

    /**
     * 天财accessId
     */
    @Value("${tiancai.api.accessId}")
    private String accessId;

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
        // 1. 根据天财AppId和accessId进行鉴权.

        // 2. 调用天财接口获取所有门店信息.

        // 3. 转换为TcShopEntity对象.

        // 4. 落库.


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
     * @param poiParamList poi param list
     * @return {@link List}<{@link TcShopEntity}>
     */
    private List<TcShopEntity> createTcShopEntity(List<PoiParam> poiParamList) {

        // 1. 创建返回值
        List<TcShopEntity> result = new ArrayList<>();

        // 2. 遍历poiParamList,转换为TcShopEntity对象.
        for (PoiParam poiParam : poiParamList) {
            // 2.1 创建TcShopEntity对象.
            TcShopEntity shopInfoEntity = TcShopEntity.builder()
                // id
                .id(poiParam.getApp_poi_code())
                // 天财门店id
                .shopId(poiParam.getApp_poi_code())
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
