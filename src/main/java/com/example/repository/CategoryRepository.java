//package com.example.repository;
//
//import com.example.entity.CategoryEntity;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.Optional;
//
//public interface CategoryRepository extends JpaRepository<Long, CategoryEntity> {
//    @Query("select c from CategoryEntity c where c.=?1")
//    Optional<CategoryEntity> findByName(String name);
//}
