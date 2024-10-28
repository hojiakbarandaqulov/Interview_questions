package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {
    /*@NotBlank(message = "phone required")
    private String phone;*/
    @NotBlank(message = "email required")
    private String email;
    @NotBlank(message = "password required")
    private String password;
}
