package com.babylonhealth.gatekeeper.service.consumernetwork;

import com.babylonhealth.gatekeeper.security.UserIdProvider;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class ConsumerNetworkService {

  private final RestTemplate coreRubyAuthPropagateRestTemplate;
  private final UserIdProvider userIdProvider;

  public ConsumerNetworkService(
      @Autowired RestTemplate coreRubyAuthPropagateRestTemplate, UserIdProvider userIdProvider) {
    this.coreRubyAuthPropagateRestTemplate = coreRubyAuthPropagateRestTemplate;
    this.userIdProvider = userIdProvider;
  }

  public ConsumerNetwork[] fetchConsumerNetworks() {
    UUID userId = getUserID();

    return coreRubyAuthPropagateRestTemplate.getForObject(
        "api/v2/patients/{uuid}/consumer_networks", ConsumerNetwork[].class, userId.toString());
  }

  private UUID getUserID() {
    return userIdProvider
        .getUserId()
        .orElseThrow(() -> new AccessDeniedException("Must be authenticated"));
  }
}
