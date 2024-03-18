package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;
import lombok.Data;


 /**
 * 品项通用做法表
 * @date : 2024-03-16 13:43:08
 */
@Data
public class TcItemMethodResponse{

     /**
      * 集团编码
      */
     private String center_code;


     /**
      * 品项id
      */
     private String item_id;


     /**
      * 做法id
      */
     private String id;


     /**
      * 做法编号
      */
     private String code;


     /**
      * 做法名称
      */
     private String name;

}
