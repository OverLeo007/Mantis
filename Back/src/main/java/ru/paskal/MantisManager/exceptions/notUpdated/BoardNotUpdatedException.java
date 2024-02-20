package ru.paskal.MantisManager.exceptions.notUpdated;

public class BoardNotUpdatedException extends ModelNotUpdatedException {

  public static final String entityType = "Board";

  public BoardNotUpdatedException(String msg) {
    super(entityType, msg);
  }
}
