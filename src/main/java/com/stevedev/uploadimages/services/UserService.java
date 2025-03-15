package com.stevedev.uploadimages.services;

import com.stevedev.uploadimages.errors.ResourceNotFoundException;
import com.stevedev.uploadimages.models.dto.response.UserResponse;
import com.stevedev.uploadimages.models.entities.UserEntity;
import com.stevedev.uploadimages.models.mappers.UserMapper;
import com.stevedev.uploadimages.models.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public UserResponse getMyProfile(Authentication authentication) {
        String username = authentication.getName();

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username));

        return userMapper.toResponse(user);
    }
}
