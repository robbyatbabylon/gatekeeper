package com.babylonhealth.gatekeeper.data.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class GateValidations {
  @Id private int id;

  @ManyToOne
  @JoinColumn(name = "gate_id", nullable = false)
  private Gate gate;

  @Column private UUID userId;

  @Column boolean validated;
}
