package com.stevedev.uploadimages.services;

import com.stevedev.uploadimages.errors.ResourceNotFoundException;
import com.stevedev.uploadimages.models.dto.request.MarcaRequest;
import com.stevedev.uploadimages.models.dto.response.MarcaResponse;
import com.stevedev.uploadimages.models.entities.MarcaEntity;
import com.stevedev.uploadimages.models.mappers.MarcaMapper;
import com.stevedev.uploadimages.models.repositories.MarcaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MarcaService {
    private final MarcaRepository marcaRepository;
    private final MarcaMapper marcaMapper;

    @Transactional(readOnly = true)
    public MarcaResponse findById(Long id) {
        MarcaEntity marca = marcaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marca con id " + id + " no encontrada"));

        return marcaMapper.toResponse(marca);
    }

    @Transactional(readOnly = true)
    public Page<MarcaResponse> findAll(Pageable pageable) {
        Page<MarcaEntity> marcas = marcaRepository.findAll(pageable);

        return marcas.map(marcaMapper::toResponse);
    }

    @Transactional
    public MarcaResponse save(MarcaRequest request) {
        MarcaEntity marca = marcaMapper.toEntity(request);
        MarcaEntity savedMarca = marcaRepository.save(marca);

        return marcaMapper.toResponse(savedMarca);
    }

    @Transactional
    public MarcaResponse update(Long id, MarcaRequest request) {
        MarcaEntity marca = marcaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marca con id " + id + " no encontrada"));

        marca.setNombre(request.getNombre());

        MarcaEntity updatedMarca = marcaRepository.save(marca);

        return marcaMapper.toResponse(updatedMarca);
    }

    @Transactional
    public void delete(Long id) {
        MarcaEntity marca = marcaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marca con id " + id + " no encontrada"));

        marcaRepository.delete(marca);
    }
}
