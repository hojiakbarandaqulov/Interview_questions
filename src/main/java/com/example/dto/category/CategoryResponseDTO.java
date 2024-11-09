package com.example.dto.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponseDTO {
    private Integer id;
    private String name;
    private LocalDateTime createdDate;
}
