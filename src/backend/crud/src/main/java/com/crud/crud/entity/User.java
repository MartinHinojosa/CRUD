package com.crud.crud.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer idUser;
    
    @Column(name = "name_user", length = 50)
    private String nameUser;
    
    @Column(name = "last_name", length = 50)
    private String lastName;
    
    @Column(name = "email", length = 100)
    private String email;
    
    @Column(name = "password_user", length = 255)
    private String passwordUser;
    
    @Column(name = "rol", length = 50)
    private String rol;
    
    // Constructores
    public User() {}
    
    public User(String nameUser, String lastName, String email, String passwordUser, String rol) {
        this.nameUser = nameUser;
        this.lastName = lastName;
        this.email = email;
        this.passwordUser = passwordUser;
        this.rol = rol;
    }
    
    // Getters y Setters
    public Integer getIdUser() {
        return idUser;
    }
    
    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
    
    public String getNameUser() {
        return nameUser;
    }
    
    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPasswordUser() {
        return passwordUser;
    }
    
    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }
    
    public String getRol() {
        return rol;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }
}

