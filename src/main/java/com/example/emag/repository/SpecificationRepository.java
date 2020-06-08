package com.example.emag.repository;

import com.example.emag.entity.Specification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Definition of SpecificationRepository methods
 */
@Repository
public interface SpecificationRepository extends JpaRepository<Specification, Long> {

  List<Specification> findAllByProductId(Long productId);
}
