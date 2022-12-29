package ru.gb.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "sub_category_2")
public class SubCategory2{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;
}
