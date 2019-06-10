package com.babylonhealth.gatekeeper.controller.zipCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

@Value
public class ZipCodeFoundResult {
  @ApiModelProperty(value = "passed", position = 1)
  private boolean passed;
}
