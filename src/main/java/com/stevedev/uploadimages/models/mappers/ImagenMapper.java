package com.stevedev.uploadimages.models.mappers;

import com.stevedev.uploadimages.models.dto.request.ImagenRequest;
import com.stevedev.uploadimages.models.dto.response.ImagenResponse;
import com.stevedev.uploadimages.models.entities.ImagenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ImagenMapper {
    // Metodo para mapear la lista de imagenes y devolver la respuesta
    @Named("mapImagenes")
    default List<ImagenResponse> mapImagenes(List<ImagenEntity> imagenes) {
        if (imagenes == null) return null;
        return imagenes.stream()
                .map(imagen -> new ImagenResponse(imagen.getId(), imagen.getUrl()))
                .collect(Collectors.toList());
    }

    ImagenResponse toResponse(ImagenEntity entity);

    @Mapping(target = "id", ignore = true)
    ImagenEntity toEntity(ImagenRequest request);
}
