package com.stevedev.uploadimages.utils;

import com.stevedev.uploadimages.models.entities.RoleEntity;
import com.stevedev.uploadimages.models.entities.UserEntity;
import com.stevedev.uploadimages.models.repositories.RoleRepository;
import com.stevedev.uploadimages.models.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminInitializer {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String adminEmail;
    @Value("${admin.password}")
    private String password;
    @Value("${admin.username}")
    private String username;

    @PostConstruct
    public void initAdminUser() {
        // Verificar si el usuario ADMIN ya existe
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            // Buscar el rol ADMIN
            RoleEntity adminRole = roleRepository.findByNombre("ADMIN")
                    .orElseThrow(() -> new RuntimeException("Rol ADMIN no encontrado"));

            // Crear usuario ADMIN
            UserEntity adminUser = UserEntity.builder()
                    .username(username)
                    .email(adminEmail)
                    .password(passwordEncoder.encode(password)) // Contraseña encriptada
                    .roles(List.of(adminRole)) // Asignar el rol ADMIN
                    .build();

            userRepository.save(adminUser);
            System.out.println(" Usuario ADMIN creado");
        }
    }
}
