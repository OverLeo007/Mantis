package ru.paskal.MantisManager.exceptions.notDeleted;

public class CommentNotDeletedException extends ModelNotDeletedException {

    public static final String entityType = "Comment";

    public CommentNotDeletedException(String msg) {
        super(entityType, msg);
    }
}