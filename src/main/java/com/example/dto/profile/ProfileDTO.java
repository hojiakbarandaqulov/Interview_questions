package com.example.dto.profile;

import com.example.entity.AttachEntity;
import com.example.entity.CategoryEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private String id;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String password;
    private ProfileRole role;
    private ProfileStatus status;
    private LocalDateTime createdDate=LocalDateTime.now();
    private String photoId;
    private Long categoryId;
    private Boolean visible=Boolean.TRUE;
}
