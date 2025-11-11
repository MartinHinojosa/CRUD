package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VehiculoDTO {
    private Long idVehiculo;
    private String placa;
    private String marca;
    private String modelo;
    @JsonProperty("anio")
    private Integer año;
    private Long idUsuario;
    private String nombrePropietario;
    
    public VehiculoDTO() {
    }
    
    public VehiculoDTO(Long idVehiculo, String placa, String marca, String modelo, Integer año, Long idUsuario, String nombrePropietario) {
        this.idVehiculo = idVehiculo;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.idUsuario = idUsuario;
        this.nombrePropietario = nombrePropietario;
    }
    
    // Getters and Setters
    public Long getIdVehiculo() {
        return idVehiculo;
    }
    
    public void setIdVehiculo(Long idVehiculo) {
        this.idVehiculo = idVehiculo;
    }
    
    public String getPlaca() {
        return placa;
    }
    
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
    public String getMarca() {
        return marca;
    }
    
    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    public String getModelo() {
        return modelo;
    }
    
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    public Integer getAño() {
        return año;
    }
    
    public void setAño(Integer año) {
        this.año = año;
    }
    
    public Long getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getNombrePropietario() {
        return nombrePropietario;
    }
    
    public void setNombrePropietario(String nombrePropietario) {
        this.nombrePropietario = nombrePropietario;
    }
}

