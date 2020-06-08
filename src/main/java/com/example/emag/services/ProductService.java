package com.example.emag.services;

import com.example.emag.dto.ProductDto;
import com.example.emag.dto.ProductFilteringDto;
import com.example.emag.dto.ProductWithAllDto;
import com.example.emag.dto.ReviewDto;
import com.example.emag.dto.UserDto;
import com.example.emag.entity.Product;
import com.example.emag.entity.Review;
import com.example.emag.entity.Specification;
import com.example.emag.entity.User;
import com.example.emag.exceptions.BadRequestException;
import com.example.emag.exceptions.NotFoundException;
import com.example.emag.repository.ProductRepository;
import com.example.emag.repository.ReviewRepository;
import com.example.emag.repository.SpecificationRepository;
import com.example.emag.repository.SubCategoryRepository;
import com.example.emag.utils.Constants;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends AbstractService {

  @Autowired
  private SpecificationRepository specificationRepository;

  @Autowired
  private ReviewRepository reviewRepository;

  @Autowired
  private SubCategoryRepository subCategoryRepository;

  @Autowired
  private ProductRepository productRepository;

  protected void checkForProductExistence(Product product) throws NotFoundException {
    if (product == null) {
      throw new NotFoundException("Product not found");
    }
  }

  public ProductWithAllDto getProduct(long productId) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new NotFoundException("No such Product"));
    checkForProductExistence(product);
    if (product.getDeleted()) {
      throw new BadRequestException("The product is not active!");
    }
    ProductDto productDto = new ProductDto(product);
    List<Specification> specifications = specificationRepository.findAllByProductId(productId);
    List<Review> reviews = reviewRepository.findAllByProductId(productId);
    List<ReviewDto> reviewDtoList = new ArrayList<>();
    for (Review review : reviews) {
      User user = review.getUser();
      UserDto userDto = new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
      ReviewDto reviewDto = new ReviewDto(review.getId(), review.getTitle(),
          review.getText(), review.getRating(),
          review.getDate(), userDto);
      reviewDtoList.add(reviewDto);
    }
    ProductWithAllDto productWithAllDto = new ProductWithAllDto(productDto, specifications, reviewDtoList);
    checkForProductExistence(product);
    return productWithAllDto;
  }

  public List<ProductDto> productsFromSearch(ProductFilteringDto productFilteringDto) {

    if (productFilteringDto.getOrderBy() != null
        && (!productFilteringDto.getOrderBy().equals("ASC") && !productFilteringDto.getOrderBy().equals("DESC"))) {
      throw new BadRequestException("Invalid order by field");
    }
    if (productFilteringDto.getSubCategoryId() == null && productFilteringDto.getSearchText() == null) {
      throw new BadRequestException("You cannot search for products without sub category and search text");
    }
    if (productFilteringDto.getColumn() != null && !productFilteringDto.getColumn().equals("price")) {
      throw new BadRequestException("Wrong input for field - column!");
    }
    BigDecimal minPrice = productFilteringDto.getMinPrice();
    BigDecimal maxPrice = productFilteringDto.getMaxPrice();
    String column = productFilteringDto.getColumn();
    minPrice = minPrice == null ? Constants.MIN_PRICE_OF_PRODUCT : minPrice;
    String orderBy = productFilteringDto.getOrderBy();
    maxPrice = maxPrice == null ? Constants.MAX_PRICE_OF_PRODUCT : maxPrice;
    column = column == null ? "id" : column;
    orderBy = orderBy == null ? "ASC" : orderBy;
    List<Product> currentProducts;
    if (productFilteringDto.getSubCategoryId() != null) {
      if (subCategoryRepository.findSubCategoryById(productFilteringDto.getSubCategoryId()) == null) {
        throw new NotFoundException("No such subcategory!");
      }
      currentProducts = productRepository.findAllBySubCategoryIdAndPriceBetweenAndOrderBy(productFilteringDto.getSubCategoryId(),
          minPrice, maxPrice, column, orderBy);
    } else if (productFilteringDto.getSearchText() != null && !productFilteringDto.getSearchText().trim().isEmpty()) {
      currentProducts = productRepository.findByNameContainingAAndPriceBetweenAndOrderBy(productFilteringDto.getSearchText(),
          minPrice, maxPrice, column, orderBy);
    } else {
      throw new BadRequestException("Can't search without good input information");
    }
    List<ProductDto> productDtoList = new ArrayList<>();
    for (Product product : currentProducts) {
      ProductDto productDto = new ProductDto(product);
      productDtoList.add(productDto);
    }
    return productDtoList;
  }
}
