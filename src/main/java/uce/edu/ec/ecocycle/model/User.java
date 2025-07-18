/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uce.edu.ec.ecocycle.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userID;  // Nombre de usuario

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private UserType userType; // 'particular', 'acogida', 'admin'

    private int ecoPoints;
    private String rank;
    private String status;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;
    // Constructor vacío
    public User() {}

    // Constructor con parámetros
    public User(String userID, String password, String name, String email, String phone, UserType userType, int ecoPoints, String rank, String status) {
        this.userID = userID;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.userType = userType;
        this.ecoPoints = ecoPoints;
        this.rank = rank;
        this.status = status;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public int getEcoPoints() {
        return ecoPoints;
    }

    public void setEcoPoints(int ecoPoints) {
        this.ecoPoints = ecoPoints;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}