package com.example.emag.dto;

import java.math.BigDecimal;
import javax.validation.constraints.Min;
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
public class EditProductDto {

  @NotNull
  private long id;

  @NotBlank
  private String name;

  @NotBlank
  private String description;

  @NotNull
  @Min(0)
  private BigDecimal price;

  @NotNull
  @Min(0)
  private int discount;

  @NotNull
  @Min(0)
  private int stock;
}
