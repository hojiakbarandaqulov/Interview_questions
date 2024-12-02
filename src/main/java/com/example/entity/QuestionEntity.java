package com.example.entity;

import com.example.enums.QuestionStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "question_entity")
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "language")
    private String language;

    @Column(name = "title")
    private String title;

    @Column(name ="created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_status")
    private QuestionStatus questionStatus;

    @Column(name = "question_type")
    private String questionType;

    @Column(name = "question_lesson_type")
    private String questionLessonType;

    @Column(name ="url_key")
    private String urlKey;

    @Column(name = "contaent_type")
    private String contentType;

    @Column(name = "super_admin_uername")
    private String superAdminUsername;

}
