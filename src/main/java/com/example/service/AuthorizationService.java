package com.example.service;

import com.example.dto.ApiResponse;
import com.example.dto.AuthorizationResponseDTO;
import com.example.dto.LoginDTO;
import com.example.dto.RegistrationDTO;
import com.example.dto.auth.SmsDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.AppLanguage;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exp.AppBadException;
import com.example.repository.ProfileRepository;
import com.example.service.history.SmsHistoryService;
import com.example.service.history.SmsService;
import com.example.utils.JwtUtil;
import com.example.utils.MD5Util;
import com.example.utils.PhoneUtil;
import com.example.utils.RandomUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ResourceBanner;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class AuthorizationService {

    private final SmsHistoryService smsHistoryService;
    private final SmsService smsService;
    private final ProfileRepository profileRepository;
    private final ResourceBundleMessageSource resourceBundleMessageSource;

    public ApiResponse<?> registration(RegistrationDTO registrationDTO, AppLanguage language) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(registrationDTO.getPhone());
        if (optional.isPresent()) {
            log.info("Phone already exists = {}", registrationDTO.getPhone());
            String message = resourceBundleMessageSource.getMessage("phone.already.exists", null, new Locale(language.name()));
            throw new AppBadException(message);
        }
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setName(registrationDTO.getName());
        profileEntity.setSurname(registrationDTO.getSurname());
        profileEntity.setPhone(registrationDTO.getPhone());
        profileEntity.setPassword(MD5Util.getMD5(registrationDTO.getPassword()));
        profileEntity.setStatus(ProfileStatus.REGISTRATION);
        profileEntity.setRole(ProfileRole.ROLE_USER);
        profileRepository.save(profileEntity);

        smsService.sendSms(registrationDTO.getPhone());
        sendRegistrationPhone(profileEntity.getId(), registrationDTO.getPhone());
        return ApiResponse.ok("Successfully registered");
    }

    public void sendRegistrationPhone(String profileId, String phone) {
        String url = "http://localhost:8080/api/v1/authorization/verification/" + profileId;
        String text = String.format(RandomUtil.getRandomSmsCode(), url);
        smsHistoryService.crete(phone, text);
    }

    public ApiResponse<?> authorizationVerification(SmsDTO dto, AppLanguage language) {
        boolean validate = PhoneUtil.validatePhone(dto.getPhone());
        if (!validate) {
            log.info("Phone not Valid! phone = {}", dto.getPhone());
            String message = resourceBundleMessageSource.getMessage("phone.validation.not-valid", null, new Locale(language.name()));
            throw new AppBadException(message);
        }

        Optional<ProfileEntity> userOptional = profileRepository.findByPhoneAndVisibleIsTrue(dto.getPhone());
        if (userOptional.isEmpty()) {
            log.warn("Client not found! phone = {}", dto.getPhone());
            String message = resourceBundleMessageSource.getMessage("client.not.found", null, new Locale(language.name()));
            throw new AppBadException(message);
        }
        ProfileEntity profile = userOptional.get();
        if (!profile.getStatus().equals(ProfileStatus.REGISTRATION)) {
            log.warn("Profile Status Blocked! Phone = {}", dto.getPhone());
            String message = resourceBundleMessageSource.getMessage("wrong.client.status", null, new Locale(language.name()));
            throw new AppBadException(message);
        }

        ApiResponse<String> smsResponse = smsHistoryService.checkSmsCode(dto.getPhone(), dto.getCode(),language);
        if (smsResponse.getIsError()) {
            String message = resourceBundleMessageSource.getMessage("sms.code.incorrect", null, new Locale(language.name()));
            throw new AppBadException(message);
        }
        // change client status
        profileRepository.updateStatus(profile.getId(), ProfileStatus.ACTIVE);
        return new ApiResponse<>(200, false, "Successful");
    }

    public ApiResponse<AuthorizationResponseDTO> login(LoginDTO dto, AppLanguage language) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndPasswordAndVisibleIsTrue(
                dto.getPhone(),
                MD5Util.getMD5(dto.getPassword()));
        if (optional.isEmpty()) {
            log.error("user not found");
            String message = resourceBundleMessageSource.getMessage("user.not.found", null, new Locale(language.name()));
            throw new AppBadException(message);
        }
        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(ProfileStatus.ACTIVE)) {
            log.error("wrong status");
            String message = resourceBundleMessageSource.getMessage("profile.status.error", null, new Locale(language.name()));
            throw new AppBadException(message);
        }

        AuthorizationResponseDTO responseDTO = new AuthorizationResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setRole(entity.getRole());
        responseDTO.setJwt(JwtUtil.encode(responseDTO.getId(), entity.getPhone(), responseDTO.getRole()));
        return ApiResponse.ok(responseDTO);
    }

    public ApiResponse<?> registrationResendPhone(String phone, AppLanguage language) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(phone);
        if (optional.isEmpty()) {
            log.error("phone not found");
            throw new AppBadException("Phone not exists");
        }
        ProfileEntity entity = optional.get();
        smsHistoryService.isNotExpiredPhone(entity.getPhone());
        if (!entity.getVisible() || !entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            log.error("registration not completed");
            String message = resourceBundleMessageSource.getMessage("registration.not.completed", null, new Locale(language.name()));
            throw new AppBadException(message);
        }
        smsHistoryService.checkPhoneLimit(phone);
        smsService.sendSms(phone);
        sendRegistrationPhone(entity.getId(), phone);
        return ApiResponse.ok("To complete your registration please verify your phone.");
    }
}
