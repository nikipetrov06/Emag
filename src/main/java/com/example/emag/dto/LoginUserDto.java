package com.example.emag.dto;

import com.example.emag.utils.Constants;
import javax.validation.constraints.NotBlank;
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
public class LoginUserDto {

  @NotNull
  @Pattern(regexp = Constants.EMAIL, message = "Invalid e-mail address")
  private String email;

  @NotBlank
  private String password;
}
