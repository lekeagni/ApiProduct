package com.example.Manage.product.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int product_id;
    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "CATEGORIE", nullable = false)
    private String categorie;

    @Column(name = "PRIX", nullable = false)
    private Double prix;

}
