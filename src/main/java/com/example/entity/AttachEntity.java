package com.example.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
@Entity
@Table(name = "attach")
@Getter
@Setter
public class AttachEntity {
    @Id
    @Column(length = 255)
    private String id;

    @Column(name = "orginal_name")
    private String originalName;

    @Column(name = "size")
    private Long size;

    @Column(name = "extension")
    private String extension;

    @Column(name = "path")
    private String path;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}
