package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrationDTO {

    @NotBlank(message = "name required")
    private String name;

    @NotBlank(message = "surname required")
    private String surname;

    @NotBlank(message = "phoneNumber required")
    private String phoneNumber;

    @NotBlank(message = "email required")
    private String email;

    @NotBlank(message = "password required")
    private String password;
}
