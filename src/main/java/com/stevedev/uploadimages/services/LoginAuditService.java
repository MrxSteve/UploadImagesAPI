package com.stevedev.uploadimages.services;

import com.stevedev.uploadimages.models.entities.LoginAuditEntity;
import com.stevedev.uploadimages.models.repositories.LoginAuditRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginAuditService {
    private final LoginAuditRepository loginAuditRepository;
    private final RestTemplate restTemplate;

    public void logLogin(String userId, String email, String username, HttpServletRequest request) {
        String userIp = getClientIp(request);
        String[] location = getGeoLocation(userIp);
        String country = location[0];
        String region = location[1];

        String userAgent = sanitizeUserAgent(request.getHeader("User-Agent"));
        String device = getDeviceType(userAgent);
        String browser = getBrowser(userAgent);

        LoginAuditEntity loginAudit = LoginAuditEntity.builder()
                .userId(userId)
                .email(email)
                .username(username)
                .loginTime(Instant.now())
                .userAgent(userAgent)
                .device(device)
                .browser(browser)
                .country(country)
                .region(region)
                .build();

        loginAuditRepository.save(loginAudit);
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "127.0.0.1".equals(ip) ? "8.8.8.8" : ip.split(",")[0].trim();
    }

    private String sanitizeUserAgent(String userAgent) {
        return (userAgent == null || userAgent.isEmpty()) ? "Desconocido"
                : userAgent.replaceAll("[^a-zA-Z0-9 ._-]", "");
    }

    private String[] getGeoLocation(String ip) {
        try {
            String url = "https://ipwhois.app/json/" + ip;
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                String country = (String) response.getBody().get("country");
                String region = (String) response.getBody().get("region");

                return new String[]{
                        country != null ? country : "Desconocido",
                        region != null ? region : "Desconocido"
                };
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[]{"Desconocido", "Desconocido"};
    }

    public Page<LoginAuditEntity> getLoginHistory(Authentication authentication, Pageable pageable) {
        String username = authentication.getName();
        Page<LoginAuditEntity> loginHistory = loginAuditRepository.findByUsername(username, pageable);
        return loginHistory;
    }

    private String getDeviceType(String userAgent) {
        if (userAgent.toLowerCase().contains("mobile")) {
            return "Mobile";
        } else if (userAgent.toLowerCase().contains("tablet")) {
            return "Tablet";
        }
        return "PC";
    }

    private String getBrowser(String userAgent) {
        if (userAgent.contains("Chrome")) {
            return "Chrome";
        } else if (userAgent.contains("Firefox")) {
            return "Firefox";
        } else if (userAgent.contains("Safari")) {
            return "Safari";
        }
        return "Other";
    }
}
