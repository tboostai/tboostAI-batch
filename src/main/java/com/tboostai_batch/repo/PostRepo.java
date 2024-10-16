package com.tboostai_batch.repo;

import com.tboostai_batch.entity.db_model.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<PostEntity, Long> {
}
