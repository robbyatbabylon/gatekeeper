package com.babylonhealth.bamstemplate.data.repository;

import com.babylonhealth.bamstemplate.data.entity.Gate;
import com.babylonhealth.bamstemplate.data.entity.Zipcodes;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZipcodesRepository extends CrudRepository<Zipcodes, UUID> {
  List<Zipcodes> findByConsumerNetworkUuidIn(List<UUID> consumerNetworkUuids);

  List<Zipcodes> findByConsumerNetworkUuid(UUID consumerNetworkUuid);
}
