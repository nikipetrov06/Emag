package com.example.emag.controller;

import com.example.emag.dto.OrderDto;
import com.example.emag.dto.ProductsWithPriceDto;
import com.example.emag.entity.Product;
import com.example.emag.entity.User;
import com.example.emag.services.ShoppingCartService;
import com.example.emag.utils.TransformationUtil;
import java.util.Map;
import javax.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


import static com.example.emag.controller.UserController.SESSION_KEY_LOGGED_USER;

@RestController
public class ShoppingCartController {

  @Autowired
  private ShoppingCartService shoppingCartService;

  @SneakyThrows
  @GetMapping("/users/cart")
  public ProductsWithPriceDto getProductsFromCart(HttpSession session) {
    User user = (User) session.getAttribute(SESSION_KEY_LOGGED_USER);
    Map<Product, Integer> products = (Map<Product, Integer>) session.getAttribute("cart");
    products = shoppingCartService.getProductsFromCart(user, products);
    return TransformationUtil.transformMap(products);
  }

  @SneakyThrows
  @PostMapping("/users/cart/products/{productId}")
  public ProductsWithPriceDto addProductToCart(@PathVariable(name = "productId") long productId,
      HttpSession session) {
    User user = (User) session.getAttribute(SESSION_KEY_LOGGED_USER);
    Map<Product, Integer> products = (Map<Product, Integer>) session.getAttribute("cart");
    products = shoppingCartService.addProductToCart(productId, user, products);
    session.setAttribute("cart", products);
    return TransformationUtil.transformMap(products);
  }

  @SneakyThrows
  @DeleteMapping("/users/cart/products/{productId}")
  public ProductsWithPriceDto removeProductFromCart(@PathVariable(name = "productId") long productId,
      HttpSession session) {
    User user = (User) session.getAttribute(SESSION_KEY_LOGGED_USER);
    Map<Product, Integer> products = (Map<Product, Integer>) session.getAttribute("cart");
    products = shoppingCartService.removeProductFromCart(productId, user, products);
    session.setAttribute("cart", products);
    return TransformationUtil.transformMap(products);
  }

  @SneakyThrows
  @PutMapping("/users/cart/products/{productId}/pieces/{quantity}")
  public ProductsWithPriceDto editProductsInCart(@PathVariable(name = "productId") long productId,
      @PathVariable(name = "quantity") int quantity,
      HttpSession session) {
    User user = (User) session.getAttribute(SESSION_KEY_LOGGED_USER);
    Map<Product, Integer> products = (Map<Product, Integer>) session.getAttribute("cart");
    products = shoppingCartService.editProductsInCart(productId, quantity, user, products);
    session.setAttribute("cart", products);
    return TransformationUtil.transformMap(products);
  }

  @SneakyThrows
  @PostMapping("/users/cart/checkout/{paymentType}")
  public OrderDto checkout(@PathVariable long paymentType,
      HttpSession session) {
    User user = (User) session.getAttribute(SESSION_KEY_LOGGED_USER);
    Map<Product, Integer> products = (Map<Product, Integer>) session.getAttribute("cart");
    OrderDto order = shoppingCartService.checkout(paymentType, user, products);
    session.setAttribute("cart", null);
    return order;
  }
}
