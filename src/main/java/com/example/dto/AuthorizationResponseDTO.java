package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.example.enums.ProfileRole;

@Data
public class AuthorizationResponseDTO {
    @NotNull
    private String id;
    @NotBlank(message = "role required")
    private ProfileRole role;
    private String jwt;
}
