package com.babylonhealth.gatekeeper.service.zipCode;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sponsors {

  private String sponsorCode;

  List<ZipCode> zipCodes;
}
