package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CategoryService {


  /*  public ApiResponse<CategoryResponseDTO> create(CategoryCreateDTO categoryCreateDTO) {
        CategoryEntity categoryEntity=new CategoryEntity();
        Optional<CategoryEntity> optional = categoryRepository.findByName(categoryCreateDTO.getName());
        if(optional.isPresent()) {
            log.info("Category already exists");
            throw new AppBadException("Category already exists");
        }
        return ApiResponse.ok(200,List.of("fg"),)*/
     /*   categoryEntity.setName(categoryCreateDTO.getName());
        categoryRepository.save(categoryEntity);
        CategoryResponseDTO categoryResponseDTO=new CategoryResponseDTO();
        categoryResponseDTO.setId(categoryEntity.getId());
        categoryResponseDTO.setName(categoryEntity.getName());
        categoryResponseDTO.setCreatedDate(categoryEntity.getCreatedDate());
        return ApiResponse.ok(List.of(categoryCreateDTO));*/
    }
