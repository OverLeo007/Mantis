package ru.paskal.MantisManager.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.paskal.MantisManager.models.dto.user.UserDtoForLink;
import ru.paskal.MantisManager.models.login.LoginRequest;
import ru.paskal.MantisManager.models.login.LoginResponse;
import ru.paskal.MantisManager.models.login.RegisterRequest;
import ru.paskal.MantisManager.security.user.UserPrincipal;
import ru.paskal.MantisManager.security.user.UserPrincipalService;
import ru.paskal.MantisManager.services.AuthService;
import ru.paskal.MantisManager.utils.EntityMapper;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/auth")
@Profile("with-security")
@Slf4j
public class AuthController {

  private final AuthService authService;
  private final UserPrincipalService userPrincipalService;
  private final EntityMapper em;

  @PostMapping("/login")
  public LoginResponse login(@RequestBody @Validated LoginRequest request) {
    log.info("Try to login: " + request.toString());
    try {
      return authService.login(request);
    } catch (Exception e) {
      throw new BadCredentialsException("Incorrect login or passowrd");
    }
  }

  @PostMapping("/register")
  public LoginResponse register(@RequestBody @Validated RegisterRequest request) {
    return authService.register(request);
  }

  @GetMapping
  public UserDtoForLink isAuth(@AuthenticationPrincipal UserPrincipal principal) {
    var user = userPrincipalService.getUserFromPrincipal(principal);
    return em.mapToUserLink(user);
  }

}
