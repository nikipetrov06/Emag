package com.example.emag.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderWithProductsDto {

  private OrderDto order;

  private List<ProductWithQuantityDto> products;
}
