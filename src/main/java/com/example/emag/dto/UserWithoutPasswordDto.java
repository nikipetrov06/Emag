package com.example.emag.dto;

import com.example.emag.entity.User;
import com.example.emag.utils.Constants;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserWithoutPasswordDto {

  private long id;
  @NotNull
  @Pattern(regexp = Constants.FIRST_NAME, message = "Invalid first name !")
  private String firstName;

  @NotNull
  @Pattern(regexp = Constants.LAST_NAME, message = "Invalid last name!")
  private String lastName;

  @NotNull
  @Pattern(regexp = Constants.EMAIL, message = "Invalid e-mail address")
  private String email;

  private boolean subscribed;

  public UserWithoutPasswordDto(User user) {
    setId(user.getId());
    setFirstName(user.getFirstName());
    setLastName(user.getLastName());
    setEmail(user.getEmail());
    setSubscribed(user.getSubscribed());
  }
}
