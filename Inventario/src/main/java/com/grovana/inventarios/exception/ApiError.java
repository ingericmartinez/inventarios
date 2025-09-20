package com.grovana.inventarios.exception;

import java.time.OffsetDateTime;
import java.util.List;

public class ApiError {
    private String codigo;
    private String mensaje;
    private List<String> detalles;
    private OffsetDateTime timestamp;

    public ApiError() {}

    public ApiError(String codigo, String mensaje, List<String> detalles) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.detalles = detalles;
        this.timestamp = OffsetDateTime.now();
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public List<String> getDetalles() { return detalles; }
    public void setDetalles(List<String> detalles) { this.detalles = detalles; }
    public OffsetDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(OffsetDateTime timestamp) { this.timestamp = timestamp; }
}
