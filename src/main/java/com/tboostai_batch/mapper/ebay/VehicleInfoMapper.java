package com.tboostai_batch.mapper.ebay;

import com.tboostai_batch.entity.ebay.dto.EbayRespBasicDTO;
import com.tboostai_batch.entity.inner_model.VehicleBasicInfo;
import com.tboostai_batch.service.VehicleInfoMappingService;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = {
                VehicleInfoMappingService.class,
                DateMapper.class
        }

)
public interface VehicleInfoMapper {


    @Mapping(target = "make", ignore = true)
    @Mapping(target = "model", ignore = true)
    @Mapping(target = "year", ignore = true)
    @Mapping(target = "trim", ignore = true)
    @Mapping(target = "vin", ignore = true)
    @Mapping(target = "mileage", ignore = true)
    @Mapping(target = "exteriorColor", ignore = true)
    @Mapping(target = "interiorColor", ignore = true)
    @Mapping(target = "bodyType", ignore = true)
    @Mapping(target = "engineType", ignore = true)
    @Mapping(target = "engineSize", ignore = true)
    @Mapping(target = "cylinder", ignore = true)
    @Mapping(target = "transmission", ignore = true)
    @Mapping(target = "drivetrain", ignore = true)
    @Mapping(target = "aiDescription", ignore = true)
    @Mapping(target = "extractedFeatures", ignore = true)
    @Mapping(target = "capacity", ignore = true)
    @Mapping(target = "engineInfo", ignore = true)
    @Mapping(target = "cylinderInfo", ignore = true)
    @Mapping(target = "warranty", ignore = true)
    @Mapping(target = "vehicleTitle", ignore = true)
    @Mapping(target = "doors", ignore = true)
    @Mapping(source = "itemCreationDate", target = "listingDate", qualifiedByName = "isoToTimestamp")
    @Mapping(target = "sourceId", expression = "java(com.tboostai_batch.common.VehicleSource.EBAY.getId())")
    VehicleBasicInfo toVehicleBasicInfo(EbayRespBasicDTO ebayRespBasicDTO);

    @AfterMapping
    default void mapLocalizedAspects(@MappingTarget VehicleBasicInfo vehicleBasicInfo, EbayRespBasicDTO ebayRespBasicDTO, VehicleInfoMappingService mappingService) {
        mappingService.mapLocalizedAspects(vehicleBasicInfo, ebayRespBasicDTO);
    }
}


