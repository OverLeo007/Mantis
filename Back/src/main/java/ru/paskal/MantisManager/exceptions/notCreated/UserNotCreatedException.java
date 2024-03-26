package ru.paskal.MantisManager.exceptions.notCreated;

public class UserNotCreatedException extends ModelNotCreatedException {

    public static final String entityType = "User";

    public UserNotCreatedException(String msg) {
        super(entityType, msg);
    }

}
