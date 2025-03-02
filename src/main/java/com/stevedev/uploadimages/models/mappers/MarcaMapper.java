package com.stevedev.uploadimages.models.mappers;

import com.stevedev.uploadimages.models.dto.request.MarcaRequest;
import com.stevedev.uploadimages.models.dto.response.MarcaResponse;
import com.stevedev.uploadimages.models.entities.MarcaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MarcaMapper {
    MarcaResponse toResponse(MarcaEntity entity);

    @Mapping(target = "id", ignore = true)
    MarcaEntity toEntity(MarcaRequest request);
}
