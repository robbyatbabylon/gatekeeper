package com.babylonhealth.bamstemplate.controller;

import com.babylonhealth.bamstemplate.data.entity.Gate;
import com.babylonhealth.bamstemplate.data.repository.GateRepository;
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
@RequestMapping("/v1/exampleApi")
class ExampleApiController {
  private final GateRepository gateRepository;

  @ApiOperation(value = "Greeting message", response = String.class)
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Gate> getGateByField() {
    return gateRepository.findAll();
  }
}
