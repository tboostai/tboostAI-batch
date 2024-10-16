package com.tboostai_batch.mapper.ebay;

import com.tboostai_batch.entity.db_model.VehiclePriceEntity;
import com.tboostai_batch.entity.ebay.dto.EbayRespPriceDTO;
import com.tboostai_batch.entity.inner_model.VehiclePrice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehiclePriceMapper {

    @Mapping(target = "price", source = "value")
    @Mapping(target = "priceType", ignore = true)
    VehiclePrice toVehiclePrice(EbayRespPriceDTO ebayRespPriceDTO);

    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "vehicle", ignore = true)
    VehiclePriceEntity toVehiclePriceEntity(VehiclePrice vehiclePrice);

    List<VehiclePriceEntity> toVehiclePriceEntities(List<VehiclePrice> vehiclePrices);
}
