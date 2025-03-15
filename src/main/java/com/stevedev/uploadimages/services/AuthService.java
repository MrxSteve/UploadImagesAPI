package com.stevedev.uploadimages.services;

import com.stevedev.uploadimages.errors.ResourceNotFoundException;
import com.stevedev.uploadimages.models.dto.request.AuthRequest;
import com.stevedev.uploadimages.models.dto.response.AuthResponse;
import com.stevedev.uploadimages.models.entities.RefreshTokenEntity;
import com.stevedev.uploadimages.models.entities.UserEntity;
import com.stevedev.uploadimages.models.repositories.UserRepository;
import com.stevedev.uploadimages.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final LoginAuditService loginAuditService;

    @Transactional
    public AuthResponse authenticate(AuthRequest authRequest, HttpServletRequest request) {
        // Buscar usuario en la base de datos
        UserEntity user = userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Verificar contraseña
        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Contraseña incorrecta");
        }

        // Generar token con el usuario
        String token = jwtUtil.generateToken(user.getUsername());
        // Crear refresh token
        RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(user.getUsername());

        // Loguear el inicio de sesión
        loginAuditService.logLogin(
                String.valueOf(user.getId()),
                user.getEmail(),
                user.getUsername(),
                request
        );

        return AuthResponse.builder()
                .token(token)
                .refreshToken(refreshToken.getToken())
                .build();
    }
}
