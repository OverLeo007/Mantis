package ru.paskal.MantisManager.exceptions.notDeleted;

public class BoardNotDeletedException extends ModelNotDeletedException {

  public static final String entityType = "Board";

  public BoardNotDeletedException(String msg) {
    super(entityType, msg);
  }
}
