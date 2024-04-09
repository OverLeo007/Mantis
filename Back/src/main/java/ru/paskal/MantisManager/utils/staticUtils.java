package ru.paskal.MantisManager.utils;

import org.slf4j.Logger;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class staticUtils {

  public static <T> ResponseEntity<T> okResponseWrapper(T toWrap) {
    return new ResponseEntity<>(toWrap, HttpStatus.OK);
  }

  public static void logMessage(Logger log, String message, Level level) {
    switch (level) {
      case ERROR:
        log.error(message);
        break;
      case WARN:
        log.warn(message);
        break;
      case INFO:
        log.info(message);
        break;
      case DEBUG:
        log.debug(message);
        break;
      case TRACE:
        log.trace(message);
        break;
    }
  }
}

