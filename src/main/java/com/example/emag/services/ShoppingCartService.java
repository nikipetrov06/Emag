package com.example.emag.services;

import com.example.emag.dto.OrderDto;
import com.example.emag.entity.Order;
import com.example.emag.entity.OrderProduct;
import com.example.emag.entity.PaymentType;
import com.example.emag.entity.Product;
import com.example.emag.entity.User;
import com.example.emag.exceptions.BadRequestException;
import com.example.emag.exceptions.NotFoundException;
import com.example.emag.repository.OrderRepository;
import com.example.emag.repository.PaymentTypeRepository;
import com.example.emag.repository.ProductRepository;
import com.example.emag.repository.StatusRepository;
import com.example.emag.utils.TransformationUtil;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShoppingCartService extends AbstractService {

  public static final Long ORDER_STATUS_NEW = 1L;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private PaymentTypeRepository paymentTypeRepository;

  @Autowired
  private StatusRepository statusRepository;

  @SneakyThrows
  public Map<Product, Integer> getProductsFromCart(User user, Map<Product, Integer> products) {
    if (products == null) {
      throw new BadRequestException("Cart is empty");
    }
    checkForLoggedUser(user);
    return products;
  }

  @SneakyThrows
  public Map<Product, Integer> addProductToCart(long productId, User user, Map<Product, Integer> products) {
    checkForLoggedUser(user);
    if (products == null) {
      products = new HashMap<>();
    }
    Product fetchedProduct = productRepository.findById(productId)
        .orElseThrow(() -> new NotFoundException("No such Product"));
    if (fetchedProduct.getDeleted()) {
      throw new BadRequestException("The product is not active!");
    }
    if ((fetchedProduct.getStock() - fetchedProduct.getReservedQuantity()) > 0) {
      if (!products.containsKey(fetchedProduct)) {
        products.put(fetchedProduct, 1);
        fetchedProduct.setReservedQuantity(1);
        productRepository.save(fetchedProduct);
      } else {
        throw new BadRequestException("This product already in cart!");
      }
    } else {
      throw new NotFoundException("The product is not available!");
    }
    return products;
  }

  @SneakyThrows
  public Map<Product, Integer> removeProductFromCart(long productId, User user, Map<Product, Integer> products) {
    if (products == null) {
      throw new BadRequestException("The cart is empty");
    }
    checkForLoggedUser(user);
    Product fetchedProduct = productRepository.findById(productId)
        .orElseThrow(() -> new NotFoundException("No such Product"));
    if (products.containsKey(fetchedProduct)) {
      fetchedProduct.setStock(fetchedProduct.getStock() + products.get(fetchedProduct));
      fetchedProduct.setReservedQuantity(0);
      productRepository.save(fetchedProduct);
      products.remove(fetchedProduct);
    } else {
      throw new NotFoundException("This product is not in cart!");
    }
    return products;
  }

  @SneakyThrows
  public Map<Product, Integer> editProductsInCart(long productId, int quantity, User user, Map<Product, Integer> products) {
    if (products == null) {
      throw new BadRequestException("The cart is empty");
    }
    checkForLoggedUser(user);
    Product fetchedProduct = productRepository.findById(productId)
        .orElseThrow(() -> new NotFoundException("No such Product"));
    if (quantity < 1 || quantity > fetchedProduct.getStock()) {
      throw new BadRequestException("Quantity should be in interval between 1 and " + fetchedProduct.getStock());
    }
    if (products.containsKey(fetchedProduct)) {
      if ((fetchedProduct.getStock() - fetchedProduct.getReservedQuantity()
          + products.get(fetchedProduct)) < quantity) {
        throw new NotFoundException("The quantity is not available on stock");
      } else {
        if (quantity < products.get(fetchedProduct)) {
          int reservedQuantity = products.get(fetchedProduct) - quantity;
          fetchedProduct.setReservedQuantity(reservedQuantity);
          productRepository.save(fetchedProduct);
        } else {
          int reservedQuantity = products.get(fetchedProduct) + (products.get(fetchedProduct) - quantity);
          productRepository.save(fetchedProduct);
        }
        products.put(fetchedProduct, quantity);
      }
    } else {
      throw new NotFoundException("This product is not in cart!");
    }
    return products;
  }

  @SneakyThrows
  public OrderDto checkout(long paymentTypeId, User user, Map<Product, Integer> products) {
    if (products == null) {
      throw new BadRequestException("The cart is empty");
    }
    checkForLoggedUser(user);
    PaymentType paymentType = paymentTypeRepository.findPaymentTypeById(paymentTypeId);
    if (paymentType == null) {
      throw new NotFoundException("Payment type not found!");
    }
    BigDecimal totalPrice = TransformationUtil.transformMap(products).getTotalPrice();
    Order order = new Order(totalPrice, LocalDate.now(), user, paymentType, statusRepository.findStatusById(ORDER_STATUS_NEW));
    for (Map.Entry<Product, Integer> entry : products.entrySet()) {
      Product product = entry.getKey();
      OrderProduct orderProduct = new OrderProduct(order, product, entry.getValue());
      order.getOrderProducts().add(orderProduct);
      product.getOrderProducts().add(orderProduct);
    }
    order = orderRepository.save(order);
    return new OrderDto(order.getId(), order.getTotalPrice(), order.getDate(), order.getPaymentType(), order.getStatus());
  }
}
