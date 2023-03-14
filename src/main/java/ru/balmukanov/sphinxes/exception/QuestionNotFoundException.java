package ru.balmukanov.sphinxes.exception;

import java.io.Serial;

public class QuestionNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -1615824035367803817L;

    public QuestionNotFoundException(String message) {
        super(message);
    }
}
