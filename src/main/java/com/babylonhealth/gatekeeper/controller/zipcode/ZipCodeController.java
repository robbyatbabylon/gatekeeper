package com.babylonhealth.gatekeeper.controller.zipcode;

import com.babylonhealth.gatekeeper.service.zipcode.ZipCode;
import com.babylonhealth.gatekeeper.service.zipcode.ZipCodeFilter;
import com.babylonhealth.gatekeeper.service.zipcode.ZipCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@Api(tags = "Zip Code API", value = "/v1/zipCodes")
public class ZipCodeController {
  private final ZipCodeService zipcodeService;

  @ApiOperation(
      value = "Smart ZipCode filter based on JSON object passed in.",
      response = ZipCode.class,
      responseContainer = "List",
      httpMethod = "GET")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping("v1/zipCodes")
  private List<ZipCode> getZipCodes(@RequestBody ZipCodeFilter zipCodeFilter) {
    return zipcodeService.getZipCodes(zipCodeFilter);
  }

  @ApiOperation(
      value = "Get all ZipCodes for all Sponsors",
      response = ZipCode.class,
      responseContainer = "List",
      httpMethod = "GET")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping("v1/zipCodes/all")
  private List<ZipCode> getAllZipCodes() {
    return zipcodeService.getAllZipCodes();
  }

  @ApiOperation(
      value = "Get all ZipCodes for a specific Sponsor",
      response = ZipCode.class,
      responseContainer = "List",
      httpMethod = "GET")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping("v1/zipCodes/sponsorCode/{sponsorCode}/all")
  private List<ZipCode> getAllZipCodes(@PathVariable("sponsorCode") String sponsorCode) {
    return zipcodeService.getAllZipCodes(sponsorCode);
  }

  @ApiOperation(
      value = "Check to see if a specific ZipCode is in a specific Sponsor",
      response = ZipCodeFoundResult.class,
      httpMethod = "GET")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping("v1/zipCodes/sponsorCode/{sponsorCode}/zipcode/{zipCode}")
  private ZipCodeFoundResult isZipCodeAvailable(
      @PathVariable("sponsorCode") String sponsorCode, @PathVariable("zipCode") String zipCode) {
    return new ZipCodeFoundResult(zipcodeService.isZipCodeInList(sponsorCode, zipCode));
  }

  @ApiOperation(
      value = "Check to see if a specific ZipCode is in any Sponsor",
      response = ZipCodeFoundResult.class,
      httpMethod = "GET")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping("v1/zipCodes/zipcode/{zipCode}")
  private ZipCodeFoundResult isZipCodeAvailable(@PathVariable("zipCode") String zipCode) {
    return new ZipCodeFoundResult(zipcodeService.isZipCodeInList(zipCode));
  }

  @ApiOperation(
      value = "Get all ZipCodes for a specific State for a specific Sponsor",
      response = ZipCode.class,
      responseContainer = "List",
      httpMethod = "GET")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping("v1/zipCodes/sponsorCode/{sponsorCode}/state/{state}")
  private List<ZipCode> getZipCodesForState(
      @PathVariable("sponsorCode") String sponsorCode, @PathVariable("state") String state) {
    return zipcodeService.getZipCodesForState(sponsorCode, state);
  }

  @ApiOperation(
      value = "Get all ZipCodes for a specific State for any Sponsor",
      response = ZipCode.class,
      responseContainer = "List",
      httpMethod = "GET")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping("v1/zipCodes/state/{state}")
  private List<ZipCode> getZipCodesForState(@PathVariable("state") String state) {
    return zipcodeService.getZipCodesForState(state);
  }
}
