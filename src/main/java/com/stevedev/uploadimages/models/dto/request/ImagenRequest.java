package com.stevedev.uploadimages.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagenRequest {
    @NotBlank(message = "La URL es requerida")
    private String url;

    @NotNull(message = "El producto es requerido")
    @Positive(message = "El id del producto debe ser un número positivo y Entero")
    private Long productoId;
}
