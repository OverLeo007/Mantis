package ru.paskal.MantisManager.exceptions.notUpdated;

public class UserNotUpdatedException extends ModelNotUpdatedException {

    public static final String entityType = "User";

    public UserNotUpdatedException(String msg) {
        super(entityType, msg);
    }
}
