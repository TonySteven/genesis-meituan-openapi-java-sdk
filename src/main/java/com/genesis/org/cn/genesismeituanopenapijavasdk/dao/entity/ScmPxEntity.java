package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcScmPxResponse;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author LiPei
 * @since 2024-04-07
 */
@Data
@TableName("scm_px")
public class ScmPxEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableField("reportUnit")
    private String reportUnit;

    @TableField("itemCode")
    private String itemCode;

    @TableField("sign")
    private String sign;

    @TableField("itemBigTypeName")
    private String itemBigTypeName;

    @TableField("netRate")
    private String netRate;

    @TableField("spec")
    private String spec;

    @TableField("expFlag")
    private String expFlag;

    @TableField("referPrice")
    private String referPrice;

    @TableField("packFlag")
    private String packFlag;

    @TableField("itemName")
    private String itemName;

    @TableField("storeAgeFlag")
    private String storeAgeFlag;

    @TableField("costType")
    private String costType;

    @TableField("storeMethod")
    private String storeMethod;

    @TableField("reportUnitId")
    private String reportUnitId;

    @TableField("enableFlag")
    private String enableFlag;

    @TableField("createDate")
    private String createDate;

    @TableField("mainUnit")
    private String mainUnit;

    @TableField("assistUnit")
    private String assistUnit;

    @TableField("abc")
    private String abc;

    @TableField("modifyDate")
    private String modifyDate;

    @TableField("financetypeID")
    private String financetypeID;

    @TableField("referCost")
    private String referCost;

    @TableField("financetypeName")
    private String financetypeName;

    @TableField("origin_place")
    private String originPlace;

    @TableField("itemTypeCode")
    private String itemTypeCode;

    @TableField("itemBigTypeCode")
    private String itemBigTypeCode;

    @TableField("reportUnitRatio")
    private String reportUnitRatio;

    @TableField("itemBigTypeID")
    private String itemBigTypeID;

    @TableId("itemId")
    private String itemId;

    @TableField("expDays")
    private String expDays;

    @TableField("mrpExcuteType")
    private String mrpExcuteType;

    @TableField("financetypeCode")
    private String financetypeCode;

    @TableField("itemTypeName")
    private String itemTypeName;

    @TableField("assistUnitFlag")
    private String assistUnitFlag;

    @TableField("itemTypeID")
    private String itemTypeID;

    @TableField("manufacturers")
    private String manufacturers;

    public static List<ScmPxEntity> toEntityListByResponse(List<TcScmPxResponse> responseList){
        return responseList.stream().map(ScmPxEntity::toEntityByResponse).toList();
    }

    public static ScmPxEntity toEntityByResponse(TcScmPxResponse response){
        ScmPxEntity entity = new ScmPxEntity();
        BeanUtils.copyProperties(response, entity);
        return entity;
    }
}
