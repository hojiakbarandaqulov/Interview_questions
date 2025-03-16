package com.example.dto.auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SmsDTO {
    @NotNull(message = "phone required")
    private String phone;

    @NotNull(message = "code required")
    private String code;

}
