package com.babylonhealth.gatekeeper.data.repository;

import com.babylonhealth.gatekeeper.data.entity.GateValidations;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GateValidationsRepository extends CrudRepository<GateValidations, UUID> {

  List<GateValidations> findByUserId(UUID userId);

  List<GateValidations> findAll();
}
