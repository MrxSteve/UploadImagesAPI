package com.stevedev.uploadimages.controllers;

import com.stevedev.uploadimages.models.dto.request.CreateProducto;
import com.stevedev.uploadimages.models.dto.request.UpdateProducto;
import com.stevedev.uploadimages.models.dto.response.ProductoResponse;
import com.stevedev.uploadimages.services.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService productoService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> findById(@PathVariable Long id) {
        ProductoResponse response = productoService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<ProductoResponse>> findAll(Pageable pageable) {
        Page<ProductoResponse> response = productoService.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductoResponse> save(@Valid @RequestBody CreateProducto request) {
        ProductoResponse response = productoService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> update(@PathVariable Long id, @RequestBody UpdateProducto request) {
        ProductoResponse response = productoService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nombre")
    public ResponseEntity<Page<ProductoResponse>> findByNombre(@RequestParam String nombre, Pageable pageable) {
        Page<ProductoResponse> response = productoService.findByNombre(nombre, pageable);
        return ResponseEntity.ok(response);
    }
}
