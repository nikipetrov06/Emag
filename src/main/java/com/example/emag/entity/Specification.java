package com.example.emag.entity;

import com.example.emag.dto.SpecificationWithProductIdDto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "specifications")
@Getter
@Setter
@AllArgsConstructor
public class Specification {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(name = "title", length = 45, nullable = false)
  private String title;

  @ManyToOne(fetch = FetchType.LAZY,
      cascade = CascadeType.MERGE)
  private Product product;

  @OneToMany(
      mappedBy = "specification",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private List<Spec> specifications;

  public Specification() {
    this.specifications = new ArrayList<>();
  }


  public Specification(SpecificationWithProductIdDto specification) {
    setTitle(specification.getTitle());
    setSpecifications(specification.getSpecList());
    setProduct(specification.getProduct());
  }
}
