package com.blogapp.web.exceptions;

public class PostDoesNotExistEXception extends Exception {
    public PostDoesNotExistEXception(String message) {
        super(message);
    }

    public PostDoesNotExistEXception(String message, Throwable cause) {
        super(message, cause);
    }

    public PostDoesNotExistEXception(Throwable cause) {
        super(cause);
    }
}
