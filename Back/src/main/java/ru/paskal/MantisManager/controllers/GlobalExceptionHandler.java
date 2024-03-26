package ru.paskal.MantisManager.controllers;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.paskal.MantisManager.exceptions.responses.ErrorResponse;

@ControllerAdvice
// FIXME: заставить работать
public class GlobalExceptionHandler {

  private final Logger log;

  @Autowired
  public GlobalExceptionHandler(Logger log) {
    this.log = log;
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleError(Exception ex) {
    log.warn(ex.getMessage());
    return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
