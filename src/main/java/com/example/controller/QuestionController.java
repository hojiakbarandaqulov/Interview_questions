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
    public ResponseEntity<ApiResponse<QuestionCreateDTO>> createQuestion(@Valid @RequestBody QuestionCreateDTO questionCreateDTO,
                                                                         @RequestParam(defaultValue = "uz") AppLanguage language) {
        ApiResponse<QuestionCreateDTO> apiResponse = questionService.createQuestion(questionCreateDTO, language);
        return ResponseEntity.ok(apiResponse);
    }


}
