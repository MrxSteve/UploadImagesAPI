package com.stevedev.uploadimages.models.mappers;

import com.stevedev.uploadimages.models.dto.request.CategoriaRequest;
import com.stevedev.uploadimages.models.dto.response.CategoriaResponse;
import com.stevedev.uploadimages.models.entities.CategoriaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    CategoriaResponse toResponse(CategoriaEntity entity);

    @Mapping(target = "id", ignore = true)
    CategoriaEntity toEntity(CategoriaRequest request);
}
