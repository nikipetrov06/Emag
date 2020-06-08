package com.example.emag.controller;

import com.example.emag.dto.EditProductDto;
import com.example.emag.dto.ProductDto;
import com.example.emag.dto.ProductDtoWithSpecDto;
import com.example.emag.dto.ProductWithSpecsDto;
import com.example.emag.entity.User;
import com.example.emag.services.AdminService;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import static com.example.emag.utils.Constants.SESSION_KEY_LOGGED_USER;

@RestController
public class AdminController {

  @Autowired
  private AdminService adminService;

  @SneakyThrows
  @PostMapping("/admin/products")
  public ProductDtoWithSpecDto addProduct(@Valid @RequestBody ProductWithSpecsDto productDto,
      HttpSession session) {
    User user = (User) session.getAttribute(SESSION_KEY_LOGGED_USER);
    return adminService.addProduct(productDto, user);
  }

  @SneakyThrows
  @DeleteMapping("/admin/products/{productId}")
  public ProductDto removeProduct(@PathVariable(name = "productId") long productId,
      HttpSession session) {
    User user = (User) session.getAttribute(SESSION_KEY_LOGGED_USER);
    return adminService.removeProduct(productId, user);
  }

  @SneakyThrows
  @PutMapping("admin/products")
  public ProductDto editProduct(@Valid @RequestBody EditProductDto editProductDto,
      HttpSession session) {
    User user = (User) session.getAttribute(SESSION_KEY_LOGGED_USER);
    return adminService.editProduct(editProductDto, user);
  }
}
