package com.example.emag.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddReviewDto {

  @NotBlank
  private String title;

  @NotNull
  @Length(min = 10)
  private String text;

  @NotNull
  @Min(0)
  @Max(5)
  private int rating;
}
