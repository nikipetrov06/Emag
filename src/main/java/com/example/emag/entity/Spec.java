package com.example.emag.entity;

import javax.persistence.CascadeType;
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
@Table(name = "specifications_have_descriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Spec {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "description_title", nullable = false)
  private String descriptionTitle;

  @Column(name = "description", nullable = false)
  private String description;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  private Specification specification;

  public Spec(String descriptionTitle, String description) {
    this(null, descriptionTitle, description, new Specification());
  }
}