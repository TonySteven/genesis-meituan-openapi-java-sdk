package com.genesis.org.cn.genesismeituanopenapijavasdk.service.assembler;

import com.genesis.org.cn.genesismeituanopenapijavasdk.dao.entity.MtShopInfoEntity;
import com.sankuai.meituan.waimai.opensdk.vo.PoiParam;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;


/**
 * 采购发票账单Entity转VOForm类
 *
 * @author steven
 * &#064;date  2023/01/11
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MtShopInfoEntityAssembler {
    MtShopInfoEntityAssembler INSTANCE = Mappers.getMapper(MtShopInfoEntityAssembler.class);

    /**
     * to
     *
     * @param poiParam poi param
     * @return {@link MtShopInfoEntity}
     */
    MtShopInfoEntity to(PoiParam poiParam);
}
