package com.example.emag.controller;

import com.example.emag.dto.AddressDto;
import com.example.emag.dto.LoginUserDto;
import com.example.emag.dto.OrderDto;
import com.example.emag.dto.OrderWithProductsDto;
import com.example.emag.dto.ProductDto;
import com.example.emag.dto.RegisterUserDto;
import com.example.emag.dto.UserPasswordDto;
import com.example.emag.dto.UserWithoutPasswordDto;
import com.example.emag.entity.Product;
import com.example.emag.entity.User;
import com.example.emag.services.UserService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  public static final String SESSION_KEY_LOGGED_USER = "logged_user";

  @Autowired
  UserService userService;

  @SneakyThrows
  @PostMapping("/users")
  public UserWithoutPasswordDto register(@Valid @RequestBody RegisterUserDto userDto,
      HttpSession session) {
    User user = userService.registerUser(userDto);
    session.setAttribute(SESSION_KEY_LOGGED_USER, user);
    return new UserWithoutPasswordDto(user);
  }

  @SneakyThrows
  @PostMapping("/users/login")
  public UserWithoutPasswordDto login(@Valid @RequestBody LoginUserDto userDto,
      HttpSession session) {
    User user = userService.loginUser(userDto);
    session.setAttribute(SESSION_KEY_LOGGED_USER, user);
    return new UserWithoutPasswordDto(user);
  }

  @SneakyThrows
  @PostMapping("/users/logout")
  public void logout(HttpSession session) {
    if (session.getAttribute("cart") != null) {
      Map<Product, Integer> products = (Map<Product, Integer>) session.getAttribute("cart");
      userService.logoutUser(products);
    }
    session.invalidate();
  }

  @SneakyThrows
  @PutMapping("/users/subscription")
  public UserWithoutPasswordDto unsubscribe(HttpSession session) {
    User user = (User) session.getAttribute(SESSION_KEY_LOGGED_USER);
    UserWithoutPasswordDto userWithoutPasswordDto = userService.changeSubscription(user);
    session.setAttribute(SESSION_KEY_LOGGED_USER, user);
    return userWithoutPasswordDto;
  }

  @SneakyThrows
  @GetMapping("/users")
  public UserWithoutPasswordDto getInfo(HttpSession session) {
    User user = (User) session.getAttribute(SESSION_KEY_LOGGED_USER);
    return userService.getUserInfo(user);
  }

  @SneakyThrows
  @PutMapping("/users")
  public UserWithoutPasswordDto updateUserInfo(@Valid @RequestBody UserWithoutPasswordDto userDto,
      HttpSession session) {
    User user = (User) session.getAttribute(SESSION_KEY_LOGGED_USER);
    UserWithoutPasswordDto userWithoutPasswordDto = userService.updateUserInfo(userDto, user);
    session.setAttribute(SESSION_KEY_LOGGED_USER, user);
    return userWithoutPasswordDto;
  }

  @SneakyThrows
  @PutMapping("/users/password")
  public UserWithoutPasswordDto userChangePassword(@Valid @RequestBody UserPasswordDto userPasswordDto,
      HttpSession session) {
    User user = (User) session.getAttribute(SESSION_KEY_LOGGED_USER);
    UserWithoutPasswordDto userWithoutPasswordDto = userService.changePassword(userPasswordDto, user);
    session.setAttribute(SESSION_KEY_LOGGED_USER, user);
    return userWithoutPasswordDto;
  }

  @SneakyThrows
  @GetMapping("/users/addresses")
  public List<AddressDto> allAdressesByUserId(HttpSession session) {
    User user = (User) session.getAttribute(SESSION_KEY_LOGGED_USER);
    return userService.getAllAddresses(user);
  }

  @SneakyThrows
  @PostMapping("/users/addresses")
  public AddressDto addAddress(@Valid @RequestBody AddressDto address, HttpSession session) {
    User user = (User) session.getAttribute(SESSION_KEY_LOGGED_USER);
    return userService.addAddress(address, user);
  }

  @SneakyThrows
  @DeleteMapping("/users/addresses/{addressId}")
  public AddressDto deleteAddress(@PathVariable(name = "addressId") long addressId,
      HttpSession session) {
    User user = (User) session.getAttribute(SESSION_KEY_LOGGED_USER);
    return userService.removeAddress(addressId, user);
  }

  @SneakyThrows
  @PutMapping("/users/addresses")
  public AddressDto editAddress(@Valid @RequestBody AddressDto address,
      HttpSession session) {
    User user = (User) session.getAttribute(SESSION_KEY_LOGGED_USER);
    return userService.editAddress(address, user);
  }

  @SneakyThrows
  @GetMapping("/users/orders")
  public List<OrderDto> allOrders(HttpSession session) {
    User user = (User) session.getAttribute(SESSION_KEY_LOGGED_USER);
    return userService.getAllUserOrders(user);
  }

  @SneakyThrows
  @GetMapping("/users/orders/{orderId}")
  public OrderWithProductsDto productsByOrder(@PathVariable(name = "orderId") long orderId,
      HttpSession session) {
    User user = (User) session.getAttribute(SESSION_KEY_LOGGED_USER);
    return userService.getAllProductsByOrder(orderId, user);
  }

  @SneakyThrows
  @GetMapping("/users/favourites")
  public List<ProductDto> getFavouriteProducts(HttpSession session) {
    User user = (User) session.getAttribute(SESSION_KEY_LOGGED_USER);
    return userService.getFavouriteProducts(user);
  }

  @SneakyThrows
  @PostMapping("/users/favourites/{productId}")
  public ProductDto addFavouriteProduct(@PathVariable(name = "productId") long productId,
      HttpSession session) {
    User user = (User) session.getAttribute(SESSION_KEY_LOGGED_USER);
    return userService.addFavouriteProduct(productId, user);
  }

  @SneakyThrows
  @DeleteMapping("/users/favourites/{productId}")
  public ProductDto deleteFavouriteProduct(@PathVariable(name = "productId") long productId,
      HttpSession session) {
    User user = (User) session.getAttribute(SESSION_KEY_LOGGED_USER);
    return userService.deleteProduct(productId, user);
  }
}
