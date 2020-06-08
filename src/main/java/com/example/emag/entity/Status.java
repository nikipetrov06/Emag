package com.example.emag.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "statuses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Status {
  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "status_name", nullable = false)
  private String name;

  public Status(Long id) {
    this(id, null);
  }
}