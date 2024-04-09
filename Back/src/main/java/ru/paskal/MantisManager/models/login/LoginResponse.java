package ru.paskal.MantisManager.models.login;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {
  private final String token;
}
