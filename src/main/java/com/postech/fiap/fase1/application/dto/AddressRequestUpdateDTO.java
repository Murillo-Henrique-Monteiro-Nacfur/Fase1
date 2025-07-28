package com.postech.fiap.fase1.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AddressRequestUpdateDTO {
    @NotBlank
    private String street;
    @NotBlank
    private String number;
    @NotBlank
    private String neighborhood;
    @NotBlank
    private String city;
    @NotBlank
    @Size(min = 2, max = 2, message = "State must be 2 characters long")
    private String state;
    @NotBlank
    private String country;
    @NotBlank
    private String postalCode;
}
