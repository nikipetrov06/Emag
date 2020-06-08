package com.example.emag.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @OneToMany(
      mappedBy = "category",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private List<SubCategory> subCategories;

  public Category(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
