package com.stevedev.uploadimages.models.repositories;

import com.stevedev.uploadimages.models.entities.ImagenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagenRepository extends JpaRepository<ImagenEntity, Long> {
}
