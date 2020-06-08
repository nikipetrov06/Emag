package com.example.emag.dto;

import com.example.emag.entity.Product;
import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

  private long id;

  @NotBlank
  private String name;

  @NotBlank
  private String description;

  @NotNull
  private BigDecimal price;

  private Integer discount;

  @NotNull
  private Integer stock;

  private Boolean deleted;

  private Long subCategoryId;

  public ProductDto(Product product) {
    setId(product.getId());
    setName(product.getName());
    setDescription(product.getDescription());
    setPrice(product.getPrice());
    setDiscount(product.getDiscount());
    setStock(product.getStock());
    setDeleted(product.getDeleted());
    setSubCategoryId(product.getSubCategory().getId());
  }
}
