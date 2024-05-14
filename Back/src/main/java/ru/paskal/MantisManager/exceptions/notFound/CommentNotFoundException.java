package ru.paskal.MantisManager.exceptions.notFound;

public class CommentNotFoundException extends ModelNotFoundException {

    public static final String entityType = "Comment";

    public CommentNotFoundException(String msg) {
        super(entityType, msg);
    }

    public CommentNotFoundException(int id) {
        super(entityType, id);
    }
}
