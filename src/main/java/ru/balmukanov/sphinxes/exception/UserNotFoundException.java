package ru.balmukanov.sphinxes.exception;

import java.io.Serial;

public class UserNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -5899730546261347359L;

    public UserNotFoundException(String message) {
        super(message);
    }
}
