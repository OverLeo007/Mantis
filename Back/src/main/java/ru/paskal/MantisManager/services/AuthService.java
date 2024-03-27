package ru.paskal.MantisManager.services;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.paskal.MantisManager.models.login.LoginResponse;
import ru.paskal.MantisManager.security.jwt.JwtIssuer;
import ru.paskal.MantisManager.security.user.UserPrincipal;

@Service
@RequiredArgsConstructor
@Profile("with-security")
public class AuthService {

  private final JwtIssuer jwtIssuer;
  private final UserService userService;
  private final AuthenticationManager authenticationManager;

  public LoginResponse login(String username, String password) {
    var auth = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, password)
    );
    SecurityContextHolder.getContext().setAuthentication(auth);
    var principal = (UserPrincipal) auth.getPrincipal();
    var token = jwtIssuer.issue(principal);
    return LoginResponse.builder().token(token).build();
  }

  public LoginResponse register(String username, String password, String email) {
    userService.createNewUser(username, password, email);
    return login(username, password);
  }


}
