package com.example.repository;

import com.example.entity.ProfileEntity;
import com.example.enums.ProfileStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, String> {

    Optional<ProfileEntity> findByPhoneAndVisibleTrue(String username);
    @Transactional
    @Modifying
    @Query("update ProfileEntity set status =?2 where id =?1")
    void updateStatus(String profileId, ProfileStatus status);

    Optional<ProfileEntity> findByPhoneAndPasswordAndVisibleIsTrue(String phone, String md5);

    Optional<ProfileEntity> findByPhoneAndVisibleIsTrue(String phone);

    Optional<ProfileEntity> findByEmailAndVisibleTrue(String email);

    Optional<ProfileEntity> findByEmailAndPasswordAndVisibleIsTrue(String email, String md5);

    @Query(value = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'profile'", nativeQuery = true)
    Integer countColumns();
}
