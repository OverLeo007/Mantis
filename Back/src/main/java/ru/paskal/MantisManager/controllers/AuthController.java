package ru.paskal.MantisManager.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.paskal.MantisManager.models.login.LoginRequest;
import ru.paskal.MantisManager.models.login.LoginResponse;
import ru.paskal.MantisManager.models.login.RegisterRequest;
import ru.paskal.MantisManager.services.AuthService;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"})
@RequestMapping("/auth")
@Profile("with-security")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public LoginResponse login(@RequestBody @Validated LoginRequest request) {
    return authService.login(request.getUsername(), request.getPassword());
  }

  @PostMapping("/register")
  public LoginResponse register(@RequestBody @Validated RegisterRequest request) {
    return authService.register(request.getUsername(), request.getPassword(), request.getEmail());
  }

}
