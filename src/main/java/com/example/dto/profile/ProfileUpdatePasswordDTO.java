package com.example.dto.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileUpdatePasswordDTO {
    @NotBlank(message = "oldPassword required")
    private String oldPassword;
    @NotBlank(message = "newPassword required")
    private String newPassword;
    @NotBlank(message = "confirmation required")
    private String confirmation;
}
