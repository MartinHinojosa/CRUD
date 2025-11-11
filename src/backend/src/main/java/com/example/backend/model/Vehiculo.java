package com.example.backend.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "vehiculos")
public class Vehiculo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehiculo")
    private Long idVehiculo;
    
    @Column(nullable = false, unique = true, length = 20)
    private String placa;
    
    @Column(nullable = false, length = 50)
    private String marca;
    
    @Column(nullable = false, length = 50)
    private String modelo;
    
    @Column(name = "año")
    private Integer año;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = true)
    private Usuario usuario;
    
    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mantenimiento> mantenimientos;
    
    // Constructors
    public Vehiculo() {
    }
    
    public Vehiculo(String placa, String marca, String modelo, Integer año, Usuario usuario) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.usuario = usuario;
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
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public List<Mantenimiento> getMantenimientos() {
        return mantenimientos;
    }
    
    public void setMantenimientos(List<Mantenimiento> mantenimientos) {
        this.mantenimientos = mantenimientos;
    }
}

