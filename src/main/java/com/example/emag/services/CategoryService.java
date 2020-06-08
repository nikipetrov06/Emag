package com.example.emag.services;

import com.example.emag.dto.CategoryDto;
import com.example.emag.dto.SubCategoryDto;
import com.example.emag.entity.Category;
import com.example.emag.entity.SubCategory;
import com.example.emag.exceptions.NotFoundException;
import com.example.emag.repository.CategoryRepository;
import com.example.emag.repository.SubCategoryRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class CategoryService extends AbstractService {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private SubCategoryRepository subCategoryRepository;

  @SneakyThrows
  public List<CategoryDto> categoryList() {
    List<Category> categoryList = categoryRepository.findAll();
    List<CategoryDto> categoryDtos = new ArrayList<>();
    for (Category category : categoryList) {
      CategoryDto categoryDto = new CategoryDto();
      categoryDto.setId(category.getId());
      categoryDto.setName(category.getName());
      categoryDtos.add(categoryDto);
    }
    return categoryDtos;
  }

  @SneakyThrows
  public List<SubCategoryDto> subCategoryList(@PathVariable(name = "categoryId") long categoryId) {
    List<SubCategory> subCategories = subCategoryRepository.findAllByCategoryId(categoryId);
    if (subCategories.isEmpty()) {
      throw new NotFoundException("No such category");
    }
    List<SubCategoryDto> subCategoriesDto = new ArrayList<>();
    for (SubCategory subCategory : subCategories) {
      subCategoriesDto.add((new SubCategoryDto(subCategory)));
    }
    return subCategoriesDto;
  }

}
