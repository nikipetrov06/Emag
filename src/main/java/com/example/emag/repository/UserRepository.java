package com.example.emag.repository;

import com.example.emag.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Definition of UserRepository methods
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByEmail(String email);

  User findUserByEmail(String email);

  List<User> findAllBySubscribed(boolean subscribed);
}
