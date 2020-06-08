package com.example.emag.repository;

import com.example.emag.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Definition of ReviewRepository methods
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

  List<Review> findAllByProductId(Long productId);

  List<Review> findAllByUserId(Long userId);
}
