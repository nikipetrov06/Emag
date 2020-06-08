package com.example.emag.controller;

import com.example.emag.dto.AddReviewDto;
import com.example.emag.dto.GetProductReviewDto;
import com.example.emag.dto.GetUserReviewDto;
import com.example.emag.entity.User;
import com.example.emag.services.ReviewService;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import static com.example.emag.utils.Constants.SESSION_KEY_LOGGED_USER;

@RestController
public class ReviewController {

  @Autowired
  private ReviewService reviewService;

  @SneakyThrows
  @PostMapping("/products/{productId}/reviews")
  public GetProductReviewDto addReviewToProduct(@PathVariable(name = "productId") long productId,
      @Valid @RequestBody AddReviewDto addReviewDto,
      HttpSession session) {
    User user = (User) session.getAttribute(SESSION_KEY_LOGGED_USER);
    return reviewService.addReviewToProduct(productId, addReviewDto, user);
  }

  @SneakyThrows
  @GetMapping("/products/{productId}/reviews")
  public List<GetProductReviewDto> getAllReviewsForProduct(@PathVariable(name = "productId") long productId) {
    return reviewService.getAllReviewsForProduct(productId);
  }

  @SneakyThrows
  @GetMapping("/users/reviews")
  public List<GetUserReviewDto> getAllReviewsForUser(HttpSession session) {
    User user = (User) session.getAttribute(SESSION_KEY_LOGGED_USER);
    return reviewService.getAllReviewsForUser(user);
  }
}
