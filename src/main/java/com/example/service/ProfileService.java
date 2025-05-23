package com.example.service;

import com.example.controller.ProfileController;
import com.example.dto.ApiResponse;
import com.example.dto.attach.AttachDTO;
import com.example.dto.profile.ProfileUpdateDTO;
import com.example.dto.profile.ProfileUpdatePasswordDTO;
import com.example.entity.AttachEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.AppLanguage;
import com.example.enums.ProfileRole;
import com.example.exp.AppBadException;
import com.example.mapper.ProfileMapper;
import com.example.repository.ProfileRepository;
import com.example.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Locale;

@Slf4j
@Service
public class ProfileService {
    private final AttachService attachService;
    private final ProfileRepository profileRepository;
    private final ResourceBundleMessageSource resourceBundleMessageSource;
    private final ProfileMapper profileMapper;

    public ProfileService(AttachService attachService, ProfileRepository profileRepository, ResourceBundleMessageSource resourceBundleMessageSource, ProfileMapper profileMapper) {
        this.attachService = attachService;
        this.profileRepository = profileRepository;
        this.resourceBundleMessageSource = resourceBundleMessageSource;
        this.profileMapper = profileMapper;
    }

    public ApiResponse<String> update(ProfileUpdateDTO profile, String id, AppLanguage language) {
        ProfileEntity profileEntity = get(id);
        profileEntity.setName(profile.getName());
        profileEntity.setSurname(profile.getSurname());
      /*  if (profile.getCategoryId() != null) {
            log.info("Category id set {}", id);
            profileEntity.setCategoryId(profile.getCategoryId());
        }*/
        if (profile.getPhotoId() == null) {
            log.info("Photo id set {}", id);
            resourceBundleMessageSource.getMessage("photo.id.not.found",null,new Locale(language.name()));
        }
        profileEntity.setPhotoId(profile.getPhotoId());
        profileRepository.save(profileEntity);
        return ApiResponse.ok("success",countColumn());
    }

    public ApiResponse<?> updatePassword(ProfileUpdatePasswordDTO profile, String id, AppLanguage language) {
        ProfileEntity profileEntity = get(id);
        if (!profile.getConfirmation().equals(profile.getNewPassword())) {
            log.info("Password confirmation not set {}", id);
            resourceBundleMessageSource.getMessage("You.arent.validating.password.correctly", null, new Locale(language.name()));
        }
        profileEntity.setPassword(MD5Util.getMD5(profile.getNewPassword()));
        profileRepository.save(profileEntity);
        return ApiResponse.ok(List.of("success"),countColumn());
    }

    public ProfileEntity get(String id) {
        log.info("get {}", id);
        return profileRepository.findById(id).orElseThrow(() -> new AppBadException("user.not.found"));
    }

    public ApiResponse<?> saveProfilePhoto(MultipartFile file, String id) {
        AttachEntity attachEntity = attachService.getOriginalName(file.getOriginalFilename());
        ProfileEntity profileEntity = get(id);
        profileEntity.setPhotoId(attachEntity.getId());
        profileRepository.save(profileEntity);
        return ApiResponse.ok(List.of("success"),countColumn());
    }

    public int countColumn() {
        int i = profileRepository.countColumns();
        if (i==0){
            throw new AppBadException("no profile table column");
        }
        return i;
    }

    public ApiResponse<?> updateProfile(String id) {
        ProfileEntity profileEntity = get(id);
        profileEntity.setRole(ProfileRole.ROLE_ADMIN);
        profileRepository.save(profileEntity);
        return ApiResponse.ok(List.of("Profile role set Admin"),countColumn());
    }
}
