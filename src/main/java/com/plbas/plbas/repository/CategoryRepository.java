package com.plbas.plbas.repository;

import com.plbas.plbas.entity.Category;
import com.plbas.plbas.enums.CategoryDirection;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    Category findByNameAndDirection(String name, CategoryDirection direction);


    boolean existsByName(@NotBlank String name);
}
