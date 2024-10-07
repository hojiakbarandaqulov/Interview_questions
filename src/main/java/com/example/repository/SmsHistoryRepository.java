package com.example.repository;

import com.example.entity.SmsHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SmsHistoryRepository extends JpaRepository<SmsHistoryEntity, Integer> {

    Long countByPhoneAndCreatedDateBetween(String phone, LocalDateTime from, LocalDateTime to);
    // select count(*) from email_history createdDate between :from and :to


    Optional<SmsHistoryEntity> findByCreatedDate(LocalDateTime createdDate);

    Optional<SmsHistoryEntity> findTopByPhoneOrderByCreatedDateDesc(String phone);

}
