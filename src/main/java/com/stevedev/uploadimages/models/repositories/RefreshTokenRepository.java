package com.stevedev.uploadimages.models.repositories;

import com.stevedev.uploadimages.models.entities.RefreshTokenEntity;
import com.stevedev.uploadimages.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);
    void deleteByUser(UserEntity user);
}
