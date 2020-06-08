package com.example.emag.entity;

import com.example.emag.dto.ProductDto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(name = "name", nullable = false)
  private String name;

  @NotBlank
  @Column(name = "description", nullable = false)
  private String description;

  @NotNull
  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @NotNull
  @Min(0)
  @Column(name = "discount")
  private Integer discount;

  @NotNull
  @Min(0)
  @Column(name = "stock", nullable = false)
  private int stock;

  @Column(name = "reserved_quantity")
  private Integer reservedQuantity = 0;

  @ManyToOne(fetch = FetchType.LAZY)
  private SubCategory subCategory;

  @Column(name = "deleted")
  private Boolean deleted = false;

  @OneToMany(
      mappedBy = "product",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private List<Review> reviews;

  @OneToMany(
      mappedBy = "product",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private List<Specification> specifications;

  @ManyToMany(mappedBy = "favouriteProducts", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  private List<User> userProducts;

  @OneToMany(mappedBy = "product",
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL)
  private List<OrderProduct> orderProducts;

  @OneToMany(
      mappedBy = "product",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private List<Picture> pictures;

  public Product(Long id, String name, String description,
      BigDecimal price, Integer discount,
      int stock, Integer reservedQuantity,
      SubCategory subCategory, Boolean deleted) {
    this(id, name, description,
        price, discount, stock,
        reservedQuantity, subCategory, deleted,
        new ArrayList<>(), new ArrayList<>(),
        new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
  }

  public Product(ProductDto productDto) {
    this.name = productDto.getName();
    this.description = productDto.getDescription();
    this.price = productDto.getPrice();
    this.discount = productDto.getDiscount();
    this.stock = productDto.getStock();
    this.deleted = productDto.getDeleted();
  }
}
