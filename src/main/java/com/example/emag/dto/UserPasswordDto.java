package com.example.emag.dto;

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
public class UserPasswordDto {

  @NotNull
  String oldPassword;

  @NotNull
  @Pattern(regexp = Constants.PASSWORD, message = "Invalid password!")
  String newPassword;

  @NotNull
  @Pattern(regexp = Constants.PASSWORD, message = "Invalid password!")
  String confirmPassword;
}
