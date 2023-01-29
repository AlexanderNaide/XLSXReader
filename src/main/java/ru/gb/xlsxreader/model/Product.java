package ru.gb.xlsxreader.model;

import jakarta.persistence.*;
import jdk.jfr.Unsigned;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "products")
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Unsigned
    private Long id;

    @Column(name = "article", nullable = false)
    private String article;

    @Column(name = "modification")
    private String modification;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @Unsigned
    private Categories categories;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id", nullable = false)
    @Unsigned
    private Manufacturer manufacturer;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private Double price;

    @Column(name = "purchase_price")
    private Double purchasePrice;

    @Column(name = "old_price")
    private Double oldPrice;

    @Column(name = "count")
    private Integer count;

    @Column(name = "description", length = 1200)
    private String description;

    @Column(name = "path")
    private String path;

    @Column(name = "images_title", length = 2000)
    private String imagesTitle;

    @Column(name = "images_linc", length = 2000)
    private String imagesLinc;

    @Column(name = "specifications", length = 3000)
    private String specifications;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
