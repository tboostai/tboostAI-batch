package com.tboostai_batch.repo;

import com.tboostai_batch.entity.db_model.SellerEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SellerRepo extends JpaRepository<SellerEntity, Long> {
    @Query("SELECT s FROM SellerEntity s WHERE s.platform = :platform AND s.username IN :usernames")
    List<SellerEntity> findByPlatformAndUsernames(@Param("platform") String platform, @Param("usernames") List<String> usernames);
}
