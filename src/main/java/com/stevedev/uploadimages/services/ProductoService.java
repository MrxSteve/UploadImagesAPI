package com.stevedev.uploadimages.services;

import com.stevedev.uploadimages.errors.ResourceNotFoundException;
import com.stevedev.uploadimages.models.dto.request.CreateProducto;
import com.stevedev.uploadimages.models.dto.request.UpdateProducto;
import com.stevedev.uploadimages.models.dto.response.ProductoResponse;
import com.stevedev.uploadimages.models.entities.CategoriaEntity;
import com.stevedev.uploadimages.models.entities.MarcaEntity;
import com.stevedev.uploadimages.models.entities.ProductoEntity;
import com.stevedev.uploadimages.models.mappers.ProductoMapper;
import com.stevedev.uploadimages.models.repositories.CategoriaRepository;
import com.stevedev.uploadimages.models.repositories.MarcaRepository;
import com.stevedev.uploadimages.models.repositories.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;
    private final CategoriaRepository categoriaRepository;
    private final MarcaRepository marcaRepository;

    @Transactional(readOnly = true)
    public ProductoResponse findById(Long id) {
        ProductoEntity entity = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto con id " + id + " no encontrado"));

        return productoMapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public Page<ProductoResponse> findAll(Pageable pageable) {
        Page<ProductoEntity> entities = productoRepository.findAll(pageable);

        return entities.map(productoMapper::toResponse);
    }

    @Transactional
    public ProductoResponse save(CreateProducto request) {
        ProductoEntity entity = productoMapper.toEntity(request);

        // Buscar y asignar categoria y marca
        CategoriaEntity categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria con id " + request.getCategoriaId() + " no encontrada"));
        entity.setCategoria(categoria);

        MarcaEntity marca = marcaRepository.findById(request.getMarcaId())
                .orElseThrow(() -> new ResourceNotFoundException("Marca con id " + request.getMarcaId() + " no encontrada"));
        entity.setMarca(marca);

        ProductoEntity savedEntity = productoRepository.save(entity);
        return productoMapper.toResponse(savedEntity);
    }

    @Transactional
    public ProductoResponse update(Long id, UpdateProducto request) {
        ProductoEntity entity = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto con id " + id + " no encontrado"));

        if (request.getNombre() != null) {
            entity.setNombre(request.getNombre());
        }
        if (request.getDescripcion() != null) {
            entity.setDescripcion(request.getDescripcion());
        }
        if (request.getPrecio() != null) {
            entity.setPrecio(request.getPrecio());
        }
        if (request.getStock() != null) {
            entity.setStock(request.getStock());
        }
        if (request.getCategoriaId() != null) {
            CategoriaEntity categoria = categoriaRepository.findById(request.getCategoriaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria con id " + request.getCategoriaId() + " no encontrada"));
            entity.setCategoria(categoria);
        }
        if (request.getMarcaId() != null) {
            MarcaEntity marca = marcaRepository.findById(request.getMarcaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Marca con id " + request.getMarcaId() + " no encontrada"));
            entity.setMarca(marca);
        }

        ProductoEntity updatedEntity = productoRepository.save(entity);
        return productoMapper.toResponse(updatedEntity);
    }

    @Transactional
    public void delete(Long id) {
        ProductoEntity entity = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto con id " + id + " no encontrado"));

        productoRepository.delete(entity);
    }

    @Transactional(readOnly = true)
    public Page<ProductoResponse> findByNombre(String nombre, Pageable pageable) {
        Page<ProductoEntity> entities = productoRepository.findByNombreContainingIgnoreCase(nombre, pageable);

        return entities.map(productoMapper::toResponse);
    }
}
