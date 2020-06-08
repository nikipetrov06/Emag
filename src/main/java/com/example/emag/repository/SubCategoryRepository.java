package com.example.emag.repository;

import com.example.emag.entity.SubCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Definition of SubCategoryRepository methods
 */
@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

  List<SubCategory> findAllByCategoryId(Long categoryId);

  SubCategory findSubCategoryById(Long subCategoryId);
}
