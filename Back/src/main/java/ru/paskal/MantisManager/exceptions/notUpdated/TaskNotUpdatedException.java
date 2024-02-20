package ru.paskal.MantisManager.exceptions.notUpdated;

public class TaskNotUpdatedException extends ModelNotUpdatedException {

    public static final String entityType = "Task";

    public TaskNotUpdatedException(String msg) {
        super(entityType, msg);
    }
}
