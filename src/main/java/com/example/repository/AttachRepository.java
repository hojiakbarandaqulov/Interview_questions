package com.example.repository;

import com.example.entity.AttachEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AttachRepository extends JpaRepository<AttachEntity, String> {


    @Query("from  AttachEntity where originalName=:orginalName order by createdDate desc limit 1")
    Optional<AttachEntity> findByOrginalName(@Param("orginalName") String orginalName);
}
