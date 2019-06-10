package com.babylonhealth.gatekeeper.controller.gates;

import com.babylonhealth.gatekeeper.data.entity.Gate;
import com.babylonhealth.gatekeeper.data.entity.GateValidations;
import com.babylonhealth.gatekeeper.service.consumernetwork.ConsumerNetwork;
import com.babylonhealth.gatekeeper.service.consumernetwork.ConsumerNetworkService;
import com.babylonhealth.gatekeeper.service.gate.GateService;
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
public class GateController {
  private final GateService gateService;
  private final ConsumerNetworkService consumerNetworkService;

  @ApiOperation(value = "Greeting message", response = Gate.class)
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping("v1/gates/all")
  private List<Gate> getAllGates() {
    return gateService.getGates();
  }

  @ApiOperation(value = "Greeting message", response = Gate.class)
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping("v1/validations/all")
  private List<GateValidations> getAllGateValidations() {
    return gateService.getGateValidations();
  }

  @ApiOperation(value = "Greeting message", response = Object.class)
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping("v1/consumerNetworks")
  private ConsumerNetwork[] getConsumerNetworks() {
    return consumerNetworkService.fetchConsumerNetworks();
  }

  @ApiOperation(value = "Greeting message", response = Object.class)
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping("v1/gates/unvalidated")
  private List<Gate> getUnvalidatedGates() {
    return gateService.getUnvalidatedGates();
  }
}
