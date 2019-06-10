package com.babylonhealth.gatekeeper.service.zipcode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZipCode {
  private String state;

  private String zipCode;
}
