package com.example.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MantenimientoDTO {
    private Long idMantenimiento;
    private Long idVehiculo;
    private String placaVehiculo;
    private String tipoServicio;
    private LocalDate fechaServicio;
    private BigDecimal costo;
    private String notas;
    private Long idUsuario;
    private String nombreUsuario;
    
    public MantenimientoDTO() {
    }
    
    public MantenimientoDTO(Long idMantenimiento, Long idVehiculo, String placaVehiculo, 
                           String tipoServicio, LocalDate fechaServicio, BigDecimal costo, 
                           String notas, Long idUsuario, String nombreUsuario) {
        this.idMantenimiento = idMantenimiento;
        this.idVehiculo = idVehiculo;
        this.placaVehiculo = placaVehiculo;
        this.tipoServicio = tipoServicio;
        this.fechaServicio = fechaServicio;
        this.costo = costo;
        this.notas = notas;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
    }
    
    // Getters and Setters
    public Long getIdMantenimiento() {
        return idMantenimiento;
    }
    
    public void setIdMantenimiento(Long idMantenimiento) {
        this.idMantenimiento = idMantenimiento;
    }
    
    public Long getIdVehiculo() {
        return idVehiculo;
    }
    
    public void setIdVehiculo(Long idVehiculo) {
        this.idVehiculo = idVehiculo;
    }
    
    public String getPlacaVehiculo() {
        return placaVehiculo;
    }
    
    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
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
    
    public Long getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}

