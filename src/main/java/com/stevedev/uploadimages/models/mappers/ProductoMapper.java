package com.stevedev.uploadimages.models.mappers;

import com.stevedev.uploadimages.models.dto.request.CreateProducto;
import com.stevedev.uploadimages.models.dto.response.ProductoResponse;
import com.stevedev.uploadimages.models.entities.ProductoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ImagenMapper.class})
public interface ProductoMapper {
    @Mapping(source = "categoria.nombre", target = "categoria")
    @Mapping(source = "marca.nombre", target = "marca")
    @Mapping(source = "imagenes", target = "imagenes", qualifiedByName = "mapImagenes")
    ProductoResponse toResponse(ProductoEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "marca", ignore = true)
    @Mapping(target = "imagenes", ignore = true)
    ProductoEntity toEntity(CreateProducto request);
}
