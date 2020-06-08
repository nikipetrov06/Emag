package com.example.emag.repository;

import com.example.emag.entity.Address;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Definition of AddressRepository methods
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

  List<Address> findAllByUserId(Long userId);

  Address findAddressById(Long addressId);

  void deleteAddressById(Long addressId);
}
