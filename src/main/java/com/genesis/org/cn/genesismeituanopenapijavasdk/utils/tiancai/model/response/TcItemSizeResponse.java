package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;
import lombok.Data;

import java.math.BigDecimal;

 /**
 * 品项规格表
 * @date : 2024-03-16 13:46:16
 */
@Data
public class TcItemSizeResponse{


     /**
      * 所属集团代码
      */
     private String center_code;


     /**
      * 所属品项id
      */
     private String item_id;


     /**
      * 规格id
      */
     private String size_id;


     /**
      * 规格编码
      */
     private String code;


     /**
      * 规格名称
      */
     private String name;


     /**
      * 是否为默认规格
      */
     private Boolean is_default;


     /**
      * 标准价格
      */
     private BigDecimal std_price;


     /**
      * 成本价格
      */
     private BigDecimal cost_price;


     /**
      * 删除标识  0表示正常（默认），1表示在回收站，2表示从回收站删除（不可恢复）
      */
     private Integer delflg;

}
