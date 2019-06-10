package com.babylonhealth.gatekeeper.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Gate {

  public enum Feature {
    registration,
    chatbot,
    healthcheck,
    appointments
  }

  public enum GateType {
    zip,
    idv,
    privacy,
    insurance
  }

  @Id private int id;

  @Column private UUID consumerNetworkUuid;

  @Column
  @Enumerated(EnumType.STRING)
  private Feature featureGated;

  @Column
  @Enumerated(EnumType.STRING)
  private GateType gateType;

  @OneToMany(mappedBy = "gate")
  @JsonIgnoreProperties("gate")
  private Set<GateValidations> gateValidations;
}
