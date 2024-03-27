package ru.paskal.MantisManager.models.login;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
  private final String token;
}
