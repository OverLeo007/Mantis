package ru.paskal.MantisManager.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import ru.paskal.MantisManager.security.user.UserPrincipal;

@Component
@RequiredArgsConstructor
public class JwtIssuer {

  private final JwtProperties properties;

  public String issue(UserPrincipal principal) {
    System.out.println(properties.getSecretKey());
    return JWT.create()
        .withSubject(String.valueOf(principal.getId()))
        .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
        .withClaim("email", principal.getEmail())
        .withClaim("username", principal.getUsername())
        .withClaim("auth", principal.getAuthorities()
            .stream().map(GrantedAuthority::getAuthority).toList())
        .sign(Algorithm.HMAC256(properties.getSecretKey()));
  }

}
