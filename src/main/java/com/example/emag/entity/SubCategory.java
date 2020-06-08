package com.example.emag.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sub_categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubCategory {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "is_header", nullable = false)
  private boolean isHeader;


  @ManyToOne(fetch = FetchType.LAZY)
  private Category category;

  @OneToMany(
      mappedBy = "subCategory",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private List<Product> products;

  public SubCategory(Long id, String name, boolean isHeader, Category category) {
    this(id, name, isHeader, category, new ArrayList<>());
  }
}
