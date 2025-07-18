/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uce.edu.ec.ecocycle.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "reputation")
public class Reputation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rated_user_id", nullable = false) // Usuario que recibe la calificación
    private User ratedUser;

    @ManyToOne
    @JoinColumn(name = "rater_user_id", nullable = false) // Usuario que deja la calificación
    private User raterUser;

    private int rating; // Calificación de 1 a 5
    private String comment; // Comentario

    public Reputation() {}

    public Reputation(User ratedUser, User raterUser, int rating, String comment) {
        this.ratedUser = ratedUser;
        this.raterUser = raterUser;
        this.rating = rating;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getRatedUser() {
        return ratedUser;
    }

    public void setRatedUser(User ratedUser) {
        this.ratedUser = ratedUser;
    }

    public User getRaterUser() {
        return raterUser;
    }

    public void setRaterUser(User raterUser) {
        this.raterUser = raterUser;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
