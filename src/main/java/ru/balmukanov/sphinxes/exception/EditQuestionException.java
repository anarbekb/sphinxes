package ru.balmukanov.sphinxes.exception;

import java.io.Serial;

public class EditQuestionException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -3589639856837297290L;

    public EditQuestionException(String message) {
        super(message);
    }
}
