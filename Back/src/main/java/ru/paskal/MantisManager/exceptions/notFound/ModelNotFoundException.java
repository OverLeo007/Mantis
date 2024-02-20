package ru.paskal.MantisManager.exceptions.notFound;

public class ModelNotFoundException extends RuntimeException {

  public ModelNotFoundException(String entityType,  int id) {
    super("Entity " + entityType + " with id: " + id + " not found!");
  }

  public ModelNotFoundException(String entityType,  String msg) {
    super("Entity " + entityType + " not found because: " + msg);
  }
}

