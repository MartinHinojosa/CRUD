package com.crud.crud.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "cars")
public class Car {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_car")
    private Integer idCar;
    
    @Column(name = "brand", length = 50)
    private String brand;
    
    @Column(name = "model", length = 50)
    private String model;
    
    @Column(name = "year_car")
    private Integer yearCar;
    
    @Column(name = "plate", length = 20, unique = true)
    private String plate;
    
    @Column(name = "id_cliente")
    private Integer idCliente;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", insertable = false, updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Client client;
    
    // Constructores
    public Car() {}
    
    public Car(String brand, String model, Integer yearCar, String plate, Integer idCliente) {
        this.brand = brand;
        this.model = model;
        this.yearCar = yearCar;
        this.plate = plate;
        this.idCliente = idCliente;
    }
    
    // Getters y Setters
    public Integer getIdCar() {
        return idCar;
    }
    
    public void setIdCar(Integer idCar) {
        this.idCar = idCar;
    }
    
    public String getBrand() {
        return brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public Integer getYearCar() {
        return yearCar;
    }
    
    public void setYearCar(Integer yearCar) {
        this.yearCar = yearCar;
    }
    
    public String getPlate() {
        return plate;
    }
    
    public void setPlate(String plate) {
        this.plate = plate;
    }
    
    public Integer getIdCliente() {
        return idCliente;
    }
    
    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }
    
    public Client getClient() {
        return client;
    }
    
    public void setClient(Client client) {
        this.client = client;
    }
}

