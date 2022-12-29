package ru.gb.xlsxreader.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "sub_category_1")
public class SubCategory1{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    @OneToMany (mappedBy = "subCategory1")
    private List<Product> productList;
}
