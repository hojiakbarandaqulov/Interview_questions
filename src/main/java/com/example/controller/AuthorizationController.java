package com.example.controller;

import com.example.dto.ApiResponse;
import com.example.dto.LoginDTO;
import com.example.dto.RegistrationDTO;
import com.example.dto.auth.SmsDTO;
import com.example.enums.AppLanguage;
import com.example.service.AuthorizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@EnableMethodSecurity(prePostEnabled = true)
@RestController
@Slf4j
@RequestMapping("/api/v1/authorization")
@Tag(name = "Auth Controller", description = "Api list for authorization, registration and other ....")
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/registration")
    @Operation(summary = "Registration", description = "Api for profile registration")
    public ResponseEntity<ApiResponse<?>> registration(@Valid @RequestBody RegistrationDTO registrationDTO,
                                                       @RequestHeader(value = "Accept-Language", defaultValue = "uz")AppLanguage language) {
        ApiResponse<?> response = authorizationService.registration(registrationDTO,language);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody LoginDTO loginDTO,
                                                @RequestHeader(value = "Accept-Language", defaultValue = "uz")AppLanguage language) {
        ApiResponse<?> response = authorizationService.login(loginDTO,language);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/verification")
    public ResponseEntity<ApiResponse<?>> verification(@Valid @RequestBody SmsDTO smsDTO,
                                                       @RequestHeader(value = "Accept-Language", defaultValue = "uz")AppLanguage language) {
        ApiResponse<?> response = authorizationService.authorizationVerification(smsDTO,language);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/registration/resend/{phone}")
    public ResponseEntity<ApiResponse<?>> registrationResend(@PathVariable("phone") String phone,
                                                             @RequestHeader(value = "Accept-Language", defaultValue = "uz")AppLanguage language) {
        ApiResponse<?> response = authorizationService.registrationResendPhone(phone,language);
        return ResponseEntity.ok().body(response);
    }
}
