package com.example.service;

import com.example.dto.ApiResponse;
import com.example.dto.profile.ProfileUpdateDTO;
import com.example.dto.profile.ProfileUpdatePasswordDTO;
import com.example.entity.ProfileEntity;
import com.example.exp.AppBadException;
import com.example.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public ApiResponse<String> update(ProfileUpdateDTO profile, String id) {
        ProfileEntity profileEntity = get(id);
        profileEntity.setName(profile.getName());
        profileEntity.setSurname(profile.getSurname());
        if (profile.getCategoryId() != null) {
            profileEntity.setCategoryId(profile.getCategoryId());
        }
        if (profile.getPhotoId() != null) {
            profileEntity.setPhotoId(profile.getPhotoId());
        }
        profileRepository.save(profileEntity);
        return ApiResponse.ok(List.of("Success"));
    }

    public ApiResponse<?> updatePassword(ProfileUpdatePasswordDTO profile, String id) {
        ProfileEntity profileEntity = get(id);
        profileEntity.setPassword(profile.getNewPassword());
        profileRepository.save(profileEntity);
        return ApiResponse.ok(List.of("Success"));
    }

    public ProfileEntity get(String id) {
        return profileRepository.findById(id).orElseThrow(() -> new AppBadException("profile not found"));
    }
}
