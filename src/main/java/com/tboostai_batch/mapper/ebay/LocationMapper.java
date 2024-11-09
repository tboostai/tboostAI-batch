package com.tboostai_batch.mapper.ebay;

import com.tboostai_batch.entity.db_model.LocationEntity;
import com.tboostai_batch.entity.ebay.dto.EbayRespLocationDTO;
import com.tboostai_batch.entity.inner_model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    @Mapping(target = "stateProvince", source = "stateOrProvince")
    @Mapping(target = "latitude", ignore = true)
    @Mapping(target = "longitude", ignore = true)
    @Mapping(target = "street", ignore = true)
    @Mapping(target = "unit", ignore = true)
    Location toLocation(EbayRespLocationDTO ebayRespLocationDTO);

    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "vehicles", ignore = true)
    LocationEntity toLocationEntity(Location location);
}
