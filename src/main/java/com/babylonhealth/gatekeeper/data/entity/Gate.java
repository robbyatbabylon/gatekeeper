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

  public enum feature {
    registration,
    chatbot,
    healthcheck,
    appointments
  }

  public enum gateType {
    zip,
    idv,
    privacy,
    insurance
  }

  @Id private int id;

  @Column private UUID consumerNetworkUuid;

  @Column
  @Enumerated(EnumType.STRING)
  private feature featureGated;

  @Column
  @Enumerated(EnumType.STRING)
  private gateType gateType;

  @OneToMany(mappedBy = "gate")
  @JsonIgnoreProperties("gate")
  private Set<GateValidations> gateValidations;
}
