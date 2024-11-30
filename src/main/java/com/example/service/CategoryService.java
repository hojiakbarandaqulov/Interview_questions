//package com.example.service;
//
//import com.example.config.CustomMapperConfig;
//import com.example.dto.ApiResponse;
//import com.example.dto.category.CategoryCreateDTO;
//import com.example.dto.category.CategoryResponseDTO;
//import com.example.entity.CategoryEntity;
//import com.example.exp.AppBadException;
//import com.example.repository.CategoryRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Slf4j
//@Service
//public class CategoryService {
//    private final CategoryRepository categoryRepository;
//    private static final ModelMapper modelMapper = CustomMapperConfig.customModelMapper();
//
//    public CategoryService(CategoryRepository categoryRepository) {
//        this.categoryRepository = categoryRepository;
//    }
//
//    public ApiResponse<CategoryResponseDTO> create(CategoryCreateDTO categoryCreateDTO) {
//        Optional<CategoryEntity> optional = categoryRepository.findByName(categoryCreateDTO.getName());
//        if (optional.isPresent()) {
//            log.info("Category already exists");
//            throw new AppBadException("Category already exists");
//        }
//        CategoryResponseDTO response = modelMapper.map(optional, CategoryResponseDTO.class);
//        return ApiResponse.ok(List.of(response));
//    }
//
//    public CategoryEntity ToDto(CategoryResponseDTO categoryResponseDTO) {
//        CategoryEntity categoryEntity = new CategoryEntity();
//        categoryEntity.setId(categoryResponseDTO.getId());
////        categoryEntity.setName(categoryResponseDTO.getName());
//        categoryEntity.setCreatedDate(categoryResponseDTO.getCreatedDate());
//        return categoryEntity;
//    }
//
//}
