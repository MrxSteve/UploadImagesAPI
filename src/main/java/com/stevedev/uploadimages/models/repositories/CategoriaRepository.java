package com.stevedev.uploadimages.models.repositories;

import com.stevedev.uploadimages.models.entities.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {
}
