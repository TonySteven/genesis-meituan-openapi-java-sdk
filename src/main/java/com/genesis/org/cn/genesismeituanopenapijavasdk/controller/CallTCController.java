package com.genesis.org.cn.genesismeituanopenapijavasdk.controller;

import com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.base.BaseVO;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor.TcItemCategoryQueryAndSaveCmdExe;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor.TcItemMethodClassesQueryAndSaveCmdExe;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor.TcItemMethodsQueryAndSaveCmdExe;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor.TcItemQueryAndSaveCmdExe;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor.TcPayTypeQueryAndSaveCmdExe;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor.TcPaywayDetailQueryAndSaveCmdExe;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor.TcRecipeCardQueryAndSaveCmdExe;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor.TcShopBillingDetailInRealTimeQueryAndSaveCmdExe;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor.TcShopBillingDetailQueryAndSaveCmdExe;
import com.genesis.org.cn.genesismeituanopenapijavasdk.service.executor.TcShopInfoQueryAndSaveCmdExe;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.request.TcItemQueryCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.request.TcRecipeCardQueryCmd;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.request.TcShopBillingDetailQueryCmd;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.oas.annotations.EnableOpenApi;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

/**
 * 天财SaaS 相关接口
 *
 * @author steven
 * &#064;date  2023/10/24
 */
@EnableOpenApi
@Api(tags = "天财SaaS 相关接口")
@RestController
@RequestMapping("/tc/api")
public class CallTCController {

    @Resource
    TcShopInfoQueryAndSaveCmdExe tcShopInfoQueryAndSaveCmdExe;

    @Resource
    TcShopBillingDetailQueryAndSaveCmdExe tcShopBillingDetailQueryAndSaveCmdExe;

    @Resource
    TcShopBillingDetailInRealTimeQueryAndSaveCmdExe tcShopBillingDetailInRealTimeQueryAndSaveCmdExe;

    @Resource
    private TcItemCategoryQueryAndSaveCmdExe tcItemCategoryQueryAndSaveCmdExe;

    @Resource
    private TcItemQueryAndSaveCmdExe tcItemQueryAndSaveCmdExe;

    @Resource
    private TcItemMethodClassesQueryAndSaveCmdExe tcItemMethodClassesQueryAndSaveCmdExe;

    @Resource
    private TcItemMethodsQueryAndSaveCmdExe tcItemMethodsQueryAndSaveCmdExe;

    @Resource
    TcPayTypeQueryAndSaveCmdExe tcPayTypeQueryAndSaveCmdExe;

    @Resource
    TcPaywayDetailQueryAndSaveCmdExe tcPaywayDetailQueryAndSaveCmdExe;

    @Resource
    private TcRecipeCardQueryAndSaveCmdExe tcRecipeCardQueryAndSaveCmdExe;


    /**
     * 天财SaaS-调用查询所有门店详情并落库api
     *
     * @return {@link BaseVO}
     */
    @ApiOperation(value = "天财SaaS-调用查询所有门店详情并落库api", notes = "天财SaaS-调用查询所有门店详情并落库api")
    @GetMapping("/save-shop-info")
    public BaseVO saveShopInfo() {
        // 在执行器里面执行具体的业务逻辑.
        return tcShopInfoQueryAndSaveCmdExe.execute();
    }

    /**
     * 天财SaaS-调用获取账单明细实时并落库api
     *
     * @return {@link BaseVO}
     */
    @ApiOperation(value = "天财SaaS-调用获取账单明细实时并落库api", notes = "天财SaaS-调用获取账单明细实时并落库api")
    @GetMapping("/save-billing-details-in-real-time")
    public BaseVO saveBillingDetailsInRealTime() {
        // 在执行器里面执行具体的业务逻辑.
        return tcShopBillingDetailInRealTimeQueryAndSaveCmdExe.execute();
    }

    /**
     * 天财SaaS-调用获取账单明细实时并落库api
     *
     * @return {@link BaseVO}
     */
    @ApiOperation(value = "天财SaaS-调用获取账单明细落库api", notes = "天财SaaS-调用获取账单明细实时并落库api")
    @GetMapping("/save-billing-details")
    public BaseVO saveBillingDetails(@RequestBody @Validated TcShopBillingDetailQueryCmd cmd) {
        // 异步执行 tcShopBillingDetailQueryAndSaveCmdExe.execute(cmd)
        CompletableFuture.runAsync(() -> tcShopBillingDetailQueryAndSaveCmdExe.execute(cmd));

        // 返回成功
        return BaseVO.builder()
            .id("1")
            .state("success")
            .msg("异步执行中")
            .build();
    }


    /**
     * 天财SaaS-调用获取品项类别信息落库api
     */
    @ApiOperation(value = "天财SaaS-调用获取品项类别信息落库api", notes = "天财SaaS-调用获取品项类别信息落库api")
    @GetMapping("/save-item-category")
    public void saveItemCategory(){
        tcItemCategoryQueryAndSaveCmdExe.execute();
    }


    /**
     * 天财SaaS-调用获取品项信息落库api
     */
    @ApiOperation(value = "天财SaaS-调用获取品项信息落库api", notes = "天财SaaS-调用获取品项信息落库api")
    @GetMapping("/save-item")
    public void saveItem(@RequestBody @Validated TcItemQueryCmd cmd){
        tcItemQueryAndSaveCmdExe.execute(cmd);
    }

    /**
     * 天财SaaS-调用获取品项做法类别信息落库api
     */
    @ApiOperation(value = "天财SaaS-调用获取品项做法类别信息落库api", notes = "天财SaaS-调用获取品项做法类别信息落库api")
    @GetMapping("/save-item-method-classes")
    public void saveItemMethodClasses(){
        tcItemMethodClassesQueryAndSaveCmdExe.execute();
    }

    /**
     * 天财SaaS-调用获取品项做法信息落库api
     */
    @ApiOperation(value = "天财SaaS-调用获取品项做法信息落库api", notes = "天财SaaS-调用获取品项做法信息落库api")
    @GetMapping("/save-item-methods")
    public void saveItemMethods(){
        tcItemMethodsQueryAndSaveCmdExe.execute();
    }


    /**
     * 天财SaaS-调用获取结算方式类型落库api
     */
    @ApiOperation(value = "天财SaaS-调用获取结算方式类型落库api", notes = "天财SaaS-调用获取结算方式类型落库api")
    @GetMapping("/save-pay-type")
    public void savePayType(){
        tcPayTypeQueryAndSaveCmdExe.execute();
    }

    /**
     * 同步保存结算方式详情
     */
    @ApiOperation(value = "天财SaaS-调用获取结算方式详情落库api", notes = "天财SaaS-调用获取结算方式详情落库api")
    @GetMapping("/save-payway-detail")
    public void savePaywayDetail(){
        tcPaywayDetailQueryAndSaveCmdExe.execute();
    }

    /**
     * 获取菜品成本卡详细信息
     */
    @ApiOperation(value = "天财SaaS-调用获取菜品成本卡详细信息api", notes = "天财SaaS-调用获取菜品成本卡详细信息api")
    @GetMapping("/get-recipe-card")
    public void getRecipeCard(@RequestBody @Validated TcRecipeCardQueryCmd cmd){
        tcRecipeCardQueryAndSaveCmdExe.execute(cmd);
    }
}
