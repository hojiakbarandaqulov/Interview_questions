package com.example.controller;

import com.example.dto.ApiResponse;
import com.example.dto.category.CategoryCreateDTO;
import com.example.dto.category.CategoryResponseDTO;
import com.example.entity.CategoryEntity;
import com.example.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@EnableMethodSecurity(prePostEnabled = true)
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

//    private CategoryService categoryService;
/*
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> create(@RequestBody CategoryCreateDTO category) {
        ApiResponse<CategoryResponseDTO> apiResponse=categoryService.create();
        return ResponseEntity.ok(apiResponse);
    }*/

}
