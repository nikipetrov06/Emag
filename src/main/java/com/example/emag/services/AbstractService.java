package com.example.emag.services;

import com.example.emag.entity.Product;
import com.example.emag.entity.User;
import com.example.emag.exceptions.AuthorizationException;
import com.example.emag.exceptions.NotFoundException;


public class AbstractService {

  protected void checkForProductExistence(Product product) throws NotFoundException {
    if (product == null) {
      throw new NotFoundException("Product not found");
    }
  }

  protected void checkForLoggedUser(User user) throws AuthorizationException {
    if (user == null) {
      throw new AuthorizationException();
    }
  }
}
