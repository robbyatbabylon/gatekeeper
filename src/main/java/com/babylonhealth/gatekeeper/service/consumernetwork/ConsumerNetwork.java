package com.babylonhealth.gatekeeper.service.consumernetwork;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.Data;

@Data
public class ConsumerNetwork {
  @JsonProperty("id")
  private int id;

  @JsonProperty("uuid")
  private UUID uuid;

  @JsonProperty("name")
  private String name;
}
