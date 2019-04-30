package com.babylonhealth.bamstemplate.controller;

import com.babylonhealth.bamstemplate.data.entity.Zipcodes;
import com.babylonhealth.bamstemplate.service.zipcode.ZipcodeService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ZipcodeController {
  private final ZipcodeService zipcodeService;

  @ApiOperation(value = "Greeting message", response = Zipcodes.class)
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping("v1/zipcodes")
  private List<Zipcodes> getAllGates() {
    return zipcodeService.getZipcodes();
  }
}
