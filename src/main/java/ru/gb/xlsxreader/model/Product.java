package ru.gb.xlsxreader.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "sub_category_1", nullable = false)
    private String subCategory1;

    @Column(name = "sub_category_2")
    private String subCategory2;

    @Column(name = "sub_category_3")
    private String subCategory3;

    @Column(name = "art", nullable = false)
    private Long article;

    @Column(name = "modification")
    private String modification;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private Double price;

    @Column(name = "old_price")
    private Double oldPrice;

    @Column(name = "purchase_price")
    private Double purchasePrice;

    @Column(name = "count")
    private Integer count;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "description", length = 1200)
    private String description;

}
