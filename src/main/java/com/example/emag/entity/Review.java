package com.example.emag.entity;

import com.example.emag.dto.AddReviewDto;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title", length = 45, nullable = false)
  private String title;

  @Column(name = "text", nullable = false)
  private String text;

  @Column(name = "rating", nullable = false)
  private int rating;

  @Column(name = "date", nullable = false)
  private LocalDate date;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  private Product product;

  public Review(AddReviewDto addReviewDto) {
    this.title = addReviewDto.getTitle();
    this.text = addReviewDto.getText();
    this.rating = addReviewDto.getRating();
  }
}
