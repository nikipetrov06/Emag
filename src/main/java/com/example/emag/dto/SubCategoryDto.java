package com.example.emag.dto;

import com.example.emag.entity.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubCategoryDto {

  private long id;

  private String name;

  private boolean isHeader;

  public SubCategoryDto(SubCategory subCategory) {
    setId(subCategory.getId());
    setName(subCategory.getName());
    setHeader(subCategory.isHeader());
  }
}
