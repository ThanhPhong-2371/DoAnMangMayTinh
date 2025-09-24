/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webserver.webbanhang.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author HP
 */

@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String comment;
    private String name;
    private Integer star;
    private String email;

    // Liên kết với Product
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // ===== Getter & Setter =====
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getStar() { return star; }
    public void setStar(Integer star) { this.star = star; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}
