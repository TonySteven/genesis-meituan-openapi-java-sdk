package com.genesis.org.cn.genesismeituanopenapijavasdk.model.api.request;

import lombok.Data;

/**
 * 金蝶生成应付单入参
 *
 * @author steven
 * &#064;date  2023/10/29
 */
@Data
public class FsubHeadSuppiler {

    private int FEntryId;

    private BaseFNumber FORDERID;

    private BaseFNumber FTRANSFERID;

    private BaseFNumber FChargeId;

}
