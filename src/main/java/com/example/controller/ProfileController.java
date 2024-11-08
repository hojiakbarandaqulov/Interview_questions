package com.example.controller;

import com.example.dto.ApiResponse;
import com.example.dto.profile.ProfileUpdateDTO;
import com.example.dto.profile.ProfileUpdatePasswordDTO;
import com.example.entity.ProfileEntity;
import com.example.service.ProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@EnableMethodSecurity(prePostEnabled = true)
@RestController
@RequestMapping("/api/v1/profile")
@Tag(name = "Profile Controller", description = "Api list for profile, profile and other ....")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }


    @PutMapping("/update/{id}")
    public ApiResponse<?> updateProfile(@RequestBody ProfileUpdateDTO profile,
                                        @PathVariable String id) {
        return profileService.update(profile,id);
    }

    @PutMapping("/update/password/{id}")
    public ApiResponse<?> updateProfilePassword(@RequestBody ProfileUpdatePasswordDTO profile,
                                                @PathVariable String id) {
        return profileService.updatePassword(profile,id);
    }
}
