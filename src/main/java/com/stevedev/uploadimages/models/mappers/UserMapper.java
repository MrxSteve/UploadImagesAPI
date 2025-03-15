package com.stevedev.uploadimages.models.mappers;

import com.stevedev.uploadimages.models.dto.response.UserResponse;
import com.stevedev.uploadimages.models.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {
    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRolesToNames")
    UserResponse toResponse(UserEntity entity);
}
