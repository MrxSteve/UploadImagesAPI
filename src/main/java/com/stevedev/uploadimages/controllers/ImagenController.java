package com.stevedev.uploadimages.controllers;

import com.stevedev.uploadimages.services.ImagenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/imagenes")
@RequiredArgsConstructor
public class ImagenController {
    private final ImagenService imagenService;

    @PostMapping("{productoId}")
    public ResponseEntity<String> subirImagen(@PathVariable Long productoId, @RequestParam("file") MultipartFile file) {
        String imageUrl = imagenService.subirImagen(productoId, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(imageUrl);
    }

    @DeleteMapping("/{imagenId}")
    public ResponseEntity<Void> eliminarImagen(@PathVariable Long imagenId) {
        imagenService.eliminarImagen(imagenId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/producto/{productoId}/imagen/{imagenId}")
    public ResponseEntity<Void> eliminarImagenDeProducto(@PathVariable Long productoId, @PathVariable Long imagenId) {
        imagenService.eliminarImagenDeProducto(productoId, imagenId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/producto/{productoId}")
    public ResponseEntity<Void> eliminarTodasLasImagenesDeProducto(@PathVariable Long productoId) {
        imagenService.eliminarTodasLasImagenesDeProducto(productoId);
        return ResponseEntity.noContent().build();
    }
}
