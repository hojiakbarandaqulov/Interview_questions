package com.example.dto.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileUpdatePasswordDTO {
    @NotBlank(message = "oldPassword required")
    @Size(min = 6, message = "Password min six character")
    private String newPassword;
    @NotBlank(message = "newPassword required")
    @Size(min = 6, message = "Password min six character")
    private String repetitionPassword;
    @NotBlank(message = "confirmation required")
    @Size(min = 6, message = "Password min six character")
    private String confirmation;
}
