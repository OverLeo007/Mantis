package ru.paskal.MantisManager.exceptions.notFound;

public class BoardListNotFoundException extends ModelNotFoundException {

  public static final String entityType = "BoardList";

  public BoardListNotFoundException(int id) {
    super(entityType, id);
  }

  public BoardListNotFoundException(String msg) {
    super(entityType, msg);

  }
}
