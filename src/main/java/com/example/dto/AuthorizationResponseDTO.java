package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.example.enums.ProfileRole;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorizationResponseDTO {
    @NotNull
    private String id;
    @NotBlank(message = "role required")
    private ProfileRole role;
    private String jwt;

}
