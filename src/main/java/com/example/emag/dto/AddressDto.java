package com.example.emag.dto;

import com.example.emag.entity.Address;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

  private Long id;

  @NotNull
  @Size(min = 5)
  private String city;

  @NotNull
  @Size(min = 5)
  private String district;

  @NotNull
  @Size(min = 5)
  private String street;

  @NotBlank
  @Pattern(regexp = "^[0-9\\-\\+]{4,4}$", message = "Invalid ZIP CODE")
  private String zip;

  @NotNull
  @Size(min = 8)
  @Pattern(regexp = "^[0-9\\-\\+]{6,15}$", message = "Invalid ZIP CODE")
  private String phoneNumber;

  public AddressDto(Address address) {
    setId(address.getId());
    setCity(address.getCity());
    setDistrict(address.getDistrict());
    setStreet(address.getStreet());
    setZip(address.getZip());
    setPhoneNumber(address.getPhoneNumber());
  }
}
