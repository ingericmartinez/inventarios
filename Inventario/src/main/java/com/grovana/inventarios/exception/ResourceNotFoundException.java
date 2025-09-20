package com.grovana.inventarios.exception;

import java.util.Collections;
import java.util.List;

public class ResourceNotFoundException extends RuntimeException {
    private final List<String> detalles;

    public ResourceNotFoundException(String message) {
        super(message);
        this.detalles = Collections.emptyList();
    }

    public ResourceNotFoundException(String message, List<String> detalles) {
        super(message);
        this.detalles = detalles;
    }

    public List<String> getDetalles() { return detalles; }
}
