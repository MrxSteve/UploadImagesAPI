package com.stevedev.uploadimages.models.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "login_audit")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class LoginAuditEntity {
    @Id
    private String id;

    private String userId;
    private String email;
    private String username;
    private Instant loginTime;

    private String userAgent;
    private String device;
    private String browser;
    private String country;
    private String region;
}
