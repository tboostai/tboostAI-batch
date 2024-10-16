package com.tboostai_batch.repo;

import com.tboostai_batch.entity.db_model.PaymentInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentInfoRepo extends JpaRepository<PaymentInfoEntity, Long> {
}
