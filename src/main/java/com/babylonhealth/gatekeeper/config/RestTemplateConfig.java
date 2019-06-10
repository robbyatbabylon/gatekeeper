package com.babylonhealth.gatekeeper.config;

import com.babylonhealth.bamsspring.services.core.auth.StandardRequestAuthenticationType;
import com.babylonhealth.bamsspring.services.resttemplate.RestTemplateClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

  private final RestTemplateClientBuilder builder;

  @Bean
  RestTemplate coreRubyAuthPropagateRestTemplate() {
    return builder
        .service("core-ruby")
        .authentication(StandardRequestAuthenticationType.PROPAGATE)
        .toBuilder()
        .build();
  }
}
