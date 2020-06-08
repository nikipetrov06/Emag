package com.example.emag.utils;

import com.example.emag.dto.ProductDto;
import com.example.emag.dto.ProductWithQuantityDto;
import com.example.emag.dto.ProductsWithPriceDto;
import com.example.emag.entity.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TransformationUtil {

  public static ProductsWithPriceDto transformMap(Map<Product, Integer> products) {
    BigDecimal price = BigDecimal.ZERO;
    List<ProductWithQuantityDto> productsWithQuantityDto = new ArrayList<>();
    for (Map.Entry<Product, Integer> entry : products.entrySet()) {
      Product product = entry.getKey();
      ProductWithQuantityDto productWithQuantityDto = new ProductWithQuantityDto();
      ProductDto productDto = new ProductDto(product);
      productWithQuantityDto.setProduct(productDto);
      productWithQuantityDto.setQuantity(entry.getValue());
      productsWithQuantityDto.add(productWithQuantityDto);
      BigDecimal productPrice = product.getPrice();
      BigDecimal productQuantity = BigDecimal.valueOf(productWithQuantityDto.getQuantity());
      BigDecimal bigDecimalOne = BigDecimal.valueOf(1);
      BigDecimal percent = BigDecimal.valueOf(product.getDiscount() / 100);
      price = price.add(productPrice
          .multiply((bigDecimalOne.subtract(percent))))
          .multiply(productQuantity);
    }
    return new ProductsWithPriceDto(productsWithQuantityDto, price);
  }

}
