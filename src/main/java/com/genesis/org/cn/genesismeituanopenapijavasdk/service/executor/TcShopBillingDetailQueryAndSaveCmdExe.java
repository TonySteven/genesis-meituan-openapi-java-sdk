package com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor;

import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcShopBillingDetailDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.api.ITcShopBillingDetailItemDao;
import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.TcShopBillingDetailEntity;
import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.base.BaseVO;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.LoginToServerAction;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.enums.ResponseStatusEnum;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.LoginResult;
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
 * tc shop billing detail query and save cmd exe
 * 天财SaaS-调用获取账单明细实时并落库api具体逻辑.
 *
 * @author steven
 * &#064;date  2023/12/10
 */
@Service
@Slf4j
public class TcShopBillingDetailQueryAndSaveCmdExe {

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
    private ITcShopBillingDetailDao iTcShopBillingDetailDao;

    @Resource
    private ITcShopBillingDetailItemDao iTcShopBillingDetailItemDao;

    /**
     * execute
     * 执行
     *
     * @return {@link BaseVO}
     */
    @SneakyThrows
    public BaseVO execute() {
        // 打印日志 - 开始.
        log.info("TcShopBillingDetailQueryAndSaveCmdExe.execute() - start");
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

        // 2. 调用天财接口获取所有账单明细实时信息.
        // 初始化分页参数.
        Integer pageNo = 1;
        Integer pageSize = 50;


        // 3. 转换为TcShopBillingDetailEntity对象.

        // 4. 落库.


        // 返回参数.
        return BaseVO.builder()
            .id("1")
            .state("success")
            .msg("成功")
            .build();
    }


    /**
     * create tc shop billing detail entity
     *
     * @param poiParamList poi param list
     * @return {@link List}<{@link TcShopBillingDetailEntity}>
     */
    private List<TcShopBillingDetailEntity> createTcShopBillingDetailEntity(List<PoiParam> poiParamList) {

        // 1. 创建返回值
        List<TcShopBillingDetailEntity> result = new ArrayList<>();

        // 2. 遍历poiParamList,转换为TcShopBillingDetailEntity对象.
        for (PoiParam poiParam : poiParamList) {
            // 2.1 创建TcShopBillingDetailEntity对象.
            TcShopBillingDetailEntity shopInfoEntity = TcShopBillingDetailEntity.builder()
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
