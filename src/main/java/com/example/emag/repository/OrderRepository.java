package com.example.emag.repository;

import com.example.emag.entity.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Definition of OrderRepository methods
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  List<Order> findAllByUserId(Long userId);
}
