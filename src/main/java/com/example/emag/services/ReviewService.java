package com.example.emag.services;

import com.example.emag.dto.AddReviewDto;
import com.example.emag.dto.GetProductReviewDto;
import com.example.emag.dto.GetUserReviewDto;
import com.example.emag.entity.Product;
import com.example.emag.entity.Review;
import com.example.emag.entity.User;
import com.example.emag.exceptions.BadRequestException;
import com.example.emag.exceptions.NotFoundException;
import com.example.emag.repository.ProductRepository;
import com.example.emag.repository.ReviewRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService extends AbstractService {

  @Autowired
  private ReviewRepository reviewRepository;

  @Autowired
  private ProductRepository productRepository;

  @SneakyThrows
  public GetProductReviewDto addReviewToProduct(long productId,
      AddReviewDto addReviewDto,
      User user) {

    checkForLoggedUser(user);
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new NotFoundException("No such Product"));
    checkForProductExistence(product);
    if (product.getDeleted()) {
      throw new BadRequestException("The product is not active!");
    }
    Review review = new Review(addReviewDto);
    review.setDate(LocalDate.now());
    review.setProduct(product);
    review.setUser(user);
    reviewRepository.save(review);
    return new GetProductReviewDto(review);
  }

  @SneakyThrows
  public List<GetProductReviewDto> getAllReviewsForProduct(long productId) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new NotFoundException("No such Product"));
    checkForProductExistence(product);
    if (product.getDeleted()) {
      throw new BadRequestException("The product is not active!");
    }
    List<Review> reviews = reviewRepository.findAllByProductId(product.getId());
    List<GetProductReviewDto> responseDto = new ArrayList<>();
    for (Review review : reviews) {
      responseDto.add(new GetProductReviewDto(review));
    }
    return responseDto;
  }

  //get all reviews by user
  @SneakyThrows
  public List<GetUserReviewDto> getAllReviewsForUser(User user) {

    checkForLoggedUser(user);
    List<Review> reviews = reviewRepository.findAllByUserId(user.getId());
    List<GetUserReviewDto> responseDto = new ArrayList<>();
    for (Review review : reviews) {
      responseDto.add(new GetUserReviewDto(review));
    }
    return responseDto;
  }
}
