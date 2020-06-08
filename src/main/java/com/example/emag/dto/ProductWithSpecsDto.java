package com.example.emag.dto;

import com.example.emag.entity.Specification;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithSpecsDto {

  @NotNull
  @Valid
  private ProductDto product;

  @NotNull
  private List<Specification> specifications;
}
