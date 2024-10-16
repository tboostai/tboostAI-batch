package com.tboostai_batch.repo;

import com.tboostai_batch.entity.db_model.TaxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxRepo extends JpaRepository<TaxEntity, Long> {
}
