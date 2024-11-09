package com.example.controller;

import com.example.dto.ApiResponse;
import com.example.dto.profile.ProfileUpdateDTO;
import com.example.dto.profile.ProfileUpdatePasswordDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.AppLanguage;
import com.example.service.ProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@EnableMethodSecurity(prePostEnabled = true)
@Slf4j
@RestController
@RequestMapping("/api/v1/profile")
@Tag(name = "Profile Controller", description = "Api list for profile, profile and other ....")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PutMapping("/update/{id}")
    public ApiResponse<?> updateProfile(@Valid @RequestBody ProfileUpdateDTO profile,
                                        @PathVariable String id,
                                        @RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage language) {
        log.info("Updating profile with id {}", id);
        return profileService.update(profile,id,language);
    }

    @PutMapping("/update/password/{id}")
    public ApiResponse<?> updateProfilePassword(@Valid @RequestBody ProfileUpdatePasswordDTO profile,
                                                @PathVariable String id,
                                                @RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage language) {
        log.info("Updating profile password with id {}", id);
        return profileService.updatePassword(profile,id,language);
    }
}
