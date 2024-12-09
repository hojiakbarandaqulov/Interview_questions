package com.example.repository;

import com.example.entity.QuestionCreateEntity;
import com.example.entity.QuestionCreateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<QuestionCreateEntity, Long> {


    @Query(value = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'question_create'", nativeQuery = true)
    Integer countColumns();
}
