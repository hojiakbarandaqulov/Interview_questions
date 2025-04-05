package com.example.service;

import com.example.dto.ApiResponse;
import com.example.dto.AuthorizationResponseDTO;
import com.example.dto.LoginDTO;
import com.example.dto.RegistrationDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.AppLanguage;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exp.AppBadException;
import com.example.repository.ProfileRepository;
import com.example.service.history.EmailHistoryService;
import com.example.service.history.MailSenderService;
import com.example.service.history.SmsHistoryService;
import com.example.service.history.SmsService;
import com.example.utils.JwtUtil;
import com.example.utils.MD5Util;
import com.example.utils.RandomUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@Service
public class AuthorizationService {
    @Value("${spring.mail.username}")
    private String fromAccount;

    @Value("${server.domain}")
    private String serverDomain;

    private final SmsHistoryService smsHistoryService;
    private final SmsService smsService;
    private final MailSenderService mailSenderService;
    private final ProfileRepository profileRepository;
    private final EmailHistoryService emailHistoryService;
    private final ResourceBundleMessageSource resourceBundleMessageSource;

    public AuthorizationService(SmsHistoryService smsHistoryService, SmsService smsService, MailSenderService mailSenderService, ProfileRepository profileRepository, EmailHistoryService emailHistoryService, ResourceBundleMessageSource resourceBundleMessageSource) {
        this.smsHistoryService = smsHistoryService;
        this.smsService = smsService;
        this.mailSenderService = mailSenderService;
        this.profileRepository = profileRepository;
        this.emailHistoryService = emailHistoryService;
        this.resourceBundleMessageSource = resourceBundleMessageSource;
    }

    public ApiResponse<String> registration(RegistrationDTO registrationDTO, AppLanguage language) {
//        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(registrationDTO.getEmail());
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(registrationDTO.getEmail());
        if (optional.isPresent()) {
            log.info("Email already exists = {}", registrationDTO.getEmail());
            String message = resourceBundleMessageSource.getMessage("email.already.exists", null, new Locale(language.name()));
            throw new AppBadException(message);
        }
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setName(registrationDTO.getName());
        profileEntity.setSurname(registrationDTO.getSurname());
        profileEntity.setEmail(registrationDTO.getEmail());
        profileEntity.setPassword(MD5Util.getMD5(registrationDTO.getPassword()));
        profileEntity.setStatus(ProfileStatus.REGISTRATION);
        profileEntity.setRole(ProfileRole.ROLE_USER);
        profileRepository.save(profileEntity);

        sendRegistrationEmail(profileEntity.getId(), profileEntity.getEmail());
       /* smsService.sendSms(registrationDTO.getEmail());
        sendRegistrationPhone(profileEntity.getId(), registrationDTO.getEmail());*/
        return ApiResponse.ok(List.of("registration.completed"),countColumn());
    }

    public void sendRegistrationPhone(String profileId, String phone) {
        String url = "http://localhost:8080/api/v1/authorization/verification/" + profileId;
        String text = String.format(RandomUtil.getRandomSmsCode(), url);
        smsHistoryService.crete(phone, text);
    }

    //    public ApiResponse<?> authorizationVerification(SmsDTO dto, AppLanguage language) {
//        boolean validate = PhoneUtil.validatePhone(dto.getPhone());
//        if (!validate) {
//            log.info("Phone not Valid! phone = {}", dto.getPhone());
//            String message = resourceBundleMessageSource.getMessage("phone.validation.not-valid", null, new Locale(language.name()));
//            throw new AppBadException(message);
//        }
//
//        Optional<ProfileEntity> userOptional = profileRepository.findByPhoneAndVisibleIsTrue(dto.getPhone());
//        if (userOptional.isEmpty()) {
//            log.warn("Client not found! phone = {}", dto.getPhone());
//            String message = resourceBundleMessageSource.getMessage("client.not.found", null, new Locale(language.name()));
//            throw new AppBadException(message);
//        }
//        ProfileEntity profile = userOptional.get();
//        if (!profile.getStatus().equals(ProfileStatus.REGISTRATION)) {
//            log.warn("Profile Status Blocked! Phone = {}", dto.getPhone());
//            String message = resourceBundleMessageSource.getMessage("wrong.client.status", null, new Locale(language.name()));
//            throw new AppBadException(message);
//        }
//
//        ApiResponse<String> smsResponse = smsHistoryService.checkSmsCode(dto.getPhone(), dto.getCode(),language);
//        if (smsResponse.getIsError()) {
//            String message = resourceBundleMessageSource.getMessage("sms.code.incorrect", null, new Locale(language.name()));
//            throw new AppBadException(message);
//        }
//        // change client status
//        profileRepository.updateStatus(profile.getId(), ProfileStatus.ACTIVE);
//        return new ApiResponse<>(200, false, "Successful");
//    }
    public ApiResponse<String> authorizationVerification(String userId, AppLanguage language) {
        Optional<ProfileEntity> optional = profileRepository.findById(userId);
        if (optional.isEmpty()) {
            log.warn("User not found => {}", userId);
            String message = resourceBundleMessageSource.getMessage("user.not.found", null, new Locale(language.name()));
            throw new AppBadException(message);
        }
        ProfileEntity entity = optional.get();
        if (!entity.getVisible() || !entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            String message = resourceBundleMessageSource.getMessage("registration.not.completed", null, new Locale(language.name()));
            throw new AppBadException(message);
        }
        profileRepository.updateStatus(userId, ProfileStatus.ACTIVE);
        String message = resourceBundleMessageSource.getMessage("success", null, new Locale(language.name()));
        return ApiResponse.ok(List.of(message),countColumn());
    }

    public ApiResponse<AuthorizationResponseDTO> login(LoginDTO dto, AppLanguage language) {
        /*Optional<ProfileEntity> optional = profileRepository.findByPhoneAndPasswordAndVisibleIsTrue(
                dto.getPhone(),
                MD5Util.getMD5(dto.getPassword()));*/
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndPasswordAndVisibleIsTrue(
                dto.getEmail(),
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

        return ApiResponse.ok(List.of(responseDTO),countColumn());
    }

    public ApiResponse<?> registrationResendEmail(String email, AppLanguage language) {
        /*Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(phone);*/
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(email);
        if (optional.isEmpty()) {
            log.error("email not found");
            String message = resourceBundleMessageSource.getMessage("email.not.found", null, new Locale(language.name()));
            throw new AppBadException(message);
        }
        ProfileEntity entity = optional.get();
//        smsHistoryService.isNotExpiredPhone(entity.getPhone());
        emailHistoryService.isNotExpiredEmail(entity.getEmail());
        if (!entity.getVisible() || !entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            log.error("registration not completed");
            String message = resourceBundleMessageSource.getMessage("registration.not.completed", null, new Locale(language.name()));
            throw new AppBadException(message);
        }
       /* smsHistoryService.checkPhoneLimit(phone);
        smsService.sendSms(phone);*/
//        sendRegistrationPhone(entity.getId(), email);
        emailHistoryService.checkEmailLimit(email);
        sendRegistrationRandomCodeEmail(entity.getId(), email);
        return ApiResponse.ok(List.of("registration.completed"),countColumn());
    }

    public void sendRegistrationEmail(String profileId, String email) {
        // send email
//        String url = String.format("http://5.182.26.40:9090/api/v1/authorization/verification/" + profileId);
        String url = String.format("http://localhost:8080/api/v1/authorization/verification/" + profileId);
        String formatText = "<style>\n" +
                "    a:link, a:visited {\n" +
                "        background-color: #f44336;\n" +
                "        color: white;\n" +
                "        padding: 14px 25px;\n" +
                "        text-align: center;\n" +
                "        text-decoration: none;\n" +
                "        display: inline-block;\n" +
                "    }\n" +
                "\n" +
                "    a:hover, a:active {\n" +
                "        background-color: red;\n" +
                "    }\n" +
                "</style>\n" +
                "<div style=\"text-align: center\">\n" +
                "    <h1>Welcome to interview.uz web portal</h1>\n" +
                "    <br>\n" +
                "    <p>Please button lick below to complete registration</p>\n" +
                "    <div style=\"text-align: center\">\n" +
                "        <a href=\"%s\" target=\"_blank\">This is a link</a>\n" +
                "    </div>";
        String text = String.format(formatText, url);
        mailSenderService.send(email, "Registration completed", text);
        emailHistoryService.create(email, text); // create history
    }

    public void sendRegistrationRandomCodeEmail(String profileId, String email) {
        // send email
//        String url = "http://5.182.26.40:9090/api/v1/authorization/verification/" + profileId;
        String url = "%s/api/v1/authorization/verification/" + profileId;
        String text = String.format(RandomUtil.getRandomSmsCode(), url);
        mailSenderService.send(email, "Complete registration", text);
        emailHistoryService.create(email, text); // create history
    }

    public Integer countColumn() {
        Integer i = profileRepository.countColumns();
        if (i==0){
            throw new AppBadException("no profile table column");
        }
        return i;
    }
}
