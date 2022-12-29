package ru.gb.model;


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

    @ManyToOne
    @Column(name = "category", nullable = false)
    private Category category;

    @ManyToOne
    @Column(name = "sub_category_1", nullable = false)
    private SubCategory1 subCategory1;

    @ManyToOne
    @Column(name = "sub_category_2")
    private SubCategory1 subCategory2;

    @ManyToOne
    @Column(name = "sub_category_3")
    private SubCategory1 subCategory3;

    @Column(name = "art", nullable = false)
    private Long article;

    @Column(name = "modification")
    private String modification;

    @Column(name = "title")
    private String title;

    @Column(name = "path")
    private String path;

    @Column(name = "price")
    private Double price;

    @Column(name = "old_price")
    private Double oldPrice;

    @Column(name = "purchase_price")
    private Double purchasePrice;

    @Column(name = "count")
    private Integer count;

    @Column(name = "description", length = 800)
    private String description;

    @Column(name = "short_description")
    private String shortDescription;

    @ManyToOne
    @Column(name = "manufacturer")
    private Manufacturer manufacturer;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "images", length = 800)
    private String images;

    @Column(name = "images_linc", length = 1200)
    private String imagesLinc;

    @Column(name = "specifications", length = 2000)
    private String specifications;


    public List<String> getImages(){
        return List.of(images.split(" "));
    }

    public List<String> getImagesLinc() {
        return List.of(imagesLinc.split(" "));
    }
}
