package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;
import lombok.Data;


 /**
 * 品项多条码表
 * @date : 2024-03-16 13:43:45
 */
@Data
public class TcItemMultiBarcodeResponse{


     /**
      * 集团编码
      */
     private String center_code;


     /**
      * 品项id
      */
     private String item_id;


     /**
      * 规格id
      */
     private String size_id;


     /**
      * 规格名称
      */
     private String size_name;


     /**
      * 条码
      */
     private String barcode;


     /**
      * 删除标识
      */
     private Integer delflg;

}
