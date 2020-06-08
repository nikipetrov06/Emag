package com.example.emag.repository;

import com.example.emag.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Definition of StatusRepository methods
 */
@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

  Status findStatusById(Long statusId);
}
