package org.project.bilryozo.domain.users.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class RegisterRequestDto {
    private String username;
    private String email;
    private String password;
    private LocalDate birthDate;
    private String role;
    private LocalDate rentAvailableDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long updatedBy;
    private LocalDateTime deletedAt;
    private Long deletedBy;
}
