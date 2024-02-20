package ru.paskal.MantisManager.exceptions.notCreated;

public class BoardListNotCreatedException extends ModelNotCreatedException {

    public static final String entityType = "BoardList";

    public BoardListNotCreatedException(String msg) {
        super(entityType, msg);
    }
}




