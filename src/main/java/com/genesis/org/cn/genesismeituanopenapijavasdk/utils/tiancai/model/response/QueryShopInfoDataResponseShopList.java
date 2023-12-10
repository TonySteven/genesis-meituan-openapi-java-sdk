package com.genesis.org.cn.genesismeituanopenapijavasdk.utils.tiancai.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * query shop info data response shop list
 *
 * @author steven
 * &#064;date  2023/12/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryShopInfoDataResponseShopList {

    /**
     * manage type name
     */
    private String manageTypeName;

    /**
     * brand code
     */
    private String brandCode;

    /**
     * shop code
     */
    private String shopCode;

    /**
     * crm company id
     */
    private String crmCompanyId;

    /**
     * crm shop id
     */
    private String crmShopId;

    /**
     * gc id
     */
    private String gcId;

    /**
     * contact tel
     */
    private String contactTel;

    /**
     * open time
     */
    private String openTime;

    /**
     * remark
     */
    private String remark;

    /**
     * mc id
     */
    private String mcId;

    /**
     * coop center id
     */
    private String coopCenterId;

    /**
     * county id
     */
    private String countyId;

    /**
     * city name
     */
    private String cityName;

    /**
     * tax number
     */
    private String taxNumber;

    /**
     * region name
     */
    private String regionName;

    /**
     * address
     */
    private String address;

    /**
     * gcx
     */
    private String gcX;

    /**
     * gcy
     */
    private String gcY;

    /**
     * sly code
     */
    private String slyCode;

    /**
     * region id
     */
    private String regionId;

    /**
     * brand name
     */
    private String brandName;

    /**
     * shop name
     */
    private String shopName;

    /**
     * county name
     */
    private String countyName;

    /**
     * province name
     */
    private String provinceName;

    /**
     * brand id
     */
    private String brandId;

    /**
     * shop id
     */
    private String shopId;

    /**
     * province id
     */
    private String provinceId;

    /**
     * manage type id
     */
    private String manageTypeId;

    /**
     * city id
     */
    private String cityId;

    /**
     * area
     */
    private String area;


    /**
     * group list
     */
    private List<GroupList> groupList;

}
