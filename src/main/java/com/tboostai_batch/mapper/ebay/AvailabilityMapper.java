package com.tboostai_batch.mapper.ebay;

import com.tboostai_batch.entity.db_model.AvailabilityEntity;
import com.tboostai_batch.entity.ebay.dto.EbayRespEstimatedAvailabilityDTO;
import com.tboostai_batch.entity.inner_model.Availability;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AvailabilityMapper {

    Availability toAvailability(EbayRespEstimatedAvailabilityDTO ebayRespEstimatedAvailabilityDTO);
    List<Availability> toAvailabilities(List<EbayRespEstimatedAvailabilityDTO> ebayRespEstimatedAvailabilityDTO);

    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "vehicleBasicInfo", ignore = true)
    @Mapping(target = "deliveryOptions", expression = "java(com.tboostai_batch.util.CommonTools.mapListToString(availability.getDeliveryOptions()))")
    AvailabilityEntity toAvailabilityEntity(Availability availability);
    List<AvailabilityEntity> toAvailabilityEntities(List<Availability> availabilities);

    default String mapDeliveryOptions(List<String> deliveryOptions) {
        return deliveryOptions != null ? String.join(",", deliveryOptions) : null;
    }
}
