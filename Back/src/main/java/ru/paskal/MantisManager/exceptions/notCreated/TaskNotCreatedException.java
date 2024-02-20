package ru.paskal.MantisManager.exceptions.notCreated;

public class TaskNotCreatedException extends ModelNotCreatedException {

    public static final String entityType = "Task";

    public TaskNotCreatedException(String msg) {
        super(entityType, msg);
    }
}
