package ru.paskal.MantisManager.exceptions.notFound;

public class BoardNotFoundException extends ModelNotFoundException {

  public static final String entityType = "Board";

  public BoardNotFoundException(int id) {
    super(entityType, id);
  }

  public BoardNotFoundException(String msg) {
    super(entityType, msg);

  }
}
