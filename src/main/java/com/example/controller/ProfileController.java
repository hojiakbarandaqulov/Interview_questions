package com.example.controller;

import com.example.dto.ApiResponse;
import com.example.dto.attach.AttachDTO;
import com.example.dto.profile.ProfileUpdateDTO;
import com.example.dto.profile.ProfileUpdatePasswordDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.AppLanguage;
import com.example.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<?>> updateProfile(@Valid @RequestBody ProfileUpdateDTO profile,
                                                       @PathVariable String id,
                                                       @RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage language) {
        log.info("Updating profile with id {}", id);
        return ResponseEntity.ok().body(profileService.update(profile,id,language));
    }


    @PutMapping("/update/password/{id}")
    public ResponseEntity<ApiResponse<?>> updateProfilePassword(@Valid @RequestBody ProfileUpdatePasswordDTO profile,
                                                @PathVariable String id,
                                                @RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage language) {
        log.info("Updating profile password with id {}", id);
        return ResponseEntity.ok().body(profileService.updatePassword(profile,id,language));
    }

    @PostMapping("/create/photo/{id}")
    @Operation(summary = "upload api", description = "Api list attach create")
    public ResponseEntity<ApiResponse<?>> saveProfilePhoto(@RequestParam("file") MultipartFile file,  @PathVariable String id,
                                                           @RequestParam(defaultValue = "uz") AppLanguage language) {
        log.info("upload attach  = {}", file.getOriginalFilename());
        ApiResponse<?> response = profileService.saveProfilePhoto(file,id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PutMapping("/update/profile_role/{id}")
    @Operation(summary = "Delete user", description = "Delete user")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponse<?>> updateProfileRole(@PathVariable String id) {
        ApiResponse<?> response = profileService.updateProfile(id);
        return ResponseEntity.ok(response);
    }
}
