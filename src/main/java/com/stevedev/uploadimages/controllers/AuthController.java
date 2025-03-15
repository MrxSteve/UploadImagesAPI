package com.stevedev.uploadimages.controllers;

import com.stevedev.uploadimages.errors.ResourceNotFoundException;
import com.stevedev.uploadimages.models.dto.request.AuthRequest;
import com.stevedev.uploadimages.models.dto.request.RefreshRequest;
import com.stevedev.uploadimages.models.dto.response.AuthResponse;
import com.stevedev.uploadimages.security.JwtUtil;
import com.stevedev.uploadimages.services.AuthService;
import com.stevedev.uploadimages.services.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest, HttpServletRequest request) {
        AuthResponse authResponse = authService.authenticate(authRequest, request);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshRequest request) {
        String refreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(refreshToken)
                .map(token -> {
                    String newToken = jwtUtil.generateToken(token.getUser().getUsername());
                    return ResponseEntity.ok(new AuthResponse(newToken, refreshToken));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Refresh token no encontrado"));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(Authentication authentication, @RequestBody RefreshRequest request) {
        String username = authentication.getName(); // Verifica el usuario autenticado
        refreshTokenService.deleteByUser(username); // Borra el Refresh Token del usuario autenticado

        Map<String, String> response = Map.of("message", "Sesión cerrada correctamente");
        return ResponseEntity.ok(response);
    }
}
