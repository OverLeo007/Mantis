package ru.paskal.MantisManager.exceptions.responses;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

  private String message;

  private Timestamp timestamp;

  public ErrorResponse(String message) {
    this.message = message;
    this.timestamp = new Timestamp(System.currentTimeMillis());
  }
}
