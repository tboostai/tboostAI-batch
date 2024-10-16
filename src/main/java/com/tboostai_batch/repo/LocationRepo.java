package com.tboostai_batch.repo;

import com.tboostai_batch.entity.db_model.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepo extends JpaRepository<LocationEntity, Long> {
}
