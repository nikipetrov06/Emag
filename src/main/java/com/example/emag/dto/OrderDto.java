package com.example.emag.dto;

import com.example.emag.entity.PaymentType;
import com.example.emag.entity.Status;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDto {

  private Long id;

  private BigDecimal totalPrice;

  private LocalDate date;

  private PaymentType paymentType;

  private Status status;
}
