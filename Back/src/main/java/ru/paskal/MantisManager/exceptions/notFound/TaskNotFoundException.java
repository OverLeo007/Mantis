package ru.paskal.MantisManager.exceptions.notFound;

public class TaskNotFoundException extends ModelNotFoundException {

    public static final String entityType = "Task";

    public TaskNotFoundException(String msg) {
        super(entityType, msg);
    }
    public TaskNotFoundException(Integer id) {
        super(entityType, String.format("Task with id=%d not found", id));
    }

}