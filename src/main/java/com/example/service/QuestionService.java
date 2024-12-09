package com.example.service;

import com.example.dto.ApiResponse;
import com.example.dto.question.QuestionCreateDTO;
import com.example.dto.question.QuestionPaginationDTO;
import com.example.entity.QuestionCreateEntity;
import com.example.entity.QuestionCreateEntity;
import com.example.enums.AppLanguage;
import com.example.enums.QuestionStatus;
import com.example.exp.AppBadException;
import com.example.mapper.QuestionMapper;
import com.example.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final ResourceBundleMessageSource resourceBundleMessageSource;
    private final QuestionMapper questionMapper;

    public QuestionService(QuestionRepository questionRepository, ResourceBundleMessageSource resourceBundleMessageSource, QuestionMapper questionMapper) {
        this.questionRepository = questionRepository;
        this.resourceBundleMessageSource = resourceBundleMessageSource;
        this.questionMapper = questionMapper;
    }

    public ApiResponse<?> createQuestion(QuestionCreateDTO questionCreateDTO, AppLanguage language) {
        if (questionCreateDTO.getUrlKey().isEmpty()) {
            log.error("Question url key is empty");
            resourceBundleMessageSource.getMessage("where.is.the.question", null, new Locale(language.name()));
        }
        QuestionCreateEntity entity = new QuestionCreateEntity();
        entity.setUrlKey(questionCreateDTO.getUrlKey());
        entity.setTitle(questionCreateDTO.getTitle());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setQuestionType(questionCreateDTO.getQuestionType());
        entity.setQuestionLessonType(questionCreateDTO.getQuestionLessonType());
        entity.setContentType(questionCreateDTO.getContentType());
        entity.setSuperAdminUsername(questionCreateDTO.getSuperAdminUsername());
        entity.setQuestionStatus(QuestionStatus.PRIVATE);
        entity.setVisible(true);
        questionRepository.save(entity);
        QuestionCreateDTO responseDTO = questionMapper.toDTO(entity);
        return ApiResponse.ok(List.of(responseDTO),countColumn());
    }

    public Integer countColumn() {
        Integer countTable = questionRepository.countColumns();
        if (countTable==0){
            throw new AppBadException("no profile table column");
        }
        return countTable;
    }

    public ApiResponse<?> updateQuestion(QuestionCreateDTO questionCreateDTO,Long id, AppLanguage language) {
        QuestionCreateEntity entity = getQuestionById(id);
        entity.setUrlKey(questionCreateDTO.getUrlKey());
        entity.setTitle(questionCreateDTO.getTitle());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setQuestionType(questionCreateDTO.getQuestionType());
        entity.setQuestionLessonType(questionCreateDTO.getQuestionLessonType());
        entity.setContentType(questionCreateDTO.getContentType());
        entity.setSuperAdminUsername(questionCreateDTO.getSuperAdminUsername());
        entity.setQuestionStatus(QuestionStatus.PRIVATE);
        questionRepository.save(entity);
        QuestionCreateDTO responseDTO = questionMapper.toDTO(entity);
        return ApiResponse.ok(List.of(responseDTO),countColumn());
    }

    public QuestionCreateEntity getQuestionById(Long id) {
        log.info("get question by id: {}", id);
        return questionRepository.findById(id).orElseThrow(()->new AppBadException("Question not found"));
    }

    public ApiResponse<PageImpl<QuestionPaginationDTO>> pagination(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        PageRequest pageable = PageRequest.of(page, size, sort);
        Page<QuestionCreateEntity> pageEntity = questionRepository.findAll(pageable);
        List<QuestionPaginationDTO> dtoList = new LinkedList<>();
        for (QuestionCreateEntity questionEntity : pageEntity.getContent()) {
            dtoList.add(questionMapper.toPaginationDTO(questionEntity));
        }
        long count = pageEntity.getTotalElements();
        return ApiResponse.ok(List.of(new PageImpl<>(dtoList, pageable, count)),countColumn());
    }

    public ApiResponse<Boolean> deleteQuestion(Long id) {
        QuestionCreateEntity entity = getQuestionById(id);
        entity.setVisible(false);
        questionRepository.save(entity);
        return ApiResponse.ok(List.of(true),countColumn());
    }

    public ApiResponse<Boolean> changeStatus(Long id) {
        QuestionCreateEntity questionById = getQuestionById(id);
        if (questionById.getQuestionStatus()==QuestionStatus.PRIVATE) {
            questionById.setQuestionStatus(QuestionStatus.PUBLISH);
        }else {
            questionById.setQuestionStatus(QuestionStatus.PRIVATE);
        }
        questionRepository.save(questionById);
        return ApiResponse.ok(List.of(true),countColumn());
    }
}
