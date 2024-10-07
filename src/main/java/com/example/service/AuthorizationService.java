package com.example.service;

import com.example.dto.ApiResponse;
import com.example.dto.AuthorizationResponseDTO;
import com.example.dto.LoginDTO;
import com.example.dto.RegistrationDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exp.AppBadException;
import com.example.repository.ProfileRepository;
import com.example.service.history.SmsHistoryService;
import com.example.service.history.SmsService;
import com.example.utils.JwtUtil;
import com.example.utils.MD5Util;
import com.example.utils.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class AuthorizationService {
    private final SmsHistoryService smsHistoryService;
    private final SmsService smsService;
    private final ProfileRepository profileRepository;

    public AuthorizationService(SmsHistoryService smsHistoryService, SmsService smsService, ProfileRepository profileRepository) {
        this.smsHistoryService = smsHistoryService;
        this.smsService = smsService;
        this.profileRepository = profileRepository;
    }

    public ApiResponse<?> registration(RegistrationDTO registrationDTO) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(registrationDTO.getPhone());
        if (optional.isPresent()) {
            log.info("Phone already exists");
            throw new AppBadException("Phone exists");
        }
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setName(registrationDTO.getName());
        profileEntity.setSurname(registrationDTO.getSurname());
        profileEntity.setPhone(registrationDTO.getPhone());
        profileEntity.setPassword(MD5Util.getMD5(registrationDTO.getPassword()));
        profileEntity.setStatus(ProfileStatus.ACTIVE);
        profileEntity.setRole(ProfileRole.ROLE_USER);
        profileRepository.save(profileEntity);

        sendRegistrationPhone(profileEntity.getId(), registrationDTO.getPhone());
        smsService.sendSms(registrationDTO.getPhone());
        return ApiResponse.ok("Registration Successful");

    }

    public void sendRegistrationPhone(String profileId, String phone) {
        String url = "http://localhost:8080/api/v1/authorization/verification/" + profileId;
        String text = String.format(RandomUtil.getRandomSmsCode(), url);
        smsHistoryService.crete(phone, text);
        smsService.sendSms(phone);
    }

    public ApiResponse<?> authorizationVerification(String userId) {
        Optional<ProfileEntity> optional = profileRepository.findById(userId);
        if (optional.isEmpty()) {
            log.error("profile not found");
            throw new AppBadException("User not found");
        }

        ProfileEntity entity = optional.get();
        if (!entity.getVisible() || !entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            log.error("Registration not completed");
            throw new AppBadException("Registration not completed");
        }
        profileRepository.updateStatus(userId, ProfileStatus.ACTIVE);
        return ApiResponse.ok("Successfully verified user");
    }

    public ApiResponse<AuthorizationResponseDTO> login(LoginDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndPasswordAndVisibleIsTrue(
                dto.getPhone(),
                MD5Util.getMD5(dto.getPassword()));
        if (optional.isEmpty()) {
            log.error("user not found");
            throw new AppBadException("User not found");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(ProfileStatus.ACTIVE)) {
            log.error("wrong status");
            throw new AppBadException("Wrong status");
        }

        AuthorizationResponseDTO responseDTO = new AuthorizationResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setRole(entity.getRole());
        responseDTO.setJwt(JwtUtil.encode(responseDTO.getId(), entity.getPhone(), responseDTO.getRole()));
        return ApiResponse.ok(responseDTO);
    }

    public ApiResponse<?> registrationResendPhone(String phone) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(phone);
        if (optional.isEmpty()) {
            log.error("phone not found");
            throw new AppBadException("Phone not exists");
        }
        ProfileEntity entity = optional.get();
        smsHistoryService.isNotExpiredPhone(entity.getPhone());
        if (!entity.getVisible() || !entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            log.error("registration not completed");
            throw new AppBadException("Registration not completed");
        }
        smsHistoryService.checkPhoneLimit(phone);
        sendRegistrationPhone(entity.getId(), phone);
        return ApiResponse.ok("To complete your registration please verify your phone.");
    }
}
