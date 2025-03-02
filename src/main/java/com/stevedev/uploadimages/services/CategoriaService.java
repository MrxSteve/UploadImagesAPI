package com.stevedev.uploadimages.services;

import com.stevedev.uploadimages.errors.ResourceNotFoundException;
import com.stevedev.uploadimages.models.dto.request.CategoriaRequest;
import com.stevedev.uploadimages.models.dto.response.CategoriaResponse;
import com.stevedev.uploadimages.models.entities.CategoriaEntity;
import com.stevedev.uploadimages.models.mappers.CategoriaMapper;
import com.stevedev.uploadimages.models.repositories.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    @Transactional(readOnly = true)
    public CategoriaResponse findById(Long id) {
        CategoriaEntity entity = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria con id " + id + " no encontrada"));

        return categoriaMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public Page<CategoriaResponse> findAll(Pageable pageable) {
        Page<CategoriaEntity> entities = categoriaRepository.findAll(pageable);

        return entities.map(categoriaMapper::toResponse);
    }

    @Transactional
    public CategoriaResponse save(CategoriaRequest request) {
        CategoriaEntity entity = categoriaMapper.toEntity(request);
        CategoriaEntity savedEntity = categoriaRepository.save(entity);

        return categoriaMapper.toResponse(savedEntity);
    }

    @Transactional
    public CategoriaResponse update(Long id, CategoriaRequest request) {
        CategoriaEntity entity = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria con id " + id + " no encontrada"));

        entity.setNombre(request.getNombre());

        CategoriaEntity updatedEntity = categoriaRepository.save(entity);

        return categoriaMapper.toResponse(updatedEntity);
    }

    @Transactional
    public void delete(Long id) {
        CategoriaEntity entity = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria con id " + id + " no encontrada"));

        categoriaRepository.delete(entity);
    }
}
