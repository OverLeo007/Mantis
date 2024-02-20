package ru.paskal.MantisManager.exceptions.notCreated;

public class BoardNotCreatedException extends ModelNotCreatedException {

  public static final String entityType = "Board";
  public BoardNotCreatedException(String msg) {
    super(entityType, msg);
  }
}


