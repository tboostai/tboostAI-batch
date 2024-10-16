package com.tboostai_batch.repo;

import com.tboostai_batch.entity.db_model.VehiclePriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiclePriceRepo extends JpaRepository<VehiclePriceEntity, Long> {
}
