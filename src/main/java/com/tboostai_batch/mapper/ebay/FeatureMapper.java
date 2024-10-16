package com.tboostai_batch.mapper.ebay;

import com.tboostai_batch.entity.db_model.VehicleFeatureEntity;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FeatureMapper {

    default List<VehicleFeatureEntity> toVehicleFeatures(List<String> extractedFeatures) {
        if (extractedFeatures == null || extractedFeatures.isEmpty()) {
            return new ArrayList<>();
        }

        return extractedFeatures.stream().map(feature -> {
            VehicleFeatureEntity vehicleFeatureEntity = new VehicleFeatureEntity();
            vehicleFeatureEntity.setName(feature);
            return vehicleFeatureEntity;
        }).collect(Collectors.toList());
    }
}
