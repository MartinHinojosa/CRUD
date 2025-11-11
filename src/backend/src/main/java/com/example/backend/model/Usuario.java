package com.example.backend.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(nullable = false, unique = true, length = 100)
    private String correo;
    
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;
    
    @Column(length = 50)
    private String rol = "empleado";
    
    @Column(nullable = false)
    private Boolean activo = true;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vehiculo> vehiculos;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mantenimiento> mantenimientos;
    
    // Constructors
    public Usuario() {
    }
    
    public Usuario(String nombre, String correo, String passwordHash, String rol) {
        this.nombre = nombre;
        this.correo = correo;
        this.passwordHash = passwordHash;
        this.rol = rol;
        this.activo = true;
    }
    
    // Getters and Setters
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
    
    public String getPasswordHash() {
        return passwordHash;
    }
    
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
    public String getRol() {
        return rol;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }
    
    public Boolean getActivo() {
        return activo;
    }
    
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }
    
    public void setVehiculos(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }
    
    public List<Mantenimiento> getMantenimientos() {
        return mantenimientos;
    }
    
    public void setMantenimientos(List<Mantenimiento> mantenimientos) {
        this.mantenimientos = mantenimientos;
    }
}

