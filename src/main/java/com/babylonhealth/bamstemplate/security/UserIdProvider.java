package com.babylonhealth.bamstemplate.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.babylonhealth.bamsspring.security.kong.KongAuthentication;
import com.babylonhealth.bamsspring.security.kong.KongAuthenticationHolder;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserIdProvider {
  private static final String USER_ID_CLAIM = "https://babylonhealth.com/user";

  private final KongAuthenticationHolder kongAuthenticationHolder;

  public Optional<UUID> getUserId() {
    return kongAuthenticationHolder.tryGet().flatMap(this::getUserId);
  }

  private Optional<UUID> getUserId(KongAuthentication kongAuthentication) {
    try {
      DecodedJWT decodedJwt = JWT.decode(kongAuthentication.getCredentials());

      Claim userIdClaim = decodedJwt.getClaim(USER_ID_CLAIM);

      return getUserId(userIdClaim);

    } catch (JWTDecodeException e) {
      throw new IllegalStateException("Expected authenticated request to have a valid JWT", e);
    }
  }

  private Optional<UUID> getUserId(Claim userIdClaim) {
    if (userIdClaim.isNull()) {
      return Optional.empty(); // not a user
    }

    try {
      return Optional.of(UUID.fromString(userIdClaim.asString()));

    } catch (IllegalArgumentException e) {
      throw new IllegalStateException("Unexpected user claim value in JWT", e);
    }
  }
}
