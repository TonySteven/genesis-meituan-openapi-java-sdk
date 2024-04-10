package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcScmGysResponse;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
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
@TableName("scm_gys")
public class ScmGysEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableField("supplierName")
    private String supplierName;

    @TableId("supplierId")
    private String supplierId;

    @TableField("modifyDate")
    private String modifyDate;

    @TableField("accountPeriodType")
    private String accountPeriodType;

    @TableField("supplierTypeID")
    private String supplierTypeID;

    @TableField("supplierState")
    private String supplierState;

    @TableField("supplierCode")
    private String supplierCode;

    @TableField("supplierLinkMan")
    private String supplierLinkMan;

    @TableField("supplierAddr")
    private String supplierAddr;

    @TableField("supplierPhone")
    private String supplierPhone;

    @TableField("supplierRemark")
    private String supplierRemark;

    public static List<ScmGysEntity> toEntityListByResponse(List<TcScmGysResponse> responseList) {
        List<ScmGysEntity> entityList = new ArrayList<>();
        for (TcScmGysResponse response : responseList) {
            entityList.add(toEntityByResponse(response));
        }
        return entityList;
    }


    public static ScmGysEntity toEntityByResponse(TcScmGysResponse response) {
        ScmGysEntity entity = new ScmGysEntity();
        entity.setSupplierName(response.getSupplierName());
        entity.setSupplierId(response.getSupplierId());
        entity.setModifyDate(response.getModifyDate());
        entity.setAccountPeriodType(response.getAccountPeriodType());
        entity.setSupplierTypeID(response.getSupplierTypeID());
        entity.setSupplierState(response.getSupplierState());
        entity.setSupplierCode(response.getSupplierCode());
        entity.setSupplierLinkMan(response.getSupplierLinkMan());
        entity.setSupplierAddr(response.getSupplierAddr());
        entity.setSupplierPhone(response.getSupplierPhone());
        entity.setSupplierRemark(response.getSupplierRemark());
        return entity;
    }
}
