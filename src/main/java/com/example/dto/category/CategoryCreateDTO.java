package com.example.dto.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryCreateDTO {
    @NotBlank(message = "category name is required")
    private String name;
}
