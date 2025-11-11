package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsuarioDTO {
    @JsonProperty("idUsuario")
    private Long idUsuario;
    
    @JsonProperty("nombre")
    private String nombre;
    
    @JsonProperty("correo")
    private String correo;
    
    @JsonProperty("rol")
    private String rol;
    
    public UsuarioDTO() {}
    
    public UsuarioDTO(Long idUsuario, String nombre, String correo, String rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.rol = rol;
    }
    
    public Long getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getRol() {
        return rol;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }
}

