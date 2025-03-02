package com.stevedev.uploadimages.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProducto {
    @NotBlank(message = "El nombre es requerido")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción es requerida")
    @Size(min = 3, max = 255, message = "La descripción debe tener entre 3 y 255 caracteres")
    private String descripcion;

    @NotNull(message = "El precio es requerido")
    @Positive(message = "El precio debe ser mayor a 0")
    private Double precio;

    @NotNull(message = "El stock es requerido")
    @Positive(message = "El stock debe ser un numero entero mayor a 0")
    private Integer stock;

    @NotNull(message = "La marca es requerida")
    @Positive(message = "El id de la marca debe ser un numero entero mayor a 0")
    private Long marcaId;

    @NotNull(message = "La categoría es requerida")
    @Positive(message = "El id de la categoría debe ser un numero entero mayor a 0")
    private Long categoriaId;
}
