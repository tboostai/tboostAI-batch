package com.tboostai_batch.repo;

import com.tboostai_batch.entity.db_model.AvailabilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailabilityRepo extends JpaRepository<AvailabilityEntity, Long> {
}
