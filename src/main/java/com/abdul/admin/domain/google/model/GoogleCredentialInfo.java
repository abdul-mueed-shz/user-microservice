package com.abdul.admin.domain.google.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class GoogleCredentialInfo {

    private Long id;
    private String credentialsKey;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String accessToken;
    private Long expirationTimeMilliseconds;
    private String refreshToken;

}
