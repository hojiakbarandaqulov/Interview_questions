package com.example.dto.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponseDTO {
    @NotNull
    private Long id;

    @NotNull(message = "name required")
    private String name;

    @NotNull(message = "createdDate required")
    private LocalDateTime createdDate;

}
