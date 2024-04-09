package ru.paskal.MantisManager.exceptions.responses;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class ErrorResponse {

  private final String message;

  private final String timestamp;

  public ErrorResponse(Throwable ex) {
    this.message = ex.getMessage();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss - dd.MM.yyyy");
    this.timestamp = LocalDateTime.now().format(formatter);
  }
}
