package com.crud.crud.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    private Integer idClient;
    
    @Column(name = "name_client", length = 50)
    private String nameClient;
    
    @Column(name = "last_name", length = 50)
    private String lastName;
    
    @Column(name = "telephone", length = 10)
    private String telephone;
    
    @Column(name = "email", length = 100)
    private String email;
    
    @Column(name = "address", length = 150)
    private String address;
    
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Car> cars;
    
    // Constructores
    public Client() {}
    
    public Client(String nameClient, String lastName, String telephone, String email, String address) {
        this.nameClient = nameClient;
        this.lastName = lastName;
        this.telephone = telephone;
        this.email = email;
        this.address = address;
    }
    
    // Getters y Setters
    public Integer getIdClient() {
        return idClient;
    }
    
    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }
    
    public String getNameClient() {
        return nameClient;
    }
    
    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public List<Car> getCars() {
        return cars;
    }
    
    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}

