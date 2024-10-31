package com.example.repository;

import com.example.entity.AttachEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AttachRepository extends JpaRepository<AttachEntity, String> {
    @Modifying
    @Transactional
    @Query(" update AttachEntity  set compressedId = ?2 where id = ?1")
    void updateCompressedId(String id, String compressedId);
}
