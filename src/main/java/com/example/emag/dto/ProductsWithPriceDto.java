package com.example.emag.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductsWithPriceDto {

  private List<ProductWithQuantityDto> products;

  private BigDecimal totalPrice;
}
