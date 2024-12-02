package com.example.service;

import com.example.dto.ApiResponse;
import com.example.dto.question.QuestionCreateDTO;
import com.example.entity.QuestionEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;

@Slf4j
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final ResourceBundleMessageSource resourceBundleMessageSource;

    public QuestionService(QuestionRepository questionRepository, ResourceBundleMessageSource resourceBundleMessageSource) {
        this.questionRepository = questionRepository;
        this.resourceBundleMessageSource = resourceBundleMessageSource;
    }

    public ApiResponse<QuestionCreateDTO> createQuestion(QuestionCreateDTO questionCreateDTO, AppLanguage language) {
        if (questionCreateDTO.getUrlKey().isEmpty()) {
            log.error("Question url key is empty");
            resourceBundleMessageSource.getMessage("where.is.the.question", null, new Locale(language.name()));
        }
        QuestionEntity entity = new QuestionEntity();
        entity.setUrlKey(questionCreateDTO.getUrlKey());
        entity.setTitle(questionCreateDTO.getTitle());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setQuestionType(questionCreateDTO.getQuestionType());
        entity.setQuestionLessonType(questionCreateDTO.getQuestionLessonType());
        entity.setContentType(questionCreateDTO.getContentType());
        entity.setSuperAdminUsername(questionCreateDTO.getSuperAdminUsername());
        return null;
    }
}
