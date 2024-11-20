package com.example.dto.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryCreateDTO {
    @NotBlank(message = "orderNumber required")
    private Integer orderNumber;
    @NotBlank(message = "nameUz required")
    private String nameUz;
    @NotBlank(message = "nameRu required")
    private String nameRu;
    @NotBlank(message = "nameEn required")
    private String nameEn;
}
