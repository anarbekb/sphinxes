package ru.balmukanov.sphinxes.exception;

import java.io.Serial;

public class EstimateException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -3873102164290737622L;

    public EstimateException(String message) {
        super(message);
    }
}
