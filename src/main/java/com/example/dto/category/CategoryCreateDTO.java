package com.example.dto.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryCreateDTO {
    @NotBlank(message = "name required")
    private String name;

}
