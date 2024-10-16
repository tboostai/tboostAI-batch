package com.tboostai_batch.repo;

import com.tboostai_batch.entity.db_model.EbayAdditionalInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EbayAdditionalInfoRepo extends JpaRepository<EbayAdditionalInfoEntity, Long> {
}
