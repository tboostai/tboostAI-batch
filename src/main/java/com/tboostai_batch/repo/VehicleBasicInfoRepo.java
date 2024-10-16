package com.tboostai_batch.repo;

import com.tboostai_batch.entity.db_model.VehicleBasicInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleBasicInfoRepo extends JpaRepository<VehicleBasicInfoEntity, Long> {
}
