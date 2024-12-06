package com.example.dto.question;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionPaginationDTO {
    @NotBlank(message = "urlKey")
    private String urlKey;

    @NotBlank(message = "questionType  required")
    private String questionType;

    @NotBlank(message = "title required")
    private String title;

    @NotBlank(message = "questionLessonType required")
    private String questionLessonType;

    @NotBlank(message = "contentType required")
    private String contentType;
}
