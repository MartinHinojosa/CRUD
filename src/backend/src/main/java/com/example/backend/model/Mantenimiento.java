package com.example.backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "mantenimientos")
public class Mantenimiento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mantenimiento")
    private Long idMantenimiento;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private Vehiculo vehiculo;
    
    @Column(name = "tipo_servicio", nullable = false, length = 100)
    private String tipoServicio;
    
    @Column(name = "fecha_servicio", nullable = false)
    private LocalDate fechaServicio;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal costo;
    
    @Column(columnDefinition = "TEXT")
    private String notas;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = true)
    private Usuario usuario;
    
    // Constructors
    public Mantenimiento() {
    }
    
    public Mantenimiento(Vehiculo vehiculo, String tipoServicio, LocalDate fechaServicio, 
                        BigDecimal costo, String notas, Usuario usuario) {
        this.vehiculo = vehiculo;
        this.tipoServicio = tipoServicio;
        this.fechaServicio = fechaServicio;
        this.costo = costo;
        this.notas = notas;
        this.usuario = usuario;
    }
    
    // Getters and Setters
    public Long getIdMantenimiento() {
        return idMantenimiento;
    }
    
    public void setIdMantenimiento(Long idMantenimiento) {
        this.idMantenimiento = idMantenimiento;
    }
    
    public Vehiculo getVehiculo() {
        return vehiculo;
    }
    
    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }
    
    public String getTipoServicio() {
        return tipoServicio;
    }
    
    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }
    
    public LocalDate getFechaServicio() {
        return fechaServicio;
    }
    
    public void setFechaServicio(LocalDate fechaServicio) {
        this.fechaServicio = fechaServicio;
    }
    
    public BigDecimal getCosto() {
        return costo;
    }
    
    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }
    
    public String getNotas() {
        return notas;
    }
    
    public void setNotas(String notas) {
        this.notas = notas;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

