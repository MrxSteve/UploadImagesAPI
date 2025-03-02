package com.stevedev.uploadimages.models.repositories;

import com.stevedev.uploadimages.models.entities.ProductoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {
    Page<ProductoEntity> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}
