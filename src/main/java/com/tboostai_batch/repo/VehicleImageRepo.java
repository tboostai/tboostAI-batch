package com.tboostai_batch.repo;

import com.tboostai_batch.entity.db_model.VehicleImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleImageRepo extends JpaRepository<VehicleImageEntity, Long> {
}
