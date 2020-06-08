package com.example.emag.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDtoWithSpecDto {

  private ProductDto productDto;

  private List<SpecificationDto> specificationDtoList;
}
