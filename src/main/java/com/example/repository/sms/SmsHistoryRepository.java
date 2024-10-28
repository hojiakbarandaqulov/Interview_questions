package com.example.repository.sms;

import com.example.entity.history.SmsHistoryEntity;
import com.example.enums.SmsStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SmsHistoryRepository extends JpaRepository<SmsHistoryEntity, String> {
    Long countByPhoneAndCreatedDateBetween(String phone, LocalDateTime toTime, LocalDateTime fromTime);


//    @Query("SELECT s FROM SmsHistoryEntity s WHERE s.phone = ?1 ORDER BY s.createdDate DESC")
    Optional<SmsHistoryEntity> findTopByPhoneOrderByCreatedDateDesc(String phone);

    @Modifying
    @Transactional
    @Query("update SmsHistoryEntity set status = ?2 where id = ?1")
    void updateStatus(Integer id, SmsStatus status);

}
