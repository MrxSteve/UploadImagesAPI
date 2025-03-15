package com.stevedev.uploadimages.controllers;

import com.stevedev.uploadimages.models.dto.response.UserResponse;
import com.stevedev.uploadimages.models.entities.LoginAuditEntity;
import com.stevedev.uploadimages.services.LoginAuditService;
import com.stevedev.uploadimages.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    private final UserService userService;
    private final LoginAuditService loginAuditService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMyProfile(Authentication authentication) {
        UserResponse response = userService.getMyProfile(authentication);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/login-history")
    public ResponseEntity<Page<LoginAuditEntity>> getLoginHistory(Authentication authentication, Pageable pageable) {
        Page<LoginAuditEntity> loginHistory = loginAuditService.getLoginHistory(authentication, pageable);
        return ResponseEntity.ok(loginHistory);
    }
}
