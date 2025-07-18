/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uce.edu.ec.ecocycle.model;


import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Usuario que publicó el producto

    private String name;
    private int quantity;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // Convierte el enum a String en la BD
    private ProductStatus status;
    
    private LocalDate expirationDate;
    private String description;
    private String imageUrl; // URL de la imagen del producto

    public Product() {
    }

    public Product(Long id, User user, String name, int quantity, ProductStatus status, LocalDate expirationDate, String description, String imageUrl) {
        this.id = id;
        
        
        this.user = user;
        this.name = name;
        this.quantity = quantity;
        this.status = status;
        this.expirationDate = expirationDate;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductStatus getStatus() { return status; }

    public void setStatus(ProductStatus status) { this.status = status; }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    
    
}