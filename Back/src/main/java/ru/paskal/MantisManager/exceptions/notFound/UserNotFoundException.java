package ru.paskal.MantisManager.exceptions.notFound;

public class UserNotFoundException extends ModelNotFoundException{

  public static final String entityType = "User";

  public UserNotFoundException(int id) {
    super(entityType, id);
  }

  public UserNotFoundException(String  msg) {
    super(entityType, msg);
  }

}
