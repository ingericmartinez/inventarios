package com.grovana.inventarios.dto;

import java.time.OffsetDateTime;

public class CategoriaDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private OffsetDateTime creado_en;
    private OffsetDateTime actualizado_en;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public OffsetDateTime getCreado_en() { return creado_en; }
    public void setCreado_en(OffsetDateTime creado_en) { this.creado_en = creado_en; }
    public OffsetDateTime getActualizado_en() { return actualizado_en; }
    public void setActualizado_en(OffsetDateTime actualizado_en) { this.actualizado_en = actualizado_en; }
}
