package ru.paskal.MantisManager.models.login;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequest {
  private String username;
  private String password;
}
