package com.example.emag.entity;

import com.example.emag.dto.RegisterUserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "first_name", length = 45, nullable = false)
  private String firstName;

  @Column(name = "last_name", length = 45, nullable = false)
  private String lastName;

  @JsonIgnore
  @Column(name = "password", length = 225, nullable = false)
  private String password;

  @Column(name = "email", length = 45, nullable = false)
  private String email;

  @Column(name = "is_admin")
  private Boolean admin = false;

  @Column(name = "subscribed")
  private Boolean subscribed = false;

  @OneToMany(
      mappedBy = "user",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private List<Address> addressList;

  @OneToMany(
      mappedBy = "user",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private List<Order> orders;

  @OneToMany(
      mappedBy = "user",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private List<Review> reviews;

  @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinTable(
      name = "users_have_favourite_products",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "product_id")
  )
  private List<Product> favouriteProducts;

  public User(RegisterUserDto dto) {
    setFirstName(dto.getFirstName());
    setLastName(dto.getLastName());
    setPassword(dto.getPassword());
    setEmail(dto.getEmail());
    setSubscribed(dto.isSubscribed());
  }

  public User(Long id, String firstName, String lastName, String password, String email, Boolean admin, Boolean subscribed) {
    this(id, firstName, lastName, password, email, admin, subscribed,
        new ArrayList<>(), new ArrayList<>(),
        new ArrayList<>(), new ArrayList<>());
  }
}
