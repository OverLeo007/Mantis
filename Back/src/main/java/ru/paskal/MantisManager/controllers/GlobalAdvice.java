package ru.paskal.MantisManager.controllers;


import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.paskal.MantisManager.exceptions.AccessForbiddenException;
import ru.paskal.MantisManager.exceptions.notCreated.ModelNotCreatedException;
import ru.paskal.MantisManager.exceptions.notDeleted.ModelNotDeletedException;
import ru.paskal.MantisManager.exceptions.notFound.ModelNotFoundException;
import ru.paskal.MantisManager.exceptions.notUpdated.ModelNotUpdatedException;
import ru.paskal.MantisManager.exceptions.responses.ErrorResponse;
import ru.paskal.MantisManager.utils.Pair;

@ControllerAdvice
@RestControllerAdvice
@Slf4j
public class GlobalAdvice {

  private static final Map<Class<? extends Exception>, Pair<HttpStatus, Level>> exceptionMappings = new HashMap<>();

  static {
    exceptionMappings.put(ModelNotCreatedException.class, new Pair<>(HttpStatus.BAD_REQUEST, Level.WARN));
    exceptionMappings.put(ModelNotFoundException.class, new Pair<>(HttpStatus.NOT_FOUND, Level.WARN));
    exceptionMappings.put(ModelNotUpdatedException.class, new Pair<>(HttpStatus.BAD_REQUEST, Level.WARN));
    exceptionMappings.put(ModelNotDeletedException.class, new Pair<>(HttpStatus.BAD_REQUEST, Level.WARN));
    exceptionMappings.put(NoHandlerFoundException.class, new Pair<>(HttpStatus.NOT_FOUND, Level.WARN));
    exceptionMappings.put(BadCredentialsException.class, new Pair<>(HttpStatus.UNAUTHORIZED, Level.WARN));
    exceptionMappings.put(HttpMessageNotReadableException.class, new Pair<>(HttpStatus.BAD_REQUEST, Level.INFO));
    exceptionMappings.put(AccessForbiddenException.class, new Pair<>(HttpStatus.UNAUTHORIZED, Level.WARN));

  }

  @ExceptionHandler(
      {
          ModelNotCreatedException.class,
          ModelNotFoundException.class,
          ModelNotUpdatedException.class,
          ModelNotDeletedException.class,
          NoHandlerFoundException.class,
          BadCredentialsException.class,
          HttpMessageNotReadableException.class,
          AccessForbiddenException.class
      }
  )
  public ResponseEntity<ErrorResponse> handleExceptionByDefault(Exception ex) {
    var mapping = findMappingForException(ex.getClass());
    String message = "Handled by default handler --> " + ex.getMessage();
    switch (mapping.second()) {
      case ERROR -> log.error(message);
      case WARN -> log.warn(message);
      case INFO -> log.info(message);
      case DEBUG -> log.debug(message);
      case TRACE -> log.trace(message);
    }
    return ResponseEntity
        .status(mapping.first())
        .body(new ErrorResponse(ex));
  }


  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception ex) {
    log.error(ex.getMessage());
    ex.printStackTrace();
    return new ResponseEntity<>(new ErrorResponse(ex), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private Pair<HttpStatus, Level> findMappingForException(Class<? extends Exception> exceptionClass) {
    for (Map.Entry<Class<? extends Exception>, Pair<HttpStatus, Level>> entry : exceptionMappings.entrySet()) {
      if (entry.getKey().isAssignableFrom(exceptionClass)) {
        return entry.getValue();
      }
    }
    return new Pair<>(HttpStatus.INTERNAL_SERVER_ERROR, Level.ERROR);
  }

}
