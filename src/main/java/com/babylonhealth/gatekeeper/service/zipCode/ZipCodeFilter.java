package com.babylonhealth.gatekeeper.service.zipCode;

import io.swagger.annotations.ApiModelProperty;
import java.util.Optional;
import lombok.Data;

@Data
public class ZipCodeFilter {
  @ApiModelProperty
  private String sponsorCode;

  @ApiModelProperty
  private String zipCode;

  @ApiModelProperty
  private String state;

  public Optional<String> getSponsorCode() {
    return Optional.ofNullable(sponsorCode);
  }

  public Optional<String> getZipCode() {
    return Optional.ofNullable(zipCode);
  }

  public Optional<String> getState() {
    return Optional.ofNullable(state);
  }
}
