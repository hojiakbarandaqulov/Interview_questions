package com.example.controller;

import com.example.dto.ApiResponse;
import com.example.dto.question.QuestionCreateDTO;
import com.example.enums.AppLanguage;
import com.example.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/question")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/create/question")
    public ResponseEntity<ApiResponse<?>> createQuestion(@Valid @RequestBody QuestionCreateDTO questionCreateDTO,
                                                         @RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage language) {
        ApiResponse<?> apiResponse = questionService.createQuestion(questionCreateDTO, language);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PutMapping("/update/question/{id}")
    public ResponseEntity<ApiResponse<?>> updateQuestion(@Valid @RequestBody QuestionCreateDTO questionCreateDTO,@PathVariable("id") Long id,
                                                         @RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage language) {
        ApiResponse<?> apiResponse = questionService.updateQuestion(questionCreateDTO,id, language);
        return ResponseEntity.ok(apiResponse);
    }
}

