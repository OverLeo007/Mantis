package ru.paskal.MantisManager.models.login;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterRequest {
  private String username;
  private String email;
  private String password;
}
