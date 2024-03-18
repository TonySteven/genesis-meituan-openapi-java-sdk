package com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response.TcPayTypeResponse;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 结算方式类型
 * @date : 2024-03-15 10:33:44
 */
@Data
@TableName("tc_pay_type")
public class TcPayTypeEntity implements Serializable {

     @Serial
     private static final long serialVersionUID = 1L;

    /** 结算方式类型id */
    @TableId("id")
    private String id;

    /**
     * 集团ID
     */
    @TableField("center_id")
    private String centerId;

    /** 结算方式类型编号 */
    @TableField("code")
    private String code;

    /** 结算方式类型名称 */
    @TableField("name")
    private String name;

    /** 父级结算方式类型id */
    @TableField("pid")
    private String pid;

     /** 集团编号 */
     @TableField("center_code")
     private String centerCode;

     /** 集团名称 */
     @TableField("center_name")
     private String centerName;

    /** 创建时间 */
    @TableField("create_time")
    private LocalDateTime createTime;

    /** 最后修改时间 */
    @TableField("modify_time")
    private LocalDateTime modifyTime;

    public static List<TcPayTypeEntity> toEntityListByResponse(List<TcPayTypeResponse> responseList,String centerId)
    {
        List<TcPayTypeEntity> entityList = new ArrayList<>();
        for(TcPayTypeResponse response:responseList){
            entityList.add(toEntityByResponse(response,centerId));
        }
        return entityList;
    }

    public static TcPayTypeEntity toEntityByResponse(TcPayTypeResponse response,String centerId)
    {
        TcPayTypeEntity entity = new TcPayTypeEntity();
        entity.setCenterId(centerId);
        entity.setId(response.getId());
        entity.setCode(response.getCode());
        entity.setName(response.getName());
        entity.setPid(response.getPid());
        entity.setCenterCode(response.getCenter_code());
        entity.setCenterName(response.getCenter_name());
        entity.setCreateTime(response.getCreate_time());
        entity.setModifyTime(response.getModify_time());
        return entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TcPayTypeEntity that = (TcPayTypeEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(centerId, that.centerId) && Objects.equals(code, that.code) && Objects.equals(name, that.name) && Objects.equals(pid, that.pid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, centerId, code, name, pid);
    }
}
