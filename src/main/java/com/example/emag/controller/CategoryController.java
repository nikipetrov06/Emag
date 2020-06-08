package com.example.emag.controller;

import com.example.emag.dto.CategoryDto;
import com.example.emag.dto.SubCategoryDto;
import com.example.emag.services.CategoryService;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @SneakyThrows
  @GetMapping("/categories")
  public List<CategoryDto> categoryList() {
    return categoryService.categoryList();
  }

  @SneakyThrows
  @GetMapping("/subcategories/{categoryId}")
  public List<SubCategoryDto> subCategoryList(@PathVariable(name = "categoryId") long categoryId) {
    return categoryService.subCategoryList(categoryId);
  }
}
