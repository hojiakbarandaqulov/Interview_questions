package com.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@EnableMethodSecurity(prePostEnabled = true)
@Slf4j
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

   /* @PostMapping("/create")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> create(@RequestBody CategoryCreateDTO category) {
        ApiResponse<CategoryResponseDTO> apiResponse=categoryService.create(category);
        return ResponseEntity.ok(apiResponse);
    }*/
}
