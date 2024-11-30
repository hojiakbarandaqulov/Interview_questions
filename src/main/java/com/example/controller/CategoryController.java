//package com.example.controller;
//
//import com.example.dto.ApiResponse;
//import com.example.dto.category.CategoryCreateDTO;
//import com.example.dto.category.CategoryResponseDTO;
//import com.example.service.CategoryService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
////@EnableMethodSecurity(prePostEnabled = true)
//@Slf4j
//@RestController
//@RequestMapping("/api/v1/category")
//public class CategoryController {
//    private final CategoryService categoryService;
//
//    public CategoryController(CategoryService categoryService) {
//        this.categoryService = categoryService;
//    }
//
//    @PostMapping("/create")
//    public ResponseEntity<ApiResponse<CategoryResponseDTO>> create(@RequestBody CategoryCreateDTO category) {
//        ApiResponse<CategoryResponseDTO> apiResponse=categoryService.create(category);
//        return ResponseEntity.ok(apiResponse);
//    }
//}
