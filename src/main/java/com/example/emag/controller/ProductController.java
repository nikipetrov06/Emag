package com.example.emag.controller;

import com.example.emag.dto.ProductDto;
import com.example.emag.dto.ProductFilteringDto;
import com.example.emag.dto.ProductWithAllDto;
import com.example.emag.services.ProductService;
import java.util.List;
import javax.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

  @Autowired
  private ProductService productService;


  @SneakyThrows
  @GetMapping("/products/{productId}")
  public ProductWithAllDto getProduct(@PathVariable(name = "productId") long productId) {
    return productService.getProduct(productId);
  }

  @SneakyThrows
  @GetMapping("/products/search")
  public List<ProductDto> productsFromSearch(@Valid ProductFilteringDto productFilteringDto) {
    return productService.productsFromSearch(productFilteringDto);
  }

}
