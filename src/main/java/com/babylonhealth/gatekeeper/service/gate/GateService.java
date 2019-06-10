package com.babylonhealth.gatekeeper.service.gate;

import com.babylonhealth.gatekeeper.data.entity.Gate;
import com.babylonhealth.gatekeeper.data.entity.GateValidations;
import com.babylonhealth.gatekeeper.data.repository.GateRepository;
import com.babylonhealth.gatekeeper.data.repository.GateValidationsRepository;
import com.babylonhealth.gatekeeper.security.UserIdProvider;
import com.babylonhealth.gatekeeper.service.consumerNetwork.ConsumerNetwork;
import com.babylonhealth.gatekeeper.service.consumerNetwork.ConsumerNetworkService;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class GateService {
  private final GateRepository gateRepository;
  private final GateValidationsRepository gateValidationsRepository;
  private final ConsumerNetworkService consumerNetworkService;
  private final UserIdProvider userIdProvider;

  public List<Gate> getGates() {
    List<UUID> consumerNetworkUuids =
        Arrays.stream(consumerNetworkService.fetchConsumerNetworks())
            .map(ConsumerNetwork::getUuid)
            .collect(Collectors.toList());

    return gateRepository.findByConsumerNetworkUuidIn(consumerNetworkUuids);
  }

  public List<Gate> getUnvalidatedGates() {
    UUID userId = getUserID();

    List<UUID> consumerNetworkUuids =
            Arrays.stream(consumerNetworkService.fetchConsumerNetworks())
                    .map(ConsumerNetwork::getUuid)
                    .collect(Collectors.toList());

    return gateRepository.findUnvalidatedGates(userId, consumerNetworkUuids);
  }

  public List<GateValidations> getGateValidations() {
    UUID userId = getUserID();

    return gateValidationsRepository.findByUserId(userId);
  }

  private UUID getUserID() {
    return userIdProvider
        .getUserId()
        .orElseThrow(() -> new AccessDeniedException("Must be authenticated"));
  }
}
