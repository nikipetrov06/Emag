package com.example.emag.dto;

import com.example.emag.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserForReviewsDto {

  private long id;

  private String firstName;

  private String lastName;

  public UserForReviewsDto(User user) {
    setId(user.getId());
    setFirstName(user.getFirstName());
    setLastName(user.getLastName());
  }
}
