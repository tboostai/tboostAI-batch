package com.tboostai_batch.mapper.ebay;

import com.tboostai_batch.entity.db_model.PostEntity;
import com.tboostai_batch.entity.ebay.dto.EbayRespBasicDTO;
import com.tboostai_batch.entity.inner_model.VehiclePostInfo;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = {
                EbaySellerMapper.class,
                DateMapper.class
        }
)
public interface VehiclePostInfoMapper {
    VehiclePostInfo toVehiclePostInfo(EbayRespBasicDTO ebayRespBasicDTO);

    @Mapping(target = "buyingOptions", expression = "java(String.join(\",\", vehiclePostInfo.getBuyingOptions()))")
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "vehicle", ignore = true)
    @Mapping(source = "itemCreationDate", target = "itemCreationDate", qualifiedByName = "stringToDate")
    @Mapping(source = "itemEndDate", target = "itemEndDate", qualifiedByName = "stringToDate")
    PostEntity toPostEntity(VehiclePostInfo vehiclePostInfo);
}
