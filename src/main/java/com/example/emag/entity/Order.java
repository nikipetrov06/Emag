package com.example.emag.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "total_price", nullable = false)
  private BigDecimal totalPrice;

  @Column(name = "date", nullable = false)
  private LocalDate date;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "payment_type_id")
  private PaymentType paymentType;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "status_id")
  private Status status;

  @OneToMany(mappedBy = "order",
      cascade = CascadeType.ALL,
      fetch = FetchType.EAGER)
  private List<OrderProduct> orderProducts;

  public Order(BigDecimal totalPrice, LocalDate date, User user, PaymentType paymentType, Status status) {
    this(null, totalPrice, date, user, paymentType, status, new ArrayList<>());
  }

  public Order(long id, BigDecimal totalPrice, LocalDate date, User user, PaymentType paymentType, Status status) {
    this(id, totalPrice, date, user, paymentType, status, new ArrayList<>());
  }
}
