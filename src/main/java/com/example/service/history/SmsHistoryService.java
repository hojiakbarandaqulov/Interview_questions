package com.example.service.history;

import com.example.dto.ApiResponse;
import com.example.entity.history.SmsHistoryEntity;
import com.example.enums.AppLanguage;
import com.example.enums.SmsStatus;
import com.example.exp.AppBadException;
import com.example.repository.sms.SmsHistoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class SmsHistoryService {

    private final SmsHistoryRepository smsHistoryRepository;
    private final ResourceBundleMessageSource resourceBundleMessageSource;

    /* private final ResourceMessageService resourceMessageService;
     private final AtomicReference<SmsSenderService> smsSenderService = new AtomicReference<SmsSenderService>();
     private static final int smsCountLimit = 3;


     public void sendRegistrationSms(String phoneNumber) {
         String smsCode = RandomUtil.getRandomSmsCode();
         String text = "<#>interview.uz partali. Ro'yxatdan o'tish uchun tasdiqlash kodi: " + smsCode;
         sendMessage(phoneNumber, text, smsCode);
     }

     public void sendMessage(String phone, String text, String smsCode) {
         Long countSms = smsHistoryRepository.countByPhoneAndCreatedDateBetween(phone,
                 LocalDateTime.now().minusMinutes(2),
                 LocalDateTime.now());
         if (countSms < smsCountLimit) {

             SmsHistoryEntity smsHistory = new SmsHistoryEntity();
             smsHistory.setCode(smsCode);
             smsHistory.setSmsCount(0);
             smsHistory.setStatus(SmsStatus.SEND);
             smsHistory.setPhone(phone);
             smsHistory.setSmsText(text);

             SmsHistoryEntity sms = smsHistoryRepository.save(smsHistory);
             smsSenderService.get().sendSmsHTTP(sms);
             return;
         }
         throw new AppBadException(resourceMessageService.getMessage("sms.limit.over"));
     }
     public ApiResponse<String> checkSmsCode(String phone, String code) {
         Optional<SmsHistoryEntity> optional = smsHistoryRepository.findTopByPhoneOrderByCreatedDateDesc(phone);
         if (optional.isEmpty()) {
             log.warn("Phone Incorrect! Phone = {}, code = {}", phone, code);
             return new ApiResponse<>(resourceMessageService.getMessage("sms.code.incorrect"), 400, true);
         }

         SmsHistoryEntity entity = optional.get();
         if (entity.getCreatedDate().plusMinutes(2L).isBefore(LocalDateTime.now())) {
             log.warn("Sms Code Incorrect! Phone = {}, code = {}", phone, code);
             smsHistoryRepository.updateStatus(entity.getId(), SmsStatus.USED_WITH_TIMEOUT);
             return new ApiResponse<>(resourceMessageService.getMessage("sms.time-out"), 400, true);
         }
         if (!entity.getCode().equals(code)) {
             return new ApiResponse<>(resourceMessageService.getMessage("sms.code.incorrect"), 400, true);
         }
         smsHistoryRepository.updateStatus(entity.getId(), SmsStatus.IS_USED);
         return new ApiResponse<>("Success!", 200, false);
     }

     public void sendResetSms(String phone, String signature) {
         if (signature == null) {
             signature = "";
         }
         String smsCode = RandomUtil.getRandomSmsCode();
 //        String text = "Scolaro.uz partali - parolni qayta tiklash uchun tasdiqlash kodi: " + smsCode;
         String text = "<#>interview.uz partali. Ro'yxatdan o'tish uchun tasdiqlash kodi: " + smsCode + "\n" + signature;
         sendMessage(phone, text, smsCode);
     }*/
    public String crete(String toPhone, String text) {
        SmsHistoryEntity smsHistoryEntity = new SmsHistoryEntity();
        smsHistoryEntity.setPhone(toPhone);
        smsHistoryEntity.setCode(text);
        smsHistoryRepository.save(smsHistoryEntity);
        return null;
    }

    public void checkPhoneLimit(String phone) { // 1 minute -3 attempt
        // 23/05/2024 19:01:13
        // 23/05/2024 19:01:23
        // 23/05/2024 19:01:33

        // 23/05/2024 19:00:55 -- (current -1)
        // 23/05/2024 19:01:55 -- current

        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = to.minusMinutes(2);

        long count = smsHistoryRepository.countByPhoneAndCreatedDateBetween(phone, from, to);
        if (count >= 3) {
            throw new AppBadException("Sms limit reached. Please try after some time");
        }
    }

    public void isNotExpiredPhone(String phone) {
        Optional<SmsHistoryEntity> optional = smsHistoryRepository.findTopByPhoneOrderByCreatedDateDesc(phone);
        if (optional.isEmpty()) {
            throw new AppBadException("Phone history not found");
        }
        SmsHistoryEntity entity = optional.get();
        if (entity.getCreatedDate().plusDays(1).isBefore(LocalDateTime.now())) {
            throw new AppBadException("Confirmation time expired");
        }
    }

    public ApiResponse<String> checkSmsCode(String phone, String code, AppLanguage language) {
        Optional<SmsHistoryEntity> optional = smsHistoryRepository.findTopByPhoneOrderByCreatedDateDesc(phone);
        if (optional.isEmpty()) {
            log.warn("Phone Incorrect! Phone = {}, code = {}", phone, code);
            String message = resourceBundleMessageSource.getMessage("sms.code.incorrect", null, new Locale(language.name()));
            throw new AppBadException(message);

        }
        SmsHistoryEntity entity = optional.get();
        if (entity.getCreatedDate().plusMinutes(2L).isBefore(LocalDateTime.now())) {
            log.warn("Sms Code Incorrect! Phone = {}, code = {}", phone, code);
            smsHistoryRepository.updateStatus(entity.getId(), SmsStatus.USED_WITH_TIMEOUT);
            String message = resourceBundleMessageSource.getMessage("sms.time-out", null, new Locale(language.name()));
            throw new AppBadException(message);
        }
        if (!entity.getCode().equals(code)) {
            String message = resourceBundleMessageSource.getMessage("sms.code.incorrect", null, new Locale(language.name()));
            throw new AppBadException(message);
        }
        smsHistoryRepository.updateStatus(entity.getId(), SmsStatus.IS_USED);
        return new ApiResponse<>("Success!", 200, false);
    }
}
