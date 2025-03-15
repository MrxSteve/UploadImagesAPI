package com.stevedev.uploadimages.models.repositories;

import com.stevedev.uploadimages.models.entities.LoginAuditEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoginAuditRepository extends MongoRepository<LoginAuditEntity, String> {
    Page<LoginAuditEntity> findByEmail(String email, Pageable pageable);
    Page<LoginAuditEntity> findByUsername(String username, Pageable pageable);
}
