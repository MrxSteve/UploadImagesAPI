package com.stevedev.uploadimages.controllers;

import com.stevedev.uploadimages.models.dto.request.CategoriaRequest;
import com.stevedev.uploadimages.models.dto.response.CategoriaResponse;
import com.stevedev.uploadimages.services.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {
    private final CategoriaService categoriaService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> findById(@PathVariable Long id) {
        CategoriaResponse response = categoriaService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<CategoriaResponse>> findAll(Pageable pageable) {
        Page<CategoriaResponse> response = categoriaService.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> save(@Valid @RequestBody CategoriaRequest request) {
        CategoriaResponse response = categoriaService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponse> update(@PathVariable Long id, @RequestBody CategoriaRequest request) {
        CategoriaResponse response = categoriaService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
