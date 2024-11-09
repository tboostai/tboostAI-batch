package com.tboostai_batch.mapper.ebay;

import com.tboostai_batch.entity.db_model.EbayAdditionalInfoEntity;
import com.tboostai_batch.entity.ebay.dto.EbayRespBasicDTO;
import com.tboostai_batch.entity.ebay.dto.EbayRespShipToLocationsDTO;
import com.tboostai_batch.entity.inner_model.EbayAdditionalInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = VehiclePriceMapper.class
)
public interface EbayAdditionalInfoMapper {

    @Mapping(target = "itemId", source = "ebayRespBasicDTO.itemId")
    @Mapping(target = "sellerItemRevision", source = "ebayRespBasicDTO.sellerItemRevision")
    @Mapping(target = "categoryPath", source = "ebayRespBasicDTO.categoryPath")
    @Mapping(target = "categoryIdPath", source = "ebayRespBasicDTO.categoryIdPath")
    @Mapping(target = "condition", source = "ebayRespBasicDTO.condition")
    @Mapping(target = "conditionId", source = "ebayRespBasicDTO.conditionId")
    @Mapping(target = "shipRegionIncluded", source = "ebayRespShipToLocationsDTO.regionIncluded")
    @Mapping(target = "shipRegionExcluded", source = "ebayRespShipToLocationsDTO.regionExcluded")
    @Mapping(target = "topRatedBuyingExperience", source = "ebayRespBasicDTO.topRatedBuyingExperience")
    @Mapping(target = "lotSize", source = "ebayRespBasicDTO.lotSize")
    @Mapping(target = "priorityListing", source = "ebayRespBasicDTO.priorityListing")
    @Mapping(target = "adultOnly", source = "ebayRespBasicDTO.adultOnly")
    @Mapping(target = "categoryId", source = "ebayRespBasicDTO.categoryId")
    @Mapping(target = "listingMarketplaceId", source = "ebayRespBasicDTO.listingMarketplaceId")
    EbayAdditionalInfo toEbayAdditionalInfo(EbayRespBasicDTO ebayRespBasicDTO,
                                            EbayRespShipToLocationsDTO ebayRespShipToLocationsDTO);

    @Mapping(target = "additionalInfo", expression = "java(com.tboostai_batch.util.CommonTools.objToJsonStr(dto))")
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    EbayAdditionalInfoEntity toEbayAdditionalInfoEntity(EbayAdditionalInfo dto);
}
