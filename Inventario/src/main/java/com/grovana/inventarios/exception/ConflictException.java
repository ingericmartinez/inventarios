package com.grovana.inventarios.exception;

import java.util.Collections;
import java.util.List;

public class ConflictException extends RuntimeException {
    private final List<String> detalles;

    public ConflictException(String message) {
        super(message);
        this.detalles = Collections.emptyList();
    }

    public ConflictException(String message, List<String> detalles) {
        super(message);
        this.detalles = detalles;
    }

    public List<String> getDetalles() { return detalles; }
}
