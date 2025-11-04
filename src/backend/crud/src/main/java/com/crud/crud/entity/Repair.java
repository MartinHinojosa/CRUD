package com.crud.crud.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;

@Entity
@Table(name = "repairs")
public class Repair {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_repair")
    private Integer idRepair;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "status", length = 50)
    private String status; // "Pendiente", "En Proceso", "Completada", "Cancelada"
    
    @Column(name = "entry_date")
    private LocalDateTime entryDate;
    
    @Column(name = "estimated_date")
    private LocalDateTime estimatedDate;
    
    @Column(name = "completion_date")
    private LocalDateTime completionDate;
    
    @Column(name = "cost", columnDefinition = "DECIMAL(10,2)")
    private Double cost;
    
    @Column(name = "id_car")
    private Integer idCar;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_car", insertable = false, updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Car car;
    
    @Column(name = "created_by")
    private Integer createdBy; // ID del usuario que crea la reparación
    
    // Constructores
    public Repair() {}
    
    public Repair(String description, String status, LocalDateTime entryDate, 
                  LocalDateTime estimatedDate, Integer idCar, Integer createdBy) {
        this.description = description;
        this.status = status;
        this.entryDate = entryDate;
        this.estimatedDate = estimatedDate;
        this.idCar = idCar;
        this.createdBy = createdBy;
    }
    
    // Getters y Setters
    public Integer getIdRepair() {
        return idRepair;
    }
    
    public void setIdRepair(Integer idRepair) {
        this.idRepair = idRepair;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getEntryDate() {
        return entryDate;
    }
    
    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }
    
    public LocalDateTime getEstimatedDate() {
        return estimatedDate;
    }
    
    public void setEstimatedDate(LocalDateTime estimatedDate) {
        this.estimatedDate = estimatedDate;
    }
    
    public LocalDateTime getCompletionDate() {
        return completionDate;
    }
    
    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }
    
    public Double getCost() {
        return cost;
    }
    
    public void setCost(Double cost) {
        this.cost = cost;
    }
    
    public Integer getIdCar() {
        return idCar;
    }
    
    public void setIdCar(Integer idCar) {
        this.idCar = idCar;
    }
    
    public Car getCar() {
        return car;
    }
    
    public void setCar(Car car) {
        this.car = car;
    }
    
    public Integer getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }
}

