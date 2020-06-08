package com.example.emag.services;

import com.example.emag.dto.AddressDto;
import com.example.emag.dto.LoginUserDto;
import com.example.emag.dto.OrderDto;
import com.example.emag.dto.OrderWithProductsDto;
import com.example.emag.dto.ProductDto;
import com.example.emag.dto.ProductWithQuantityDto;
import com.example.emag.dto.RegisterUserDto;
import com.example.emag.dto.UserPasswordDto;
import com.example.emag.dto.UserWithoutPasswordDto;
import com.example.emag.entity.Address;
import com.example.emag.entity.Order;
import com.example.emag.entity.OrderProduct;
import com.example.emag.entity.Product;
import com.example.emag.entity.User;
import com.example.emag.exceptions.AuthorizationException;
import com.example.emag.exceptions.BadRequestException;
import com.example.emag.exceptions.NotFoundException;
import com.example.emag.repository.AddressRepository;
import com.example.emag.repository.OrderRepository;
import com.example.emag.repository.ProductRepository;
import com.example.emag.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService {

  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ProductRepository productRepository;

  public User registerUser(RegisterUserDto userDto) {
    if (userRepository.existsByEmail(userDto.getEmail())) {
      throw new AuthorizationException("User with same e-mail already exist!");
    }
    User user = new User(userDto);
    user.setPassword(BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt()));
    userRepository.save(user);
    return user;
  }


  public User loginUser(LoginUserDto userDto) {
    User user = userRepository.findUserByEmail(userDto.getEmail());
    if (user == null || userDto.getPassword().isEmpty()) {
      throw new AuthorizationException("Invalid credentials");
    }
    if (!BCrypt.checkpw(userDto.getPassword(), user.getPassword())) {
      throw new BadRequestException("Invalid email or password!");
    }
    return user;
  }

  public void logoutUser(Map<Product, Integer> products) {
    if (products != null && !products.isEmpty()) {
      for (Map.Entry<Product, Integer> entry : products.entrySet()) {
        Product product = entry.getKey();
        int reservedQuantity = entry.getValue();
        product.setStock(product.getStock() + reservedQuantity);
        product.setReservedQuantity(0);
        productRepository.save(product);
      }
    }
  }

  public UserWithoutPasswordDto changeSubscription(User user) {
    checkForLoggedUser(user);
    user.setSubscribed(!user.getSubscribed());
    userRepository.save(user);
    return new UserWithoutPasswordDto(user);
  }

  public UserWithoutPasswordDto getUserInfo(User user) {

    checkForLoggedUser(user);
    return new UserWithoutPasswordDto(user);
  }

  public UserWithoutPasswordDto updateUserInfo(UserWithoutPasswordDto userDto, User user) {
    checkForLoggedUser(user);
    if (userRepository.existsByEmail(userDto.getEmail())) {
      throw new BadRequestException("User with this e-mail already exists");
    }
    user.setFirstName(userDto.getFirstName());
    user.setLastName(userDto.getLastName());
    user.setEmail(userDto.getEmail());
    userRepository.save(user);
    return new UserWithoutPasswordDto(user);
  }

  public UserWithoutPasswordDto changePassword(UserPasswordDto userPasswordDto, User user) {
    checkForLoggedUser(user);
    if (!BCrypt.checkpw(userPasswordDto.getOldPassword(), user.getPassword())) {
      throw new AuthorizationException("The old password does not match to the current password");
    }
    if (!userPasswordDto.getNewPassword().equals(userPasswordDto.getConfirmPassword())) {
      throw new BadRequestException("The new password does not match to confirm password");
    }
    String hashedPassword = BCrypt.hashpw(userPasswordDto.getNewPassword(), BCrypt.gensalt());
    user.setPassword(hashedPassword);
    userRepository.save(user);
    return new UserWithoutPasswordDto(user);
  }

  public List<AddressDto> getAllAddresses(User user) {

    checkForLoggedUser(user);
    List<Address> addresses = addressRepository.findAllByUserId(user.getId());
    if (addresses == null) {
      throw new NotFoundException("There are no addresses found!");
    }
    List<AddressDto> addressesDto = new ArrayList<>();
    for (Address address : addresses) {
      addressesDto.add(new AddressDto(address));
    }
    return addressesDto;
  }

  public AddressDto addAddress(AddressDto address, User user) {

    checkForLoggedUser(user);
    Address newAddress = new Address(null, address.getCity(), address.getDistrict(), address.getStreet(),
        address.getZip(), address.getPhoneNumber(), user);
    newAddress = addressRepository.save(newAddress);
    address.setId(newAddress.getId());
    return address;
  }

  @Transactional
  public AddressDto removeAddress(long addressId, User user) {

    checkForLoggedUser(user);
    Address address = addressRepository.findAddressById(addressId);
    checkForAddressExistence(address);
    if (address.getUser().getId() != user.getId()) {
      throw new AuthorizationException("This address does not belong to this user!");
    }
    addressRepository.deleteAddressById(addressId);
    return new AddressDto(address);
  }

  private void checkForAddressExistence(Address address) {
    if (address == null) {
      throw new NotFoundException("Address not found");
    }
  }

  public AddressDto editAddress(AddressDto address, User user) {

    checkForLoggedUser(user);
    Address currentAddress = addressRepository.findAddressById(address.getId());
    checkForAddressExistence(currentAddress);
    if (currentAddress.getUser().getId() != user.getId()) {
      throw new AuthorizationException("This address does not belong to this user!");
    }
    editAddressInfo(currentAddress, address);
    addressRepository.save(currentAddress);
    return address;
  }

  private void editAddressInfo(Address currentAddress, AddressDto updatedAddress) {
    currentAddress.setCity(updatedAddress.getCity());
    currentAddress.setDistrict(updatedAddress.getDistrict());
    currentAddress.setStreet(updatedAddress.getStreet());
    currentAddress.setZip(updatedAddress.getZip());
    currentAddress.setPhoneNumber(updatedAddress.getPhoneNumber());
  }

  public List<OrderDto> getAllUserOrders(User user) {

    checkForLoggedUser(user);
    List<Order> orders = orderRepository.findAllByUserId(user.getId());
    if (orders.isEmpty()) {
      throw new NotFoundException("No orders found!");
    }
    List<OrderDto> orderDtoList = new ArrayList<>();
    for (Order order : orders) {
      OrderDto orderDto = new OrderDto(order.getId(), order.getTotalPrice(), order.getDate(), order.getPaymentType(), order.getStatus());
      orderDtoList.add(orderDto);
    }
    return orderDtoList;
  }

  public OrderWithProductsDto getAllProductsByOrder(long orderId, User user) {

    checkForLoggedUser(user);
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new NotFoundException("No such order"));
    OrderDto orderDto = new OrderDto(order.getId(), order.getTotalPrice(), order.getDate(), order.getPaymentType(), order.getStatus());
    OrderWithProductsDto orderWithProductsDto = new OrderWithProductsDto();
    orderWithProductsDto.setOrder(orderDto);
    List<ProductWithQuantityDto> productWithQuantityDtoList = new ArrayList<>();
    for (OrderProduct orderProduct : order.getOrderProducts()) {
      Product product = orderProduct.getProduct();
      ProductDto productDto = new ProductDto(product);
      ProductWithQuantityDto productWithQuantityDto = new ProductWithQuantityDto(productDto, orderProduct.getQuantity());
      productWithQuantityDtoList.add(productWithQuantityDto);
    }
    orderWithProductsDto.setProducts(productWithQuantityDtoList);
    return orderWithProductsDto;
  }

  private boolean checkIfOrderExist(List<Order> orders, long orderId) {
    for (Order order : orders) {
      if (order.getId() == orderId) {
        return true;
      }
    }
    return false;
  }

  public List<ProductDto> getFavouriteProducts(User user) {

    checkForLoggedUser(user);
    List<Product> products = productRepository.findUserFavouriteProducts(user.getId());
    if (products.isEmpty()) {
      throw new NotFoundException("No favourite products found!");
    }
    List<ProductDto> productDto = new ArrayList<>();
    for (Product product : products) {
      if (product.getDeleted()) {
        continue;
      }
      productDto.add(new ProductDto(product));
    }
    return productDto;
  }

  @Transactional
  public ProductDto addFavouriteProduct(long productId, User user) {

    checkForLoggedUser(user);
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new NotFoundException("No such Product"));
    if (product.getDeleted()) {
      throw new BadRequestException("The product is not active!");
    }
    List<Product> products = productRepository.findUserFavouriteProducts(user.getId());
    if (products.contains(product)) {
      throw new BadRequestException("Product is already added to favourites!");
    }
    user.getFavouriteProducts().add(product);
    product.getUserProducts().add(user);
    productRepository.save(product);
    return new ProductDto(product);
  }

  public ProductDto deleteProduct(long productId, User user) {

    checkForLoggedUser(user);
    List<Product> products = productRepository.findUserFavouriteProducts(user.getId());
    if (products.isEmpty()) {
      throw new NotFoundException("No favourite products found!");
    }
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new NotFoundException("No such Product"));
    if (!products.contains(product)) {
      throw new BadRequestException("This product is not added to favourite products!");
    }
    user.getFavouriteProducts().remove(product);
    product.getUserProducts().remove(user);
    userRepository.save(user);
    return new ProductDto(product);
  }
}
