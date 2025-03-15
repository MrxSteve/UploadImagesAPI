package com.stevedev.uploadimages.models.mappers;

import com.stevedev.uploadimages.models.entities.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    // Metodo para mapear roles con sus nombres
    @Named("mapRolesToNames")
    default List<String> mapRolesToNames(List<RoleEntity> roles) {
        return roles.stream()
                .map(RoleEntity::getNombre)
                .collect(Collectors.toList());
    }
}
