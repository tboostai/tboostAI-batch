package com.tboostai_batch.mapper.ebay;

import com.tboostai_batch.entity.db_model.VehicleBasicInfoEntity;
import com.tboostai_batch.entity.inner_model.*;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = {
                VehicleImageMapper.class,
                VehiclePriceMapper.class,
                LocationMapper.class,
                FeatureMapper.class,
                EbaySellerMapper.class,
                PaymentInfoMapper.class,
                TaxMapper.class
        }
)
public interface VehicleInfoEntityMapper {

    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "taxInfos", ignore = true)
    @Mapping(target = "locationEntity", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "paymentInfos", ignore = true)
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "availability", ignore = true)
    @Mapping(target = "features", source = "basicInfo.extractedFeatures")
    @Mapping(target = "aiDescription", expression = "java(String.join(\", \", basicInfo.getAiDescription()))")
    VehicleBasicInfoEntity toVehicleBasicInfoEntity(VehicleBasicInfo basicInfo);
}
