package ru.paskal.MantisManager.exceptions;

import com.fasterxml.jackson.databind.JsonNode;

public class JsonParsingException extends RuntimeException {

  public JsonParsingException(JsonNode json) {
    super("Error while parsing json: " + json.toString());
  }


}
