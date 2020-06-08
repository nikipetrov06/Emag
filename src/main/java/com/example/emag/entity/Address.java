package com.example.emag.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @Column(name = "city", nullable = false, length = 45)
  private String city;


  @Column(name = "district", nullable = false, length = 45)
  private String district;


  @Column(name = "street", nullable = false, length = 45)
  private String street;


  @Column(name = "zip", nullable = false, length = 45)
  private String zip;


  @Column(name = "phoneNumber", nullable = false, length = 45)
  private String phoneNumber;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

}


