package com.babylonhealth.bamstemplate.service.zipcode;

import com.babylonhealth.bamstemplate.data.entity.Zipcodes;
import com.babylonhealth.bamstemplate.data.repository.ZipcodesRepository;
import com.babylonhealth.bamstemplate.service.consumerNetwork.ConsumerNetwork;
import com.babylonhealth.bamstemplate.service.consumerNetwork.ConsumerNetworkService;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ZipcodeService {
  private final ZipcodesRepository zipcodesRepository;
  private final ConsumerNetworkService consumerNetworkService;

  public List<Zipcodes> getZipcodes() {
    List<UUID> consumerNetworkUuids =
        Arrays.stream(consumerNetworkService.fetchConsumerNetworks())
            .map(ConsumerNetwork::getUuid)
            .collect(Collectors.toList());

    return zipcodesRepository.findByConsumerNetworkUuidIn(consumerNetworkUuids);
  }
}
