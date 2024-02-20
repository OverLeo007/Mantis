package ru.paskal.MantisManager.exceptions.notDeleted;

public class TaskNotDeletedException extends ModelNotDeletedException {

    public static final String entityType = "Task";

    public TaskNotDeletedException(String msg) {
        super(entityType, msg);
    }
}