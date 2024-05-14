package ru.paskal.MantisManager.exceptions.notUpdated;

public class CommentNotUpdatedException extends ModelNotUpdatedException {

    public static final String entityType = "Comment";

    public CommentNotUpdatedException(String msg) {
        super(entityType, msg);
    }
}
