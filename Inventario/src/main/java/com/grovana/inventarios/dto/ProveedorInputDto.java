package com.grovana.inventarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProveedorInputDto {
    @NotBlank(message = "nombre es requerido")
    @Size(max = 150)
    private String nombre;

    @Size(max = 100)
    private String contacto_nombre;

    @NotBlank(message = "contacto_email es requerido")
    @Email
    @Size(max = 100)
    private String contacto_email;

    @Size(max = 50)
    private String contacto_telefono;

    @Size(max = 200)
    private String direccion;

    private Boolean activo = Boolean.TRUE;

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
}
