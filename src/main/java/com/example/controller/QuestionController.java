package com.example.controller;

import com.example.dto.ApiResponse;
import com.example.dto.question.QuestionCreateDTO;
import com.example.dto.question.QuestionPaginationDTO;
import com.example.enums.AppLanguage;
import com.example.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@SecurityRequirement(name = "Bearer Authentication")
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

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/pagination")
    public ResponseEntity<ApiResponse<PageImpl<QuestionPaginationDTO>>> pagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        ApiResponse<PageImpl<QuestionPaginationDTO>> apiResponse = questionService.pagination(page - 1, size);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteQuestion(@PathVariable("id")Long id) {
        ApiResponse<Boolean> apiResponse = questionService.deleteQuestion(id);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PutMapping("/change/status/{id}")
    public ResponseEntity<ApiResponse<Boolean>> changeQuestionStatus(@PathVariable("id")Long id) {
        ApiResponse<Boolean> apiResponse = questionService.changeStatus(id);
        return ResponseEntity.ok(apiResponse);
    }
}

