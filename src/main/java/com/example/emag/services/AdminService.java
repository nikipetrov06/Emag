package com.example.emag.services;

import com.example.emag.dto.EditProductDto;
import com.example.emag.dto.ProductDto;
import com.example.emag.dto.ProductDtoWithSpecDto;
import com.example.emag.dto.ProductWithSpecsDto;
import com.example.emag.dto.SpecDto;
import com.example.emag.dto.SpecificationDto;
import com.example.emag.entity.Product;
import com.example.emag.entity.Spec;
import com.example.emag.entity.Specification;
import com.example.emag.entity.SubCategory;
import com.example.emag.entity.User;
import com.example.emag.exceptions.AuthorizationException;
import com.example.emag.exceptions.BadRequestException;
import com.example.emag.exceptions.NotFoundException;
import com.example.emag.repository.ProductRepository;
import com.example.emag.repository.SpecificationRepository;
import com.example.emag.repository.SubCategoryRepository;
import com.example.emag.repository.UserRepository;
import com.example.emag.utils.SendEmailUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService extends AbstractService {

  private static final String PRODUCT_NAME_PATTERN = "([A-Za-z0-9'\\.\\-\\s\\,]).{5,225}";
  private static final String PRODUCT_DESCRIPTION_PATTERN = "([A-Za-z0-9'\\.\\-\\s\\,]).{10,225}";

  @Autowired
  private SubCategoryRepository subCategoryRepository;

  @Autowired
  private SpecificationRepository specificationRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ProductRepository productRepository;

  private void checkForAdminRights(User user) throws AuthorizationException {
    if (user == null) {
      throw new AuthorizationException();
    }
    if (!user.getAdmin()) {
      throw new AuthorizationException("You need to be admin to perform this!");
    }
  }

  protected void checkForProductExistence(Product product) throws NotFoundException {
    if (product == null) {
      throw new NotFoundException("Product not found");
    }
  }

  public ProductDtoWithSpecDto addProduct(ProductWithSpecsDto productDto,
      User user) {

    checkForAdminRights(user);
    SubCategory subCategory = subCategoryRepository.findSubCategoryById(productDto.getProduct().getSubCategoryId());
    if (subCategory == null) {
      throw new BadRequestException("No such subcategory found!");
    }
    Product product = new Product(productDto.getProduct());
    product.setSubCategory(subCategory);
    List<Specification> specifications = new ArrayList<>();
    product.setSpecifications(specifications);
    for (Specification specification : productDto.getSpecifications()) {
      specification.setProduct(product);
      specifications.add(specification);
      for (Spec spec : specification.getSpecifications()) {
        spec.setSpecification(specification);
      }
    }
    productRepository.save(product);
    ProductDto productDto1 = new ProductDto(product);
    ArrayList<SpecificationDto> specificationDtos = new ArrayList<>();
    for (Specification specification : product.getSpecifications()) {
      SpecificationDto specificationDto = new SpecificationDto(specification.getId(), specification.getTitle(), new ArrayList<>());
      for (Spec spec : specification.getSpecifications()) {
        SpecDto specDto = new SpecDto(spec.getId(), spec.getDescriptionTitle(), spec.getDescription());
        specificationDto.getSpecifications().add(specDto);
      }
      specificationDtos.add(specificationDto);
    }
    return new ProductDtoWithSpecDto(productDto1, specificationDtos);
  }

  public ProductDto removeProduct(long productId,
      User user) {

    checkForAdminRights(user);
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new NotFoundException("No such Product"));
    if (product.getDeleted()) {
      throw new BadRequestException("Product is already removed");
    }
    product.setDeleted(true);
    productRepository.save(product);
    return new ProductDto(product);
  }

  public ProductDto editProduct(EditProductDto editProductDto,
      User user) {

    checkForAdminRights(user);
    Product fetchedProduct = productRepository.findById(editProductDto.getId())
        .orElseThrow(() -> new NotFoundException("No such Product"));
    if (fetchedProduct.getDeleted()) {
      throw new BadRequestException("The product is not active!");
    }
    if (editProductDto.getDiscount() > fetchedProduct.getDiscount()) {
      startMailThread(editProductDto);
    }
    fetchedProduct.setName(editProductDto.getName());
    fetchedProduct.setDescription(editProductDto.getDescription());
    fetchedProduct.setPrice(editProductDto.getPrice());
    fetchedProduct.setDiscount(editProductDto.getDiscount());
    fetchedProduct.setStock(editProductDto.getStock());
    productRepository.save(fetchedProduct);
    return new ProductDto(fetchedProduct);
  }

  private void startMailThread(EditProductDto product) {
    Thread email = new Thread(() -> sendEmailToSubscribedUsers(product.getDiscount(), product));
    email.start();
  }

  @SneakyThrows
  private void sendEmailToSubscribedUsers(Integer discount,
      EditProductDto product) {
    List<User> subscribedUsers = userRepository.findAllBySubscribed(true);
    BigDecimal productPrice = product.getPrice();
    BigDecimal bigDecimalValueOfOne = BigDecimal.valueOf(1);
    BigDecimal percent = BigDecimal.valueOf(product.getDiscount() / 100);
    BigDecimal newPrice = productPrice.multiply(bigDecimalValueOfOne.subtract(percent));
    String subject = "Special offer";
    String body = String.format("Dear Mr./Ms., \n\nWe have a special discount of %d%s for our Product - %s"
        + "\n\nYou can buy it now only for %.2f lv. instead of regular price of %.2f lv."
        + "\n\nThe product is waiting for you"
        + "\n\nYour Emag Team", discount, "%", product.getName(), newPrice, product.getPrice());
    for (User user : subscribedUsers) {
      SendEmailUtil.sendMail(user.getEmail(), subject, body);
    }
  }
}
