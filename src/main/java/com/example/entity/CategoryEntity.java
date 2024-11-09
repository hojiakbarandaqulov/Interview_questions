package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class CategoryEntity extends BaseIdentityEntity {

    @Column(name = "name_uz")
    private String nameUz;
    @Column(name = "name_ru")
    private String nameRu;
    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "name", unique = true, nullable = false)
    private String name;


    public CategoryEntity(Long id, String nameUz, String nameRu, String nameEn) {
        super(id);
        this.nameUz = nameUz;
        this.nameRu = nameRu;
        this.nameEn = nameEn;
    }

    public CategoryEntity() {

    }
}
