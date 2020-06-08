package com.example.emag.dto;

import com.example.emag.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductForReviewsDto {

  private long id;

  private String name;

  public ProductForReviewsDto(Product product) {
    setId(product.getId());
    setName(product.getName());
  }
}
