package com.example.emag.dto;

import com.example.emag.entity.Review;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserReviewDto {

  private long id;

  private String title;

  private String text;

  private int rating;

  private LocalDate date;

  private ProductForReviewsDto productForReviewsDto;

  public GetUserReviewDto(Review review) {
    setId(review.getId());
    setTitle(review.getTitle());
    setText(review.getText());
    setRating(review.getRating());
    setDate(review.getDate());
    setProductForReviewsDto(new ProductForReviewsDto(review.getProduct()));
  }
}
