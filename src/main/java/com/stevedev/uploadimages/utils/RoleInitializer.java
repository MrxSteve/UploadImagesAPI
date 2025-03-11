package com.stevedev.uploadimages.utils;

import com.stevedev.uploadimages.models.entities.RoleEntity;
import com.stevedev.uploadimages.models.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleInitializer {
    private final RoleRepository roleRepository;

    @PostConstruct
    public void initRoles() {
        List<String> roleNames = Arrays.asList("USER", "ADMIN");

        for (String roleName: roleNames) {
            roleRepository.findByNombre(roleName).orElseGet(() -> {
                RoleEntity role = new RoleEntity();
                role.setNombre(roleName);
                return roleRepository.save(role);
            });
        }
    }
}
