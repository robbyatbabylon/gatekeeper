package com.babylonhealth.gatekeeper.service.zipCode;

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
