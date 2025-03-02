package com.stevedev.uploadimages.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private String categoria;
    private String marca;
    private List<ImagenResponse> imagenes;
}
