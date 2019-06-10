package com.babylonhealth.gatekeeper.data.repository;

import com.babylonhealth.gatekeeper.data.entity.Gate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GateRepository extends CrudRepository<Gate, UUID> {
  List<Gate> findByConsumerNetworkUuidIn(List<UUID> consumerNetworkUuids);

  List<Gate> findByConsumerNetworkUuid(UUID consumerNetworkUuid);

  List<Gate> findAll();

  List<Gate> findByGateValidations_UserId(UUID userId);

  @Query(
      value =
          "SELECT DISTINCT \n"
              + "\tg.id,\n"
              + "\tg.consumer_network_uuid,\n"
              + "\tg.feature_gated,\n"
              + "\tg.gate_type\n"
              + "FROM\n"
              + "\tgate g\n"
              + "\tFULL OUTER JOIN gate_validations gv\n"
              + "\tON g.id = gv.gate_id\n"
              + "\tWHERE\n"
              + "\t\t(consumer_network_uuid IN ?2)\n"
              + "\t\tAND\t\n"
              + "\t\t(\n"
              + "\t\t\t(user_id <> ?1 OR user_id IS NULL)\n"
              + "\t\t\tOR\n"
              + "\t\t\t(user_id = ?1 AND validated = FALSE)\n"
              + "\t\t)",
      nativeQuery = true)
  List<Gate> findUnvalidatedGates(UUID userId, List<UUID> consumerNetworkId);
}
