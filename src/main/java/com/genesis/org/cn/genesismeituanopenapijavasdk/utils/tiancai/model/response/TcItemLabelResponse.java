package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;
import lombok.Data;


 /**
 * 品项标签表
 * @date : 2024-03-16 13:41:45
 */
@Data
public class TcItemLabelResponse{


     /**
      * 集团编码
      */
     private String center_code;


     /**
      * 品项id
      */
     private String item_id;


     /**
      * 标签id
      */
     private String label_id;


     /**
      * 标签名称
      */
     private String label_name;

}
