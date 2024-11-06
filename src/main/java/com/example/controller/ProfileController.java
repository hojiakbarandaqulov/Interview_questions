package com.example.controller;

import com.example.service.ProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@EnableMethodSecurity(prePostEnabled = true)
@RestController
@RequestMapping("/api/v1/profile")
@Tag(name = "Profile Controller", description = "Api list for profile, profile and other ....")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }
}
