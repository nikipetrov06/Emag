package com.example.emag.dto;

import com.example.emag.entity.Specification;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithAllDto {

  private ProductDto product;

  private List<Specification> specifications;

  private List<ReviewDto> reviews;
}
