package ru.paskal.MantisManager.exceptions.notUpdated;

public class BoardListNotUpdatedException extends ModelNotUpdatedException {

    public static final String entityType = "BoardList";

    public BoardListNotUpdatedException(String msg) {
        super(entityType, msg);
    }
}
