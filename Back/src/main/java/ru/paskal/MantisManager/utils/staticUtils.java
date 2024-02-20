package ru.paskal.MantisManager.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class staticUtils {
  public static  <T> ResponseEntity<T> okResponseWrapper(T toWrap) {
    return new ResponseEntity<>(toWrap, HttpStatus.OK);
  }

}
