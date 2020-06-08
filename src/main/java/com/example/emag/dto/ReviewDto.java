package com.example.emag.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

  private Long id;

  private String title;

  private String text;

  private int rating;

  private LocalDate date;

  private UserDto user;
}
