package ru.paskal.MantisManager.exceptions.notCreated;

public class CommentNotCreatedException extends ModelNotCreatedException {

    public static final String entityType = "Comment";

    public CommentNotCreatedException(String msg) {
        super(entityType, msg);
    }
}
