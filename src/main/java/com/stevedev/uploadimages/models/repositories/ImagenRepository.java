package com.stevedev.uploadimages.models.repositories;

import com.stevedev.uploadimages.models.entities.ImagenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagenRepository extends JpaRepository<ImagenEntity, Long> {
    List<ImagenEntity> findByProducto_Id(Long productoId);
}
