package com.example.controller;

import com.example.dto.ApiResponse;
import com.example.dto.question.QuestionCreateDTO;
import com.example.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/question")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/create/question")
    public ResponseEntity<ApiResponse<QuestionCreateDTO>> createQuestion(@Valid @RequestBody QuestionCreateDTO questionCreateDTO) {
        ApiResponse<QuestionCreateDTO> apiResponse =questionService.createQuestion(questionCreateDTO);
        return ResponseEntity.ok(apiResponse);
    }



}
