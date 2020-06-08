package com.example.emag.repository;

import com.example.emag.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Definition of CategoryRepository methods
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
