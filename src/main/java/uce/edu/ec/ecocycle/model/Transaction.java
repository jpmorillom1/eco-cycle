package uce.edu.ec.ecocycle.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User buyer; // Usuario que compra

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // Producto comprado

    private int quantity; // Cantidad comprada
    private LocalDateTime transactionDate; // Fecha de la transacción
    private String status; // Estado de la transacción: PENDIENTE, CONFIRMADA, RECHAZADA

    public Transaction(Long id, User buyer, Product product, int quantity, LocalDateTime transactionDate, String status) {
        this.id = id;
        this.buyer = buyer;
        this.product = product;
        this.quantity = quantity;
        this.transactionDate = transactionDate;
        this.status = status;
    }

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
    
}