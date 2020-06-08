package com.example.emag.dto;

import java.math.BigDecimal;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilteringDto {

  @Min(0)
  private Long subCategoryId;

  private String column;

  private String searchText;

  @Min(0)
  private BigDecimal minPrice;

  @Min(0)
  private BigDecimal maxPrice;

  private String orderBy;
}
