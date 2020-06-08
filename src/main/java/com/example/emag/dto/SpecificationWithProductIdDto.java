package com.example.emag.dto;

import com.example.emag.entity.Product;
import com.example.emag.entity.Spec;
import java.lang.reflect.Field;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpecificationWithProductIdDto {

  private Product product;

  private String title;

  private List<Spec> specList;

  public boolean checkNull() throws IllegalAccessException {
    for (Field f : getClass().getDeclaredFields()) {
      if (f.get(this) != null) {
        return false;
      }
    }
    return true;
  }
}
