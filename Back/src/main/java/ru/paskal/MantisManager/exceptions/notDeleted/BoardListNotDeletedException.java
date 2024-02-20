package ru.paskal.MantisManager.exceptions.notDeleted;

public class BoardListNotDeletedException extends ModelNotDeletedException {

    public static final String entityType = "BoardList";

    public BoardListNotDeletedException(String msg) {
        super(entityType, msg);
    }
}
