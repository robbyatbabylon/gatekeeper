package com.babylonhealth.gatekeeper.data.repository;

import com.babylonhealth.gatekeeper.data.entity.Zipcodes;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZipcodesRepository extends CrudRepository<Zipcodes, UUID> {
  List<Zipcodes> findByConsumerNetworkUuidIn(List<UUID> consumerNetworkUuids);

  List<Zipcodes> findByConsumerNetworkUuid(UUID consumerNetworkUuid);
}
