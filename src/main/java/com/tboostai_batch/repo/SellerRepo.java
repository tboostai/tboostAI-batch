package com.tboostai_batch.repo;

import com.tboostai_batch.entity.db_model.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepo extends JpaRepository<SellerEntity, Long> {
}
