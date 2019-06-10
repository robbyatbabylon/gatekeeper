package com.babylonhealth.gatekeeper.data.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Zipcodes {

  @Id
  private int id;

  @Column
  private UUID consumerNetworkUuid;

  @Column
  private String zipcode;
}
