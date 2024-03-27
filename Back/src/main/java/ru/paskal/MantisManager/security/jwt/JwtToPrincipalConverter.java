package ru.paskal.MantisManager.security.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ru.paskal.MantisManager.security.user.UserPrincipal;

@Component
public class JwtToPrincipalConverter {
  public UserPrincipal convert(DecodedJWT jwt) {
    return UserPrincipal.builder()
        .id(Integer.valueOf(jwt.getSubject()))
        .username(jwt.getClaim("username").asString())
        .email(jwt.getClaim("email").asString())
        .authorities(extractAuth(jwt))
        .build();
  }

  private List<SimpleGrantedAuthority> extractAuth(DecodedJWT jwt) {
    var claim = jwt.getClaim("auth");
    if (claim.isNull() || claim.isMissing()) {
      return List.of();
    }
    return claim.asList(SimpleGrantedAuthority.class);
  }

}
