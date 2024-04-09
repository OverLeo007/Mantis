package ru.paskal.MantisManager.exceptions;


public class AccessForbiddenException extends RuntimeException {

  public AccessForbiddenException(String message) {
    super(message);
  }
}
