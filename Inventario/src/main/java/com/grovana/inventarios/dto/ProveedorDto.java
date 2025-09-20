package com.grovana.inventarios.dto;

import java.time.OffsetDateTime;

public class ProveedorDto {
    private Long id;
    private String nombre;
    private String contacto_nombre;
    private String contacto_email;
    private String contacto_telefono;
    private String direccion;
    private Boolean activo;
    private OffsetDateTime creado_en;
    private OffsetDateTime actualizado_en;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getContacto_nombre() { return contacto_nombre; }
    public void setContacto_nombre(String contacto_nombre) { this.contacto_nombre = contacto_nombre; }
    public String getContacto_email() { return contacto_email; }
    public void setContacto_email(String contacto_email) { this.contacto_email = contacto_email; }
    public String getContacto_telefono() { return contacto_telefono; }
    public void setContacto_telefono(String contacto_telefono) { this.contacto_telefono = contacto_telefono; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public OffsetDateTime getCreado_en() { return creado_en; }
    public void setCreado_en(OffsetDateTime creado_en) { this.creado_en = creado_en; }
    public OffsetDateTime getActualizado_en() { return actualizado_en; }
    public void setActualizado_en(OffsetDateTime actualizado_en) { this.actualizado_en = actualizado_en; }
}
