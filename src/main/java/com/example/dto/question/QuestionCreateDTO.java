package com.example.dto.question;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionCreateDTO {
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

    @NotBlank(message = "superAdminUsername required")
    private String superAdminUsername;

    @NotBlank(message = "content required")
    private  String  content;


}
