package com.tboostai_batch.repo;

import com.tboostai_batch.entity.db_model.VehicleFeatureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleFeatureRepo extends JpaRepository<VehicleFeatureEntity, Long> {
}
