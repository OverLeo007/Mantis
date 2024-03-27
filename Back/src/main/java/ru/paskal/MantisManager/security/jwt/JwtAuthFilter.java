package ru.paskal.MantisManager.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.paskal.MantisManager.security.user.UserPrincipalAuthToken;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtDecoder jwtDecoder;

  private final JwtToPrincipalConverter converter;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    extractToken(request)
        .map(jwtDecoder::decode)
            .map(converter::convert)
                .map(UserPrincipalAuthToken::new)
                    .ifPresent(auth -> SecurityContextHolder.getContext().setAuthentication(auth));
    filterChain.doFilter(request, response);

  }

  private Optional<String> extractToken(HttpServletRequest request) {
    var token = request.getHeader("Authorization");
    if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
      return Optional.of(token.replaceFirst("Bearer ", ""));
    }
    return Optional.empty();
  }
}
