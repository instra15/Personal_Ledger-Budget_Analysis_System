package com.plbas.plbas.repository;

import com.plbas.plbas.entity.Category;
import com.plbas.plbas.enums.CategoryDirection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    Category findByNameAndDirection(String name, CategoryDirection direction);


}
