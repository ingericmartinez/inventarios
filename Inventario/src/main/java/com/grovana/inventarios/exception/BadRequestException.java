package com.grovana.inventarios.exception;

import java.util.Collections;
import java.util.List;

public class BadRequestException extends RuntimeException {
    private final List<String> detalles;

    public BadRequestException(String message) {
        super(message);
        this.detalles = Collections.emptyList();
    }

    public BadRequestException(String message, List<String> detalles) {
        super(message);
        this.detalles = detalles;
    }

    public List<String> getDetalles() { return detalles; }
}
