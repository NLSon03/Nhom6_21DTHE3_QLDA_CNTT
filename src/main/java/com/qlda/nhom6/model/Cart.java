package com.qlda.nhom6.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CART")
    private int id;
    @Column(name = "DATEBEGIN", nullable = false)
    private java.sql.Date dateBegin;
    @Column(name = "META")
    private String meta;
    @Column(name = "`ORDER`", nullable = false)
    private int order;
    @Column(name = "LINK")
    private String link;
    @Column(name = "HIDE", nullable = false)
    private boolean hide;

}