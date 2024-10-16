package com.tboostai_batch.mapper.ebay;

import com.tboostai_batch.entity.db_model.VehicleImageEntity;
import com.tboostai_batch.entity.ebay.dto.EbayRespImageDTO;
import com.tboostai_batch.entity.inner_model.VehicleImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleImageMapper {

    @Mapping(target = "url", source = "imageUrl")
    VehicleImage toVehicleImage(EbayRespImageDTO ebayRespImageDTO);

    List<VehicleImage> toVehicleImageList(List<EbayRespImageDTO> ebayRespImageDTOList);

    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "vehicle", ignore = true)
    VehicleImageEntity toVehicleImageEntity(VehicleImage vehicleImage);

    List<VehicleImageEntity> toVehicleImageEntityList(List<VehicleImage> vehicleImages);
}
