package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

/**
* 结算方式类型
* @date : 2024-03-15 10:33:44
*/
@Data
public class TcPayTypeResponse {

   /** 结算方式类型id */
   private String id;

   /** 结算方式类型编号 */
   private String code;

   /** 结算方式类型名称 */
   private String name;

   /** 父级结算方式类型id */
   private String pid;

    /** 集团编号 */
    private String center_code;

    /** 集团名称 */
    private String center_name;

   /** 创建时间 */
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   private LocalDateTime create_time;

   /** 最后修改时间 */
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   private LocalDateTime modify_time;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TcPayTypeResponse that = (TcPayTypeResponse) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
